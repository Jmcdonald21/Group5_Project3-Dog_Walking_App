package com.example.dogwalkingapp;

import android.accounts.AccountManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.identity.Identity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AccountSettings extends AppCompatActivity {

    public static final String TAG = "Account_Settings";

    TextView name, email;
    Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        logout = findViewById(R.id.logout_btn);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignOut();
            }
        });

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            String Name = user.getDisplayName();
            String Email = user.getEmail();

            name.setText(Name);
            email.setText(Email);
        }

        //Home Button
        Button HomeButton = (Button) findViewById(R.id.home_btn);
        HomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AccountSettings.this, HomeScreen.class));
            }
        });

    }

    private void SignOut() {

        Identity.getSignInClient(this).signOut();
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getApplicationContext(), LoginScreen.class);
        startActivity(intent);

    }
}

