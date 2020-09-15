package com.se17.edonation;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;

public class MoneyPaymentActivity extends AppCompatActivity {

    Context ctx = MoneyPaymentActivity.this;
    //for toolbar
    Toolbar toolbar;
    Button pay;
    EditText etCardNumber, etVerCode;
    Spinner paymentSpinner, cardTypeSpinner,  monthSpinner, yearSpinner;
    AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_payment);

        etCardNumber = findViewById(R.id.cardNumber);
        etVerCode = findViewById(R.id.verifcationCode);
        paymentSpinner = findViewById(R.id.paymentinsSpinner);
        cardTypeSpinner = findViewById(R.id.cardtypeSpinner);
        monthSpinner = findViewById(R.id.monthSpinner);
        yearSpinner = findViewById(R.id.yearSpinner);

        //Initializing Validation
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        //Card Number validation
        awesomeValidation.addValidation(this,R.id.cardNumber, "[0-9]{16}$", R.string.invalid_cardNumber);

        //Verification Code validation
        awesomeValidation.addValidation(this,R.id.verifcationCode, "[0-9]{4}$", R.string.invalid_code);




        //toolbar code
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            toolbar.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        pay = findViewById(R.id.PayBtn);

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (paymentSpinner.getSelectedItem().toString().trim().equals("--Select--")) {
                        Toast.makeText(getApplicationContext(), "Please! Select Payment Instrument.", Toast.LENGTH_SHORT).show();
                }
                else if (cardTypeSpinner.getSelectedItem().toString().trim().equals("--Select--")) {
                        Toast.makeText(getApplicationContext(), "Please! Select Card Type.", Toast.LENGTH_SHORT).show();
                }
                else if (monthSpinner.getSelectedItem().toString().trim().equals("--Month--")) {
                    Toast.makeText(getApplicationContext(), "Please! Select Card Expiration Month.", Toast.LENGTH_SHORT).show();
                }
                else if (yearSpinner.getSelectedItem().toString().trim().equals("--Year--")) {
                    Toast.makeText(getApplicationContext(), "Please! Select Card Expiration Year.", Toast.LENGTH_SHORT).show();
                }
                else {
                        if (awesomeValidation.validate()){
                            startActivity(new Intent(ctx, SubmittedActivity.class));
                            finish();
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Please Fill out all the Fields", Toast.LENGTH_SHORT).show();
                        }
                }
            }

        });
    }

    //toolbar home icon
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

}
