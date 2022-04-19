package com.example.dogwalkingapp;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.math.BigDecimal;
import java.math.RoundingMode;


public class WalkTracking extends AppCompatActivity {

    private static final int PERMISSIONS_FINE_LOCATION = 99;
    FusedLocationProviderClient fusedLocationClient;
    Location PreviousLocation, CurrentLocation;
    LocationRequest LocationRequest;
    LocationCallback locationCallBack;
    BroadcastReceiver broadcastReceiver;
    double distanceTraveled;
    TextView trackingDisplay;
    Handler h = new Handler();
    int delay = 100; //milliseconds


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
                }
            };
        }
        registerReceiver(broadcastReceiver, new IntentFilter("GPS_UPDATE"));

        h.postDelayed(new Runnable(){
            public void run(){
                updateTracker(distanceTraveled);
                h.postDelayed(this, delay);
            }
        }, delay);

    }

    private void startLocationUpdates() {
        PreviousLocation = CurrentLocation;
        locationCallBack = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);
                CurrentLocation = locationResult.getLastLocation();
                if (PreviousLocation == null) {
                    distanceTraveled = 0.0;
                }
                else {
                    distanceTraveled += BigDecimal.valueOf((PreviousLocation.distanceTo(CurrentLocation)*0.000621371192))
                                            .setScale(3, RoundingMode.HALF_UP)
                                            .doubleValue();
                }
            }
        };
        fusedLocationClient.requestLocationUpdates(LocationRequest, locationCallBack, null);
        updateGPS();
    }

    public void updateTracker(double distance) {
        trackingDisplay.setText("Total Distance Walked:\n"+ distance + " Miles");
    }

    public void onStopTracking(View view) {
        Toast.makeText(this, "Tracking Stopped", Toast.LENGTH_SHORT).show();
        trackingDisplay.setText(getString(R.string.click_start_tracking_to_begin));
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