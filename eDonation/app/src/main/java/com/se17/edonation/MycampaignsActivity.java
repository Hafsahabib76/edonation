package com.se17.edonation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MycampaignsActivity extends AppCompatActivity {

    Context ctx=MycampaignsActivity.this;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private FirebaseRecyclerOptions<Campaign> options;
    private FirebaseRecyclerAdapter<Campaign,CampaignHolder> adapter;
    private DatabaseReference dataReff;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycampaigns);

        //for toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        dataReff= FirebaseDatabase.getInstance().getReference().child("Campaign");
        //campaign recyclerview coding
        recyclerView = findViewById(R.id.myCampaignRecyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);
        LoadData();

    }

    private void LoadData(){

        options = new FirebaseRecyclerOptions.Builder<Campaign>().setQuery(dataReff,Campaign.class).build();
        adapter=new FirebaseRecyclerAdapter<Campaign, CampaignHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CampaignHolder holder, final int i, @NonNull Campaign campaign) {
                holder.campaignTitle.setText(campaign.getTitle());
                holder.campaignCateogry.setText(campaign.getCategory());
                holder.campaignCity.setText(campaign.getCity());

                holder.v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(ctx,CampaignsingleActivity.class);
                        intent.putExtra("CampaignKey",getRef(i).getKey());
                        startActivity(intent);
                    }
                });


                //Campaign profile color
                ColorGenerator generator = ColorGenerator.MATERIAL;
                int color = generator.getRandomColor();
                TextDrawable builder = TextDrawable.builder().buildRound("ed", color);
                holder.campaignImg.setImageDrawable(builder);


            }

            @NonNull
            @Override
            public CampaignHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.campaign_row,parent,false);
                return new CampaignHolder(view);
            }
        };
        recyclerView.setAdapter(adapter);
    }
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
