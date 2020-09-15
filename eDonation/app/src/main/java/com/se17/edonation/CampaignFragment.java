package com.se17.edonation;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CampaignFragment extends Fragment {

    private RecyclerView recyclerView;
    private FirebaseRecyclerOptions<Campaign> options;
    private FirebaseRecyclerAdapter<Campaign,CampaignHolder> adapter;
    private DatabaseReference dataReff;

    public CampaignFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_campaign, container, false);
        dataReff= FirebaseDatabase.getInstance().getReference().child("Campaign");
        recyclerView = view.findViewById(R.id.fcampRecyclerview);
        //charity recyclerview code
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        recyclerView.setHasFixedSize(true);
        LoadData();

        return view;
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
                        Intent intent=new Intent(getActivity(),CampaignsingleActivity.class);
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
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

}
