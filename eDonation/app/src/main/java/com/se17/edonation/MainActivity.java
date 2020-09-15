package com.se17.edonation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.se17.edonation.UserAcountActivity.LoginActivity;
import com.se17.edonation.UserAcountActivity.RegisterActivity;

public class MainActivity extends AppCompatActivity {

    Context ctx = MainActivity.this;
    Button loginbtn, registerbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //check internet connection
        checkConnection();

        Button loginbtn = (Button) findViewById(R.id.loginId);
        Button registerbtn = (Button) findViewById(R.id.registerId);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(ctx, LoginActivity.class);
                startActivity(in);
                finish();
            }
        });

        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(ctx, RegisterActivity.class);
                startActivity(in);
                finish();
            }
        });
    }

    //check internet connection method
    public void checkConnection(){
        ConnectivityManager manager = (ConnectivityManager) getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();

        if (null==activeNetwork){

            Toast.makeText(this, "No Internet Available", Toast.LENGTH_LONG).show();

        }
    }


}
