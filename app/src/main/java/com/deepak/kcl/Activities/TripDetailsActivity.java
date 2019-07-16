package com.deepak.kcl.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.deepak.kcl.Adapter.TripDetailsRecyclerView;
import com.deepak.kcl.R;
import com.deepak.kcl.Storage.SharedPrefManager;
import com.deepak.kcl.models.BranchTrips;
import com.deepak.kcl.models.TripDetails;

import java.util.ArrayList;
import java.util.List;

public class TripDetailsActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView tripDetailsRecyclerView;
    TripDetailsRecyclerView mTripDetailsAdapter;
    List<TripDetails> mTripDetails;
    BranchTrips branchTrips;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_details);

        initView();
    }

    private void initView() {
        toolbar = findViewById(R.id.tripDetailsToolbar);
        tripDetailsRecyclerView = findViewById(R.id.TripDetailRecylerView);
        initializeView();
    }

    private void initializeView() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        tripDetailsRecyclerView.setLayoutManager(mLayoutManager);

        loadJSON();

    }

    private void loadJSON() {
        List<TripDetails> mtTripDetails = new ArrayList<>();
        mtTripDetails.add(new TripDetails("01","Summary"));
        mtTripDetails.add(new TripDetails("02","Advances"));
        mtTripDetails.add(new TripDetails("03","Status"));
        mtTripDetails.add(new TripDetails("04","Splitting Advances"));
        mtTripDetails.add(new TripDetails("05","Trip Closure"));
        mtTripDetails.add(new TripDetails("06","Trip Recon"));
        tripDetailsRecyclerView.setAdapter(new TripDetailsRecyclerView(this, mtTripDetails));
        tripDetailsRecyclerView.smoothScrollToPosition(0);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
