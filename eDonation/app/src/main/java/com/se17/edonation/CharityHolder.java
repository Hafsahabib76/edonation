package com.se17.edonation;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.se17.edonation.R;

public class CharityHolder extends RecyclerView.ViewHolder{

    public TextView charityTitle;
    public ImageView charityImage;
    public View v;

    public CharityHolder(@NonNull View itemView) {
        super(itemView);

        charityTitle =itemView.findViewById(R.id.charityName);
        charityImage =itemView.findViewById(R.id.imageView);
        v=itemView;
    }

}

