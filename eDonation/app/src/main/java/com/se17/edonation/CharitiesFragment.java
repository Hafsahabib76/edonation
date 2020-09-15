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

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class CharitiesFragment extends Fragment {

    private RecyclerView recyclerView;
    FirebaseRecyclerOptions<Charity> options;
    FirebaseRecyclerAdapter<Charity, CharityHolder> adapter;
    private DatabaseReference dataRef;

    public CharitiesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_charities, container, false);
        dataRef= FirebaseDatabase.getInstance().getReference().child("Charity");
        recyclerView = view.findViewById(R.id.fcRecyclerview);
        //charity recyclerview code
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        recyclerView.setHasFixedSize(true);
        LoadData();


        return view;
    }

    private void LoadData() {

        options = new FirebaseRecyclerOptions.Builder<Charity>().setQuery(dataRef, Charity.class).build();

        adapter = new FirebaseRecyclerAdapter<Charity, CharityHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CharityHolder holder, final int i, @NonNull Charity charity) {
                holder.charityTitle.setText(charity.getCharityName());
                Picasso.get().load(charity.getImageUrl()).into(holder.charityImage);
                holder.v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), ChartiysingleActivity.class);
                        intent.putExtra("CharityKey", getRef(i).getKey());
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public CharityHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
                return new CharityHolder(view);
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
