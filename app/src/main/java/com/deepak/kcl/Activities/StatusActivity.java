package com.deepak.kcl.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.deepak.kcl.R;
import com.deepak.kcl.Storage.SharedPrefManager;
import com.deepak.kcl.models.BranchTrips;

public class StatusActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView txtlrno,txtdate,txtstatus;
    Button btnstatus1,btnstatus2,btnstatus3,btnstatus4,btnstatus5;
    TextView txtlrnumber;
    BranchTrips branchTrips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        initView();
    }

    private void initView() {
        toolbar = findViewById(R.id.tripStatusToolbar);
        txtlrnumber = findViewById(R.id.status_txt_lrno);
        initializeView();
    }

    private void initializeView() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);

        branchTrips = SharedPrefManager.getInstance(this).getBranchTrips();
        txtlrnumber.setText(branchTrips.getLR());

        txtlrno = findViewById(R.id.advance_txt_lrno);
        txtdate = findViewById(R.id.status_txt_date);
        txtstatus = findViewById(R.id.status_txt_current_sts);
        btnstatus1 = findViewById(R.id.status_btn1);
        btnstatus2 = findViewById(R.id.status_btn2);
        btnstatus3 = findViewById(R.id.status_btn3);
        btnstatus4 = findViewById(R.id.status_btn4);
        btnstatus5 = findViewById(R.id.status_btn5);

        btnstatus1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtstatus.setText(btnstatus1.getText().toString().trim());
            }
        });

        btnstatus2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtstatus.setText(btnstatus2.getText().toString().trim());
            }
        });

        btnstatus3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtstatus.setText(btnstatus3.getText().toString().trim());
            }
        });

        btnstatus4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtstatus.setText(btnstatus4.getText().toString().trim());
            }
        });

        btnstatus5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtstatus.setText(btnstatus5.getText().toString().trim());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
