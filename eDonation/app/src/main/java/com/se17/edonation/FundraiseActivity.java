package com.se17.edonation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.se17.edonation.UserAcountActivity.LoginActivity;

public class FundraiseActivity extends AppCompatActivity {

    Context ctx = FundraiseActivity.this;
    BottomNavigationView bottomNavigationView;
    private Toolbar toolbar;
    private FirebaseAuth auth;


    Button startCampaignBtn, viewCampaignBtn, otherCampaignBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fundraise);

        //for toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //firebase authentication instance
        auth = FirebaseAuth.getInstance();

        //start Campaign btn link
        startCampaignBtn = (Button) findViewById(R.id.createCampaignBtn);

        startCampaignBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ctx, StartCampaignActivity.class));
            }
        });

        //view my Campaign btn link
        viewCampaignBtn = (Button) findViewById(R.id.viewCampaignBtn);

        viewCampaignBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ctx, MycampaignsActivity.class));
            }
        });

        //other Campaign btn link
        otherCampaignBtn = (Button) findViewById(R.id.otherCampaignBtn);

        otherCampaignBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ctx, DonateActivity.class));
            }
        });

        //for Bottom navigation code
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        //setting home as a selection
        bottomNavigationView.setSelectedItemId(R.id.nav_fundraise);

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
                        finish();
                        return true;

                    case R.id.nav_donate:
                        startActivity(new Intent(getApplicationContext(),DonateActivity.class));
                        overridePendingTransition(0,0);
                        finish();
                        return true;

                    case R.id.nav_settings:
                        startActivity(new Intent(getApplicationContext(),SettingsActivity.class));
                        overridePendingTransition(0,0);
                        finish();
                        return true;
                }
                return false;
            }
        });
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
                startActivity(new Intent(ctx, LoginActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

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
}
