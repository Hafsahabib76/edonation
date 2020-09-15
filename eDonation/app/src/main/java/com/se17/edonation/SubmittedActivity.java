package com.se17.edonation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SubmittedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submitted);

        new Handler().postDelayed(new Runnable() {

                                      @Override
                                      public void run() {
             startActivity(new Intent(SubmittedActivity.this, HomeActivity.class));
             finish();
             }
        },

        3*1000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

