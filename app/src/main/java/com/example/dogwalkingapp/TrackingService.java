package com.example.dogwalkingapp;


import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

import android.os.Bundle;
import android.os.IBinder;
import android.text.format.Time;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class TrackingService extends Service {
    private Location PreviousLocation, CurrentLocation;
    private LocationListener listener;
    private LocationManager locationManager;
    private double distanceTraveled;
    private Long walkStartTime, walkEndTime;

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

            Intent i = new Intent("GPS_UPDATE");
            Bundle extras = new Bundle();

            @Override
            public void onLocationChanged(@NonNull Location location) {
                CurrentLocation = location;
                if (walkStartTime == null) {
                    walkStartTime = CurrentLocation.getTime();
                    extras.putLong("walkStartTime", walkStartTime);
                }
                if (PreviousLocation == null) {
                    distanceTraveled = 0.0;
                    extras.putDouble("distanceTraveled", distanceTraveled);
                    i.putExtras(extras);
                    sendBroadcast(i);
                } else {
                    distanceTraveled += BigDecimal.valueOf((PreviousLocation.distanceTo(CurrentLocation) * 0.000621371192))
                            .setScale(2, RoundingMode.HALF_UP)
                            .doubleValue();
                    extras.putDouble("distanceTraveled", distanceTraveled);
                    i.putExtras(extras);
                    sendBroadcast(i);
                }
                PreviousLocation = CurrentLocation;
                walkEndTime = CurrentLocation.getTime();
                extras.putLong("walkEndTime", walkEndTime);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

        };

        locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 0, listener);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "Tracking Stopped", Toast.LENGTH_SHORT).show();

        locationManager.removeUpdates(listener);

    }

}