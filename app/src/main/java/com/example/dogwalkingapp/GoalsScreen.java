package com.example.dogwalkingapp;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.dogwalkingapp.databinding.GoalsScreenBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GoalsScreen extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private GoalsScreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = GoalsScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //stuff for firebase
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