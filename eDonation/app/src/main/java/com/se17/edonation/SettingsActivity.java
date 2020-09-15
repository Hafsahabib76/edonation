package com.se17.edonation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.se17.edonation.UserAcountActivity.ChangePasswordActivity;
import com.se17.edonation.UserAcountActivity.LoginActivity;

public class SettingsActivity extends AppCompatActivity {

    Context ctx = SettingsActivity.this;
    BottomNavigationView bottomNavigationView;
    private Toolbar toolbar;
    private TextView email;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private Button changePasswordBtn, updateDetails;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //firebase authentication instance
        auth = FirebaseAuth.getInstance();
        email = findViewById(R.id.useremail);
        changePasswordBtn = findViewById(R.id.changePasswordBtn);
        updateDetails = findViewById(R.id.updateDetailsBtn);

        updateDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ctx, UpdateUserDetailsActivity.class));
            }
        });

        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        setDataToView(user);

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(ctx, LoginActivity.class));
                    finish();
                }
            }
        };

        changePasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ctx, ChangePasswordActivity.class));
            }
        });


        //for toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //for Bottom navigation code
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        //setting home as a selection
        bottomNavigationView.setSelectedItemId(R.id.nav_settings);

        //Item selection
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){

                    case R.id.nav_home:
                        startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                        overridePendingTransition(0,0);
                        finish();
                        return true;

                    case R.id.nav_fundraise:
                        startActivity(new Intent(getApplicationContext(),FundraiseActivity.class));
                        overridePendingTransition(0,0);
                        finish();
                        return true;

                    case R.id.nav_donate:
                        startActivity(new Intent(getApplicationContext(),DonateActivity.class));
                        overridePendingTransition(0,0);
                        finish();
                        return true;

                    case R.id.nav_settings:
                        finish();
                        return true;
                }
                return false;
            }
        });

    }

    @SuppressLint("SetTextI18n")
    private void setDataToView(FirebaseUser user) {

        email.setText("Email: " + user.getEmail());


    }

    // listener call, when there is change in firebase user session
    FirebaseAuth.AuthStateListener authListener = new FirebaseAuth.AuthStateListener() {
        @SuppressLint("SetTextI18n")
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user == null) {
                // user auth state is changed - user is null
                // launch login activity
                startActivity(new Intent(ctx, LoginActivity.class));
                finish();
            } else {
                setDataToView(user);

            }
        }


    };

    //sign out method
    public void signOut() {
        auth.signOut();

        // this listener will be called when there is change in firebase user session
        FirebaseAuth.AuthStateListener authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(ctx, LoginActivity.class));
                    finish();
                }
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.logout:
                signOut();
                startActivity(new Intent(ctx,LoginActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
