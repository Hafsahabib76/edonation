package com.se17.edonation;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.se17.edonation.UserAcountActivity.LoginActivity;
import com.squareup.picasso.Picasso;

public class HomeActivity extends AppCompatActivity {

    Context ctx = HomeActivity.this;
    private BottomNavigationView bottomNavigationView;

    private RecyclerView recyclerView;
    FirebaseRecyclerOptions<Charity> options;
    FirebaseRecyclerAdapter<Charity, CharityHolder> adapter;
    private DatabaseReference dataRef;
    private FirebaseAuth auth;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //firebase authentication instance
        auth = FirebaseAuth.getInstance();

        //check internet connection
        checkConnection();

        //for toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //for Bottom navigation code
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        //setting home as a selection
        bottomNavigationView.setSelectedItemId(R.id.nav_home);

        //Item selection
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.nav_home:
                        finish();
                        return true;

                    case R.id.nav_fundraise:
                        startActivity(new Intent(getApplicationContext(), FundraiseActivity.class));
                        overridePendingTransition(0, 0);
                        finish();
                        return true;

                    case R.id.nav_donate:
                        startActivity(new Intent(getApplicationContext(), DonateActivity.class));
                        overridePendingTransition(0, 0);
                        finish();
                        return true;

                    case R.id.nav_settings:
                        startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                        overridePendingTransition(0, 0);
                        finish();
                        return true;
                }
                return false;
            }
        });

        dataRef= FirebaseDatabase.getInstance().getReference().child("Charity");
        //charity recyclerview code
        recyclerView = (RecyclerView) findViewById(R.id.homeRecyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);
        LoadData();


    }

    private void LoadData() {

        options = new FirebaseRecyclerOptions.Builder<Charity>().setQuery(dataRef,Charity.class).build();

        adapter=new FirebaseRecyclerAdapter<Charity, CharityHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CharityHolder holder, final int i, @NonNull Charity charity) {
                holder.charityTitle.setText(charity.getCharityName());
                Picasso.get().load(charity.getImageUrl()).into(holder.charityImage);
                holder.v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //check internet connection
                        checkConnection();
                        Intent intent=new Intent(ctx, ChartiysingleActivity.class);
                        intent.putExtra("CharityKey",getRef(i).getKey());
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public CharityHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row,parent,false);
                return new CharityHolder(view);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.logout:
                signOut();
                startActivity(new Intent(ctx,LoginActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //sign out method
    public void signOut() {
        auth.signOut();

        // this listener will be called when there is change in firebase user session
        FirebaseAuth.AuthStateListener authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(ctx, LoginActivity.class));
                    finish();
                }
            }
        };
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

