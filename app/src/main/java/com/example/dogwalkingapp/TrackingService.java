package com.example.dogwalkingapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class TrackingService extends Service {
    FusedLocationProviderClient fusedLocationClient;
    Location PreviousLocation, CurrentLocation;
    LocationCallback locationCallBack;
    LocationListener listener;
    LocationManager locationManager;
    double distanceTraveled;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onCreate() {
        Toast.makeText(this, "Tracking Started", Toast.LENGTH_SHORT).show();
        listener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                CurrentLocation = location;
                Intent i = new Intent("GPS_UPDATE");
                if (PreviousLocation == null) {
                    distanceTraveled = 0.0;
                    i.putExtra("distanceTraveled", distanceTraveled);
                    sendBroadcast(i);
                } else {
                    distanceTraveled += BigDecimal.valueOf((PreviousLocation.distanceTo(CurrentLocation) * 0.000621371192))
                            .setScale(2, RoundingMode.HALF_UP)
                            .doubleValue();
                    i.putExtra("distanceTraveled", distanceTraveled);
                    sendBroadcast(i);
                }
                PreviousLocation = CurrentLocation;
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

        };

        locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, listener);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "Tracking service Stopped", Toast.LENGTH_SHORT).show();

        fusedLocationClient.removeLocationUpdates(locationCallBack);

    }

}