package com.deepak.kcl.Activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.deepak.kcl.Adapter.AdvanceAdapter;
import com.deepak.kcl.Adapter.SplitAdvanceAdapter;
import com.deepak.kcl.R;
import com.deepak.kcl.Storage.SharedPrefManager;
import com.deepak.kcl.models.AdvanceChild;
import com.deepak.kcl.models.AdvanceHeader;
import com.deepak.kcl.models.BranchTrips;
import com.deepak.kcl.models.SplitAdvChild;
import com.deepak.kcl.models.SplitAdvHeader;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SplitAdvanActivity extends AppCompatActivity {

    Toolbar toolbar;
    ImageButton imgbtnAdd;
    DatePickerDialog datePickerDialog;
    int year;
    int month;
    int dayOfMonth;
    Calendar calendar;

    private RecyclerView splitAdvRecyclerView;
    private SplitAdvanceAdapter splitAdvanceAdapter;
    private List<SplitAdvHeader> splitAdvanceHeader;
    List<SplitAdvChild> splitAdvanceChildList;
    BranchTrips branchTrips;
    TextView txtlrnumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_split_advan);
        initView();
    }

    private void initView() {
        toolbar = findViewById(R.id.splitAdvanceToolbar);
        //imgbtnAdd = findViewById(R.id.split_adv_imgBtnAdd);
        splitAdvRecyclerView = findViewById(R.id.splitAdvRecyclerView);
        txtlrnumber = findViewById(R.id.split_advance_txtlrno);
        initializeView();
    }

    private void initializeView() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);

        branchTrips = SharedPrefManager.getInstance(this).getBranchTrips();
        txtlrnumber.setText(branchTrips.getLR());

        getsplitadvanceHeader();
        splitAdvanceAdapter = new SplitAdvanceAdapter(splitAdvanceHeader,this);
        splitAdvRecyclerView.setLayoutManager(new LinearLayoutManager(SplitAdvanActivity.this));
        splitAdvRecyclerView.setAdapter(splitAdvanceAdapter);

      /*  imgbtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenAddExpenseDialog();
            }
        });*/
    }

    private void getsplitadvanceHeader() {
        splitAdvanceHeader = new ArrayList<>(5);
        for(int i = 0; i < 5; i++)
        {
            splitAdvanceChildList = new ArrayList<>();
            if(i==0) {
                splitAdvanceChildList.add(new SplitAdvChild("05 June'19","FUEL","Rs.10000/-","HO","Fuel Charge Day1"));
                splitAdvanceChildList.add(new SplitAdvChild("05 June'19","FUEL","Rs.5000/-","HO","Fuel Charge Day2"));
                splitAdvanceHeader.add(new SplitAdvHeader("IOCL", splitAdvanceChildList));
            }
            if(i==1) {
                splitAdvanceChildList.add(new SplitAdvChild("05 June'19","FUEL","Rs.10000/-","HO","Fuel Charge Day1"));
                splitAdvanceChildList.add(new SplitAdvChild("05 June'19","FUEL","Rs.5000/-","HO","Fuel Charge Day2"));
                splitAdvanceHeader.add(new SplitAdvHeader("CASH", splitAdvanceChildList));
            }
            if(i==2) {
                splitAdvanceChildList.add(new SplitAdvChild("05 June'19","FUEL","Rs.10000/-","HO","Fuel Charge Day1"));
                splitAdvanceChildList.add(new SplitAdvChild("05 June'19","FUEL","Rs.5000/-","HO","Fuel Charge Day2"));
                splitAdvanceHeader.add(new SplitAdvHeader("BANK", splitAdvanceChildList));
            }
            if(i==3) {
                splitAdvanceChildList.add(new SplitAdvChild("05 June'19","FUEL","Rs.10000/-","HO","Fuel Charge Day1"));
                splitAdvanceChildList.add(new SplitAdvChild("05 June'19","FUEL","Rs.5000/-","HO","Fuel Charge Day2"));
                splitAdvanceHeader.add(new SplitAdvHeader("HAPPAY", splitAdvanceChildList));
            }
            if(i==4) {
                splitAdvanceChildList.add(new SplitAdvChild("05 June'19","FUEL","Rs.10000/-","HO","Fuel Charge Day1"));
                splitAdvanceChildList.add(new SplitAdvChild("05 June'19","FUEL","Rs.5000/-","HO","Fuel Charge Day2"));
                splitAdvanceHeader.add(new SplitAdvHeader("OTHERS", splitAdvanceChildList));
            }
        }
    }

    private void OpenAddExpenseDialog() {
        Button btnSave,btnCancel;
        ImageButton imgBtnClose,imgBtnDate;
        Spinner spnBranch,spnSplit;
        EditText edtDate;
        TextView txtHeader;

        final Dialog dialog=new Dialog(SplitAdvanActivity.this);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_expense);
        if (dialog.getWindow()!=null)
        {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
            WindowManager.LayoutParams params=dialog.getWindow().getAttributes();
            params.gravity= Gravity.CENTER_VERTICAL;
        }

        imgBtnClose = dialog.findViewById(R.id.dialog_exp_imgBtnClose);
        btnSave = dialog.findViewById(R.id.dialog_exp_btnSave);
        btnCancel = dialog.findViewById(R.id.dialog_exp_btnCancel);
        spnBranch = dialog.findViewById(R.id.dialog_exp_spnBranch);
        spnSplit = dialog.findViewById(R.id.dialog_exp_spnExpense);
        imgBtnDate = dialog.findViewById(R.id.dialog_exp_imgBtnDate);
        edtDate = dialog.findViewById(R.id.dialog_exp_edtDate);
        txtHeader = dialog.findViewById(R.id.dialog_exp_header);

        txtHeader.setText("Split Advance");

        edtDate.setKeyListener(null);

        imgBtnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(SplitAdvanActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                edtDate.setText(day + "/" + (month + 1) + "/" + year);
                            }
                        }, year, month, dayOfMonth);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.Branches, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnBranch.setAdapter(adapter);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(
                this, R.array.SplitType, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnSplit.setAdapter(adapter1);

        imgBtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        dialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
