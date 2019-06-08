package com.deepak.kcl.Activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import com.deepak.kcl.R;

import java.util.Calendar;

public class SplitAdvanActivity extends AppCompatActivity {

    Toolbar toolbar;
    ImageButton imgbtnAdd;
    DatePickerDialog datePickerDialog;
    int year;
    int month;
    int dayOfMonth;
    Calendar calendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_split_advan);
        initView();
    }

    private void initView() {
        toolbar = findViewById(R.id.splitAdvanceToolbar);
        imgbtnAdd = findViewById(R.id.split_adv_imgBtnAdd);
        initializeView();
    }

    private void initializeView() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);

        imgbtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenAddExpenseDialog();
            }
        });
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
        spnSplit = dialog.findViewById(R.id.dialog_exp_spnSplit);
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
