package com.deepak.kcl.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.deepak.kcl.Networking.RetrofitClient;
import com.deepak.kcl.R;
import com.deepak.kcl.Storage.SharedPrefManager;
import com.deepak.kcl.ViewHolders.AdvChildViewHolder;
import com.deepak.kcl.ViewHolders.AdvHeaderViewHolder;
import com.deepak.kcl.models.BranchTrips;
import com.deepak.kcl.models.TripAdvance;
import com.deepak.kcl.models.TripAdvanceResponse;
import com.mindorks.placeholderview.ExpandablePlaceHolderView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdvancesActivity extends AppCompatActivity {

    private Map<String,List<TripAdvance>> mHeading;
    private List<TripAdvance> advanceList;
    private ExpandablePlaceHolderView expandablePlaceHolderView;
    Toolbar toolbar;
    TextView txtlrnumber;
    BranchTrips branchTrips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advances);
        initView();
    }

    private void initView() {
        toolbar = findViewById(R.id.advanceToolbar);
        expandablePlaceHolderView = findViewById(R.id.Adv_recyclerview);
        txtlrnumber = findViewById(R.id.advance_txt_lrno);
        initializeView();
    }

    private void initializeView() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);

        branchTrips = SharedPrefManager.getInstance(this).getBranchTrips();
        txtlrnumber.setText(branchTrips.getLR());

        advanceList = new ArrayList<>();
        mHeading = new HashMap<>();

        loadData();
    }

    private void loadData() {

        Call<TripAdvanceResponse> call = RetrofitClient.getInstance().getApi().getTripAdvances(Integer.parseInt(branchTrips.getLR()));
        call.enqueue(new Callback<TripAdvanceResponse>() {
            @Override
            public void onResponse(Call<TripAdvanceResponse> call, Response<TripAdvanceResponse> response) {
                advanceList =  response.body().getTripAdvance();
                getHeaderAndChild(advanceList);
            }

            @Override
            public void onFailure(Call<TripAdvanceResponse> call, Throwable t) {

            }
        });
    }

    private void getHeaderAndChild(List<TripAdvance> tripAdvances){

        for (TripAdvance tripAdvance : tripAdvances ){
            List<TripAdvance> tripAdvances1 = mHeading.get(tripAdvance.getTrip_exp_type());
            if(tripAdvances1 == null){
                tripAdvances1 = new ArrayList<>();
            }
            tripAdvances1.add(tripAdvance);
            mHeading.put(tripAdvance.getTrip_exp_type(),tripAdvances1);
        }

        Log.d("Map",mHeading.toString());
        Iterator it = mHeading.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            Log.d("Key", pair.getKey().toString());
            expandablePlaceHolderView.addView(new AdvHeaderViewHolder(this, pair.getKey().toString()));
            List<TripAdvance> tripAdvances2 = (List<TripAdvance>) pair.getValue();
            for (TripAdvance tripAdvance : tripAdvances2){
                expandablePlaceHolderView.addView(new AdvChildViewHolder(this, tripAdvance));
            }
            it.remove();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}