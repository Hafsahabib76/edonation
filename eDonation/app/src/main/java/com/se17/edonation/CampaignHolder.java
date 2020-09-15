package com.se17.edonation;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.se17.edonation.R;

class CampaignHolder extends RecyclerView.ViewHolder {

    TextView campaignTitle, campaignCateogry, campaignCity;
    ImageView campaignImg;
    View v;

    public CampaignHolder(@NonNull View itemView) {
        super(itemView);

        campaignTitle =itemView.findViewById(R.id.campaignName);
        campaignCateogry =itemView.findViewById(R.id.campaignCategory);
        campaignCity = itemView.findViewById(R.id.campaignCity);
        campaignImg = itemView.findViewById(R.id.imageView);
        v=itemView;
    }
}
