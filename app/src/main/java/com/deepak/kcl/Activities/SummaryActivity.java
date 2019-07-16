package com.deepak.kcl.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.deepak.kcl.R;
import com.deepak.kcl.Storage.SharedPrefManager;
import com.deepak.kcl.models.BranchTrips;

public class SummaryActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView txtCLientName,txtJourneyId,txtStartDate,txtEndDate,txtVehicleNo,txtKm;
    BranchTrips branchTrips;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        initView();
    }

    private void initView() {
        toolbar = findViewById(R.id.summaryToolbar);
        initializeView();
    }

    private void initializeView() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);

        branchTrips = SharedPrefManager.getInstance(this).getBranchTrips();

        txtCLientName = findViewById(R.id.txt_summary_clientName);
        txtJourneyId = findViewById(R.id.txt_summary_journeyId);
        txtStartDate = findViewById(R.id.txt_summary_startDate);
        txtEndDate = findViewById(R.id.txt_summary_endDate);
        txtVehicleNo = findViewById(R.id.txt_summary_vehicleNo);
        txtKm = findViewById(R.id.txt_summary_km);

        txtCLientName.setText(branchTrips.getClient_name());
        txtJourneyId.setText(branchTrips.getLR());
        txtStartDate.setText(branchTrips.getS_date());
        txtEndDate.setText(branchTrips.getE_date());
        txtVehicleNo.setText(branchTrips.getVehicle_no());
        txtKm.setText(branchTrips.getKM());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
