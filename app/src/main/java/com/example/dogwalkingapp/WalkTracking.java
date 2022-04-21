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

import com.google.android.gms.location.FusedLocationProviderClient;

import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import java.util.HashMap;


@RequiresApi(api = Build.VERSION_CODES.N)
public class WalkTracking extends AppCompatActivity {

    private static final int PERMISSIONS_FINE_LOCATION = 99;
    private FusedLocationProviderClient fusedLocationClient;
    private Location CurrentLocation;
    private BroadcastReceiver broadcastReceiver;
    private double distanceTraveled;
    private Long walkStartTime, walkEndTime;
    private TextView trackingDisplay;
    private Handler h = new Handler();
    private int delay = 100; //milliseconds
    private DecimalFormat formatter = new DecimalFormat("0.00");
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private Date startDate, endDate;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.walk_tracking);
        trackingDisplay = findViewById(R.id.trackingCounter);
    }

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

        h.postDelayed(new Runnable(){
            public void run(){
                updateTracker(formatter.format(distanceTraveled));
                h.postDelayed(this, delay);
            }
        }, delay);

    }

    public void updateTracker(String distance) {
        trackingDisplay.setText("Total Distance Walked:\n"+ distance + " Miles");
    }

    public void onStopTracking(View view) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("walkDistance", distanceTraveled);
        hashMap.put("walkStartTime", startDate);
        hashMap.put("walkEndTime", endDate);

        /*

        databaseReference.child("Users")
                .child(userName)
                .setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(WalkTracking.this,"hello", Toast.LENGTH_SHORT).show();                 }
                });

         */

        trackingDisplay.setText(getString(R.string.click_start_tracking_to_begin));
        Intent i = new Intent(getApplicationContext(), TrackingService.class);
        stopService(i);
        distanceTraveled = 0;
        h.removeCallbacksAndMessages(null);

    }

    public void onHome(View view) {
        Intent intent = new Intent(this, StatScreen.class);
        startActivity(intent);
    }

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
                requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_FINE_LOCATION);
            }
        }
    }
}