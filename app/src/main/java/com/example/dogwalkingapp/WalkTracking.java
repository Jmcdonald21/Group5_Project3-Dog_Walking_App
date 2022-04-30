package com.example.dogwalkingapp;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.icu.text.DecimalFormat;
import android.icu.util.Calendar;
import android.location.Location;



import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.Time;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;

import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import java.util.HashMap;

/**
 * WalkTracking class sets up the Tracking service for use with the GPS location as well as Firebase Database user information storing
 */
@RequiresApi(api = Build.VERSION_CODES.N)
public class WalkTracking extends AppCompatActivity {
    /**
     * Permission code for access to user's fine location
     */
    private static final int PERMISSIONS_FINE_LOCATION = 99;
    /**
     * Permission code for access to user's background location
     */
    private static final int PERMISSIONS_BACKGROUND_LOCATION = 66;
    /**
     * Creates client for use with FusedLocation service
     */
    private FusedLocationProviderClient fusedLocationClient;
    /**
     * Location variable keeping track of user's current location
     */
    private Location CurrentLocation;
    /**
     * Creates a broadcast receiver to receive information from the tracking service
     */
    private BroadcastReceiver broadcastReceiver;
    /**
     * Variable keeping track of user's distance traveled during a tracked walk
     */
    private double distanceTraveled;
    /**
     * Variables storing the walk start time and end time in milliseconds
     */
    private Long walkStartTime, walkEndTime;
    /**
     * Creates variable connecting the textview of the XML screen associated with this activity
     */
    private TextView trackingDisplay;
    /**
     * New handler used to update textview UI to show current distance traveled during walk tracking
     */
    private Handler updateCurrDistance = new Handler();
    /**
     * Variable storing the handler's delay time in milliseconds
     */
    private int delay = 100; //milliseconds
    /**
     * New instance of decimal formatter for adjusting decimal placement length of distance traveled variable
     */
    private DecimalFormat formatter = new DecimalFormat("0.00");
    /**
     * Creates Firebase Database connectivity
     */
    private FirebaseDatabase firebaseDatabase;
    /**
     * Creates Firebase Database reference
     */
    private DatabaseReference databaseReference;
    /**
     * Date variables storing the start and end dates of walk being tracked (converts milliseconds to date time)
     */
    private Date startDate, endDate;
    /**
     * Creates variable for storing currently logged in user's username
     */
    private String userName;

    private String[] PERMISSIONS;

    /**
     * Override for the onCreate method of the WalkTracking activity class
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.walk_tracking);
        trackingDisplay = findViewById(R.id.trackingCounter);
        updateGPS();

    }

    /**
     * Override for the OnRequestPermissionResult method used when checking permissions used during the walk tracking
     * associated with user location and GPS functionality
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch(requestCode) {
            case PERMISSIONS_FINE_LOCATION:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    updateGPS();
                }
                else {
                    Toast.makeText(this, "This app requires permission to be granted in order to work properly", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
        }
    }

    /**
     * Creates new method for beginning the tracking service utilizing the user's GPS as well as location data.
     * This method stores all information being broadcast by the tracking service into variables that can then be
     * uploaded to the user's stored data on the Firebase Database
     * @param view
     */
    public void onStartTracking(View view) {
        Intent i = new Intent(getApplicationContext(), TrackingService.class);
        startService(i);

        if (broadcastReceiver == null) {
            broadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    distanceTraveled = intent.getDoubleExtra("distanceTraveled",0);
                    walkStartTime = intent.getLongExtra("walkStartTime", 0);
                    walkEndTime = intent.getLongExtra("walkEndTime", 0);
                    startDate = new Date(walkStartTime);
                    endDate = new Date(walkEndTime);
                }
            };
        }
        registerReceiver(broadcastReceiver, new IntentFilter("GPS_UPDATE"));

        updateCurrDistance.postDelayed(new Runnable(){
            public void run(){
                updateTracker(formatter.format(distanceTraveled));
                updateCurrDistance.postDelayed(this, delay);
            }
        }, delay);

    }

    /**
     * Creates a method that updates the textview display from the XML document associated with the WalkTracking class's activity
     * @param distance
     */
    public void updateTracker(String distance) {
        trackingDisplay.setText("Total Distance Walked:\n"+ distance + " Miles");
    }

    /**
     * Creates a method that stop the service tracking the user using GPS and location data, as well as updates the user's firebase database
     * profile to accurately reflect all tracking information being generated by the current walk being tracked
     * @param view
     */
    public void onStopTracking(View view) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("walkDistance", distanceTraveled);
        hashMap.put("walkStartTime", startDate);
        hashMap.put("walkEndTime", endDate);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        DAOWalks daoWalks = new DAOWalks();
        Walks walks = new Walks();
        if (user != null){
            walks.setName(user.getDisplayName());
            walks.setDistanceTraveled(distanceTraveled);
            walks.setStartDate(startDate);
            walks.setEndDate(endDate);
            walks.setuID(user.getUid());
            daoWalks.add(walks).addOnSuccessListener(suc ->
            {
                Toast.makeText(this, "Walk was inserted", Toast.LENGTH_SHORT).show();
            }).addOnFailureListener(er ->
            {
                Toast.makeText(this, "" + er.getMessage(), Toast.LENGTH_SHORT).show();
            });
        }


        trackingDisplay.setText(getString(R.string.click_start_tracking_to_begin));
        Intent i = new Intent(getApplicationContext(), TrackingService.class);
        stopService(i);
        distanceTraveled = 0;
        updateCurrDistance.removeCallbacksAndMessages(null);

    }

    /**
     * Creates method that navigates the user to the "home" screen via the "home" button being pressed
     * @param view
     */
    public void onHome(View view) {
        Intent intent = new Intent(this, HomeScreen.class);
        startActivity(intent);
    }

    /**
     * Creates a method that generates the permission request to the user as well as provides the first user location data that will
     * be used when beginning the walk current being tracked by the user
     */

    private void updateGPS() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(WalkTracking.this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    CurrentLocation = location;
                }
            });
        }
        else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                ActivityCompat.requestPermissions(WalkTracking.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_FINE_LOCATION);
            }
        }
    }
}