package com.example.dogwalkingapp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.dogwalkingapp.databinding.HomeScreenBinding;

/**
 * Homescreen class for creating the activity that generates the homescreen UI as well as all
 * associated buttons
 */
public class HomeScreen extends AppCompatActivity {

    // declare variables for homescreen
    private AppBarConfiguration appBarConfiguration;
    private HomeScreenBinding binding;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = HomeScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //start tracking button
        Button StartTrackButton = (Button) findViewById(R.id.button2);
        StartTrackButton.setOnClickListener(v -> startActivity(new Intent(HomeScreen.this, WalkTracking.class)));

        //View tracking button
        Button ViewTrackingButton = (Button) findViewById(R.id.button);
        ViewTrackingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeScreen.this, StatScreen.class));
            }
        });

        //Goals button
        Button GoalsButton = (Button) findViewById(R.id.button3);
        GoalsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeScreen.this, GoalsScreen.class));
            }
        });

        //account settings button
        Button AccSetButton = (Button) findViewById(R.id.button4);

        AccSetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeScreen.this, AccountSettings.class));
            }
        });

        Button viewWalks = (Button) findViewById(R.id.viewWalks_btn);

        viewWalks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeScreen.this, RVScreen.class));

            }
        });

    }
}