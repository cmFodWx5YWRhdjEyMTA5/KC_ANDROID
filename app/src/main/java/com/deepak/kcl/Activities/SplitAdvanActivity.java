package com.deepak.kcl.Activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import android.widget.Toast;

import com.deepak.kcl.Networking.RetrofitClient;
import com.deepak.kcl.R;
import com.deepak.kcl.Storage.SharedPrefManager;
import com.deepak.kcl.ViewHolders.SplitAdvHeadViewHolder;
import com.deepak.kcl.models.BranchTrips;
import com.deepak.kcl.models.SplitAdvChild;
import com.deepak.kcl.models.SplitAdvHead;
import com.deepak.kcl.models.SplitAdvHeadResponse;
import com.deepak.kcl.models.SplitAdvHeader;
import com.deepak.kcl.models.User;
import com.mindorks.placeholderview.ExpandablePlaceHolderView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplitAdvanActivity extends AppCompatActivity {


    private Map<String,List<SplitAdvHead>> mHeading;
    private List<SplitAdvHead> advanceList;
    private ExpandablePlaceHolderView expandablePlaceHolderView;
    Toolbar toolbar;
    ImageButton imgbtnAdd;
    DatePickerDialog datePickerDialog;
    int year;
    int month;
    int dayOfMonth;
    Calendar calendar;
    User user;

    private RecyclerView splitAdvRecyclerView;
    //private SplitAdvanceAdapter splitAdvanceAdapter;
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
        imgbtnAdd = findViewById(R.id.heading_btn_splitAdd);
        expandablePlaceHolderView = findViewById(R.id.splitAdvRecyclerView);
        txtlrnumber = findViewById(R.id.split_advance_txtlrno);
        initializeView();
    }

    private void initializeView() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);

        branchTrips = SharedPrefManager.getInstance(this).getBranchTrips();
        user = SharedPrefManager.getInstance(this).getUser();
        txtlrnumber.setText(branchTrips.getLR());

        advanceList = new ArrayList<>();
        mHeading = new HashMap<>();

        loadData();

       /* getsplitadvanceHeader();
        splitAdvanceAdapter = new SplitAdvanceAdapter(splitAdvanceHeader,this);
        splitAdvRecyclerView.setLayoutManager(new LinearLayoutManager(SplitAdvanActivity.this));
        splitAdvRecyclerView.setAdapter(splitAdvanceAdapter);*/

       imgbtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenAddExpenseDialog();
            }
        });
    }

    private void loadData() {

        Call<SplitAdvHeadResponse> call = RetrofitClient.getInstance().getApi().getSplitAdvHeading(branchTrips.getLR());
        call.enqueue(new Callback<SplitAdvHeadResponse>() {
            @Override
            public void onResponse(Call<SplitAdvHeadResponse> call, Response<SplitAdvHeadResponse> response) {
                advanceList = response.body().getSplitAdvHeading();
                getHeaderAndChild(advanceList);
            }

            @Override
            public void onFailure(Call<SplitAdvHeadResponse> call, Throwable t) {
                Toast.makeText(SplitAdvanActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getHeaderAndChild(List<SplitAdvHead> splitAdvHeads){

        for (SplitAdvHead splitHead : splitAdvHeads ){
            List<SplitAdvHead> splitAdvHeads1 = mHeading.get(splitHead.getTrip_exp_type());
            if(splitAdvHeads1 == null){
                splitAdvHeads1 = new ArrayList<>();
            }
            splitAdvHeads1.add(splitHead);
            mHeading.put(splitHead.getTrip_exp_type(),splitAdvHeads1);
        }

        Log.d("Map",mHeading.toString());
        Iterator it = mHeading.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            Log.d("Key", pair.getKey().toString());
            List<SplitAdvHead> splitAdvances2 = (List<SplitAdvHead>) pair.getValue();
           expandablePlaceHolderView.addView(new SplitAdvHeadViewHolder(this, pair.getKey().toString(),"Rs."+splitAdvances2.get(0).getAmount()));
           /* for (SplitAdvHead tripAdvance : splitAdvances2){
                expandablePlaceHolderView.addView(new SplitAdvItemViewHolder(this, tripAdvance));
            }*/
            it.remove();
        }
    }

    /*private void getsplitadvanceHeader() {
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
    }*/

    private void OpenAddExpenseDialog() {
        Button btnSave,btnCancel;
        ImageButton imgBtnClose,imgBtnDate;
        Spinner spnSplit;
        EditText edtBranch;
        EditText edtDate,edtAmount,edtDesc;

        final Dialog dialog=new Dialog(SplitAdvanActivity.this);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.split_add_dialog);
        if (dialog.getWindow()!=null)
        {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
            WindowManager.LayoutParams params=dialog.getWindow().getAttributes();
            params.gravity= Gravity.CENTER_VERTICAL;
        }

        imgBtnClose = dialog.findViewById(R.id.add_split_imgBtnClose);
        btnSave = dialog.findViewById(R.id.add_split_btnSave);
        btnCancel = dialog.findViewById(R.id.add_split_btnCancel);
        edtBranch = dialog.findViewById(R.id.add_split_edtBranch);
        spnSplit = dialog.findViewById(R.id.add_split_spnType);
        imgBtnDate = dialog.findViewById(R.id.add_split_imgBtnDate);
        edtDate = dialog.findViewById(R.id.add_split_edtDate);
        edtAmount = dialog.findViewById(R.id.add_split_edtAmount);
        edtDesc = dialog.findViewById(R.id.add_split_edtDesc);


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

        edtBranch.setText(user.getBranch_name());
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(
                this, R.array.SplitType, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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
