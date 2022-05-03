package com.example.dogwalkingapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;

import androidx.navigation.ui.AppBarConfiguration;

import com.example.dogwalkingapp.databinding.GoalsScreenBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Goals screen class creates the activity for displaying the goal screen UI
 */
public class GoalsScreen extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private GoalsScreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = GoalsScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //firebase connection information
        FirebaseDatabase rootNode;
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference();


        //Create Goal Button
        Button GoalButton = (Button) findViewById(R.id.button8);
        GoalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //Home Screen Button
        Button HomeButton = (Button) findViewById(R.id.button2);
        HomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GoalsScreen.this, HomeScreen.class));
            }
        });







    }


}