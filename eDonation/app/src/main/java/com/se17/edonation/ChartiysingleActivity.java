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
import com.squareup.picasso.Picasso;

public class ChartiysingleActivity extends AppCompatActivity {

    private Toolbar toolbar;
    Context ctx = ChartiysingleActivity.this;
    private DatabaseReference ref;
    private ImageView charityimg;
    private TextView charityName, about;
    private Button donatenow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chartiysingle);

        charityimg = findViewById(R.id.imageView);
        charityName = findViewById(R.id.charityName);
        about = findViewById(R.id.about_tv);
        donatenow = findViewById(R.id.donateNowBtn);

        ref= FirebaseDatabase.getInstance().getReference().child("Charity");

        String CharityKey=getIntent().getStringExtra("CharityKey");

        ref.child(CharityKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if ((dataSnapshot.exists())){
                    String CharityName = dataSnapshot.child("CharityName").getValue().toString();
                    String ImageUrl = dataSnapshot.child("ImageUrl").getValue().toString();
                    String About = dataSnapshot.child("About").getValue().toString();

                    Picasso.get().load(ImageUrl).into(charityimg);
                    charityName.setText(CharityName);
                    about.setText(About);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        donatenow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Data = charityName.getText().toString();
                Intent in = new Intent(ctx, DonateMoneyActivity.class);
                in.putExtra("name",Data);
                startActivity(in);
                finish();

            }
        });

        //for toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
}
