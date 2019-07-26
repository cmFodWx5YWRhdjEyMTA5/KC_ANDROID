package com.deepak.kcl.Activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
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

import com.deepak.kcl.Adapter.SplitAdvanceAdapter;
import com.deepak.kcl.Networking.RetrofitClient;
import com.deepak.kcl.R;
import com.deepak.kcl.Storage.SharedPrefManager;
import com.deepak.kcl.models.BranchTrips;
import com.deepak.kcl.models.SplitAdvData;
import com.deepak.kcl.models.SplitAdvDataResponse;
import com.deepak.kcl.models.SplitAdvHead;
import com.deepak.kcl.models.SplitAdvHeadResponse;
import com.deepak.kcl.models.SplitAdvHeader;
import com.deepak.kcl.models.User;

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


    private Map<String, List<SplitAdvHead>> mHeading;
    private List<SplitAdvHead> advanceList;
    private List<SplitAdvData> advDataList;
    List<SplitAdvHead> splitAdvances2;
    Toolbar toolbar;
    DatePickerDialog datePickerDialog;
    int year;
    int month;
    int dayOfMonth;
    Calendar calendar;
    User user;
    int countHead = 0;
    int advSize = 0;
    boolean flag;

    private RecyclerView splitAdvRecyclerView;
    private SplitAdvanceAdapter splitAdvanceAdapter;
    private List<SplitAdvHeader> splitAdvanceHeader;
    List<SplitAdvData> splitAdvanceChildList;
    BranchTrips branchTrips;
    TextView txtlrnumber;

    Map<String, String> hashMap = new HashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_split_advan);
        initView();
    }

    private void initView() {
        toolbar = findViewById(R.id.splitAdvanceToolbar);
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
        user = SharedPrefManager.getInstance(this).getUser();
        txtlrnumber.setText(branchTrips.getLR());

        advanceList = new ArrayList<>();
        mHeading = new HashMap<>();

        loadData();
    }

    private void loadData() {

        Call<SplitAdvHeadResponse> call = RetrofitClient.getInstance().getApi().getSplitAdvHeading(branchTrips.getLR());
        Call<SplitAdvDataResponse> call1 = RetrofitClient.getInstance().getApi().getSplitAdvData(branchTrips.getLR());
        call.enqueue(new Callback<SplitAdvHeadResponse>() {
            @Override
            public void onResponse(Call<SplitAdvHeadResponse> call, Response<SplitAdvHeadResponse> response) {
                advanceList = response.body().getSplitAdvHeading();

                call1.enqueue(new Callback<SplitAdvDataResponse>() {
                    @Override
                    public void onResponse(Call<SplitAdvDataResponse> call, Response<SplitAdvDataResponse> response) {
                        splitAdvanceChildList = response.body().getSplitAdvanceData();
                        advSize = splitAdvanceChildList.size();
                        Log.d("TestData1", String.valueOf(advSize));
                        getHeaderAndChild(advanceList);
                    }

                    @Override
                    public void onFailure(Call<SplitAdvDataResponse> call, Throwable t) {
                        Toast.makeText(SplitAdvanActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onFailure(Call<SplitAdvHeadResponse> call, Throwable t) {
                Toast.makeText(SplitAdvanActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getHeaderAndChild(List<SplitAdvHead> splitAdvHeads) {

        for (SplitAdvHead splitHead : splitAdvHeads) {
            List<SplitAdvHead> splitAdvHeads1 = mHeading.get(splitHead.getTrip_exp_type());
            if (splitAdvHeads1 == null) {
                splitAdvHeads1 = new ArrayList<>();
            }
            splitAdvHeads1.add(splitHead);
            mHeading.put(splitHead.getTrip_exp_type(), splitAdvHeads1);
        }

        Iterator it = mHeading.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();

            splitAdvances2 = (List<SplitAdvHead>) pair.getValue();
            hashMap.put(pair.getKey().toString(), splitAdvances2.get(0).getAmount());

            it.remove();
        }

        for (String head : hashMap.keySet()) {
            String amount = hashMap.get(head);
            countHead = countHead + 1;
            Log.d("DeepakTest", "Key = " + head + ", Value = " + amount);
        }

        getSplitAdvanceHeader();
    }

    private void getSplitAdvanceHeader() {
        int m = 0;
        int i;
        splitAdvanceHeader = new ArrayList<>(countHead);
        for (String head1 : hashMap.keySet()) {
            String amt = hashMap.get(head1);
            String title = head1+ "  "+"RS."+ amt;
            Log.d("TestData2",head1);
            i=m;
            flag=true;
            for (i = 0; i < advSize; i++) {
                String headType = splitAdvanceChildList.get(i).getSplit_head().toString();
                Log.d("TestData3",head1);
                advDataList = new ArrayList<>();
                if (head1.equals(headType)) {
                    flag=false;
                    Log.d("TestData4",head1);
                    for (int j = 0; j < advSize; j++) {
                        Log.d("TestData5",head1);
                        String head = splitAdvanceChildList.get(j).getSplit_head().toString();
                            if(head.equals(head1)){
                                    advDataList.add(new SplitAdvData(splitAdvanceChildList.get(j).getSplit_date(), splitAdvanceChildList.get(j).getBranch_name(), splitAdvanceChildList.get(j).getSplit_type(),
                                    splitAdvanceChildList.get(j).getAmount(), splitAdvanceChildList.get(j).getDescription(), splitAdvanceChildList.get(j).getSplit_head()));
                        }
                    }
                    splitAdvanceHeader.add(new SplitAdvHeader(title, advDataList));
                    break;
                }
            }
            if(flag==true)
            {
                Log.d("TestData6",head1);
                splitAdvanceHeader.add(new SplitAdvHeader(title, advDataList));
            }
            m=m+1;
        }

        splitAdvanceAdapter = new SplitAdvanceAdapter(splitAdvanceHeader, this);
        splitAdvRecyclerView.setLayoutManager(new LinearLayoutManager(SplitAdvanActivity.this));
        splitAdvRecyclerView.setAdapter(splitAdvanceAdapter);
    }


    public void OpenAddExpenseDialog() {
        Button btnSave, btnCancel;
        ImageButton imgBtnClose, imgBtnDate;
        Spinner spnSplit;
        EditText edtBranch;
        EditText edtDate, edtAmount, edtDesc;

        final Dialog dialog = new Dialog(SplitAdvanActivity.this);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.split_add_dialog);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
            params.gravity = Gravity.CENTER_VERTICAL;
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
