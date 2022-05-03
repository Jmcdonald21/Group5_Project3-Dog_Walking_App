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

/**
 * Account Settings class creates the screen for displaying user account settings as well as provides the logout function
 * for the user
 */
public class AccountSettings extends AppCompatActivity {

    public static final String TAG = "Account_Settings";
    // creates textview instances for UI
    TextView name, email;
    // creates instance of button for logout
    Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);

        // associates previously creates variables with their assigned UI aspects
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        logout = findViewById(R.id.logout_btn);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignOut();
            }
        });

        // create instance of currently logged in user to firebase
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        //If the user is logged in this method saves the Name and Email to strings Name and Email.
        if (user != null) {
            String Name = user.getDisplayName();
            String Email = user.getEmail();

            name.setText(Name);
            email.setText(Email);
        }

        //Home Button
        //Returns user to the "HomeScreen"
        Button HomeButton = (Button) findViewById(R.id.home_btn);
        HomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AccountSettings.this, HomeScreen.class));
            }
        });

    }

    //Begins the sign out process for oneTapClient
    private void SignOut() {

        Identity.getSignInClient(this).signOut();
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getApplicationContext(), LoginScreen.class);
        startActivity(intent);

    }
}

