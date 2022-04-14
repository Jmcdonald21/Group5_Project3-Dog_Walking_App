package com.example.dogwalkingapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.Timer;
import java.util.TimerTask;


public class WalkTracking extends AppCompatActivity {

    private static final int PERMISSIONS_FINE_LOCATION = 99;
    FusedLocationProviderClient fusedLocationClient;
    Location PreviousLocation, CurrentLocation;
    LocationRequest LocationRequest;
    LocationCallback locationCallBack;
    double distanceTraveled;
    TextView trackingDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.walk_tracking);
        trackingDisplay = findViewById(R.id.trackingCounter);

        LocationRequest = new LocationRequest();
        LocationRequest.setInterval(1000); //1 seconds
        LocationRequest.setFastestInterval(500); //.5 seconds
        LocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationRequest.setSmallestDisplacement(1); //1 meter

        updateGPS();

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
        startLocationUpdates();
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
                    distanceTraveled += PreviousLocation.distanceTo(CurrentLocation);
                }
            }
        };
        fusedLocationClient.requestLocationUpdates(LocationRequest, locationCallBack, null);
        updateGPS();
    }

    public void updateTracker(double distance) {
        trackingDisplay.setText("Total Distance Walked:\n"+ distance + " Meters");
    }

    public void onStopTracking(View view) {
        updateTracker(distanceTraveled);
    }

    public void onHome(View view) {
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