package com.se17.edonation;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class DonateMoneyActivity extends AppCompatActivity {

    Context ctx = DonateMoneyActivity.this;
    Button donateMoney;
    EditText etAmount, etName, etNumber;
    AwesomeValidation awesomeValidation;
    TextView charityName , campaignName;

    Donation donation;
    DatabaseReference mreff,ref;

    //for toolbar
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate_money);

        charityName = findViewById(R.id.charityName);
        campaignName = findViewById(R.id.campaignName);
        etAmount = findViewById(R.id.amount);
        etName = findViewById(R.id.donorName);
        etNumber = findViewById(R.id.number);

        Bundle bn = getIntent().getExtras();
        String charityname = bn.getString("name");
        String campaignTitle = bn.getString("camp");
        campaignName.setText(campaignTitle);
        charityName.setText(charityname);

        //Initializing Validation
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        //amount validation
        awesomeValidation.addValidation(this,R.id.amount, RegexTemplate.NOT_EMPTY,R.string.invalid_amount);

        //name validation
        awesomeValidation.addValidation(this,R.id.donorName, RegexTemplate.NOT_EMPTY,R.string.invalid_name);

        //mobile validation
        awesomeValidation.addValidation(this,R.id.number, "[0]{1}[0-9]{10}$", R.string.invalid_mobile);

        donateMoney = (Button)findViewById(R.id.donateMoneyBtn);

        //coding for moving data to database
        donation = new Donation();
        mreff= FirebaseDatabase.getInstance().getReference().child("Donation");

        donateMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Check Validation
                if (awesomeValidation.validate()){
                    //saving data in database after btn click
                    donation.setDonationAmount(etAmount.getText().toString().trim());
                    donation.setDonorName(etName.getText().toString().trim());
                    donation.setDonorNumber(etNumber.getText().toString().trim());
                    donation.setCharityName(charityName.getText().toString().trim());
                    donation.setCampaignTitle(campaignName.getText().toString().trim());
                    mreff.push().setValue(donation);


                    Intent in = new Intent(ctx, MoneyPaymentActivity.class);
                    startActivity(in);
                    finish();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Please Fill out all the Fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

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
                startActivity(new Intent(ctx,HomeActivity.class));
                finish();
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
