package com.deepak.kcl.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.deepak.kcl.Adapter.AdvanceAdapter;
import com.deepak.kcl.R;
import com.deepak.kcl.Storage.SharedPrefManager;
import com.deepak.kcl.models.AdvanceChild;
import com.deepak.kcl.models.AdvanceHeader;
import com.deepak.kcl.models.BranchTrips;

import java.util.ArrayList;
import java.util.List;

public class AdvancesActivity extends AppCompatActivity {

    Toolbar toolbar;
    private RecyclerView advRecyclerView;
    private AdvanceAdapter advanceAdapter;
    private List<AdvanceHeader> advanceHeader;
    List<AdvanceChild> advanceChildList;
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
        advRecyclerView = findViewById(R.id.Adv_recyclerview);
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

        getadvanceHeader();
        advanceAdapter = new AdvanceAdapter(advanceHeader,this);
        advRecyclerView.setLayoutManager(new LinearLayoutManager(AdvancesActivity.this));
        advRecyclerView.setAdapter(advanceAdapter);
    }

    private void getadvanceHeader() {
        advanceHeader = new ArrayList<>(5);
        for(int i = 0; i < 5; i++)
        {
            advanceChildList = new ArrayList<>();
            if(i==0) {
                advanceChildList.add(new AdvanceChild("05 June'19", "Rs.10000", "Mumbbai HO","Fuel Charge Day1"));
                advanceChildList.add(new AdvanceChild("04 June'19", "Rs.10000", "HO", "Fuel Charge Day2"));
                advanceHeader.add(new AdvanceHeader("IOCL", advanceChildList));
            }
            if(i==1) {
                advanceChildList.add(new AdvanceChild("05 June'19", "Rs.10000", "Jaipur","Fuel Charge Day1"));
                advanceHeader.add(new AdvanceHeader("CASH", advanceChildList));
            }
            if(i==2) {
                advanceChildList.add(new AdvanceChild("05 June'19", "Rs.10000", "Nagpur","Fuel Charge Day1"));
                advanceHeader.add(new AdvanceHeader("BANK", advanceChildList));
            }
            if(i==3) {
                advanceChildList.add(new AdvanceChild("05 June'19", "Rs.10000", "Banglore","Fuel Charge Day1"));
                advanceHeader.add(new AdvanceHeader("HAPPAY", advanceChildList));
            }
            if(i==4) {
                advanceHeader.add(new AdvanceHeader("OTHERS", advanceChildList));
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void setAdvanceHeader(List<AdvanceHeader> advanceHeader) {
        this.advanceHeader = advanceHeader;
    }
}
