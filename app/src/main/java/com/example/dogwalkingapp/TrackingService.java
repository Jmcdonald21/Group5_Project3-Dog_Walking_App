package com.example.dogwalkingapp;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

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
                            .setScale(3, RoundingMode.HALF_UP)
                            .doubleValue();
                    i.putExtra("distanceTraveled", distanceTraveled);
                    sendBroadcast(i);
                }
                PreviousLocation = CurrentLocation;
            }

        };

        locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, listener);

    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "Tracking service Stopped", Toast.LENGTH_SHORT).show();

        fusedLocationClient.removeLocationUpdates(locationCallBack);

    }

}