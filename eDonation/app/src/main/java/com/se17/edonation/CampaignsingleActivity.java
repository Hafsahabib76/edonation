package com.se17.edonation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class CampaignsingleActivity extends AppCompatActivity {

    Context ctx=CampaignsingleActivity.this;
    private Toolbar toolbar;
    TextView campaignTitle, campaignCategory, campaignCity, campaignAbout;
    Button donatenow;
    ImageView campaignImage;

    private StorageReference mSReff;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campaignsingle);

        //for toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        campaignTitle = findViewById(R.id.singleCampaignTitle);
        campaignCategory = findViewById(R.id.singleCampaignCateogry);
        campaignCity = findViewById(R.id.singleCampaignCity);
        campaignAbout = findViewById(R.id.singleCampaignAbout);
        campaignImage = findViewById(R.id.singleCampaignImage);
        donatenow = findViewById(R.id.singleDonteNowBtn);

        ref= FirebaseDatabase.getInstance().getReference().child("Campaign");

        String CampaignKey = getIntent().getStringExtra("CampaignKey");

        ref.child(CampaignKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if ((dataSnapshot.exists())){
                    String title = dataSnapshot.child("title").getValue().toString();
                    String category = dataSnapshot.child("category").getValue().toString();
                    String city = dataSnapshot.child("city").getValue().toString();
                    String aboutCampaign = dataSnapshot.child("aboutCampaign").getValue().toString();

                    campaignTitle.setText(title);
                    campaignCategory.setText(category);
                    campaignCity.setText(city);
                    campaignAbout.setText(aboutCampaign);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        donatenow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Data = campaignTitle.getText().toString();
                Intent in = new Intent(ctx, DonateMoneyActivity.class);
                in.putExtra("camp",Data);
                startActivity(in);
                finish();
            }
        });
    }
}
