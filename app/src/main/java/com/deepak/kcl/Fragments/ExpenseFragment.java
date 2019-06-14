package com.deepak.kcl.Fragments;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.deepak.kcl.Networking.RetrofitClient;
import com.deepak.kcl.R;
import com.deepak.kcl.models.Branch;
import com.deepak.kcl.models.BranchResponse;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExpenseFragment extends Fragment {

    View view;
    ImageButton imgBtnAdd;
    DatePickerDialog datePickerDialog;
    int year;
    int month;
    int dayOfMonth;
    Calendar calendar;
    private List<Branch> branchList;

    public ExpenseFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_expense, container, false);
        initView();
        return view;
    }

    private void initView() {
        imgBtnAdd = view.findViewById(R.id.exp_imgbtn_add);
        initializeView();
    }

    private void initializeView() {

        imgBtnAdd.setOnClickListener(new View.OnClickListener() {
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


        final Dialog dialog=new Dialog(getActivity());
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
        edtDate.setKeyListener(null);

        imgBtnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(getActivity(),
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

        Call<BranchResponse> call = RetrofitClient.getInstance().getApi().getAllBranch();

        call.enqueue(new Callback<BranchResponse>() {
            @Override
            public void onResponse(Call<BranchResponse> call, Response<BranchResponse> response) {
                branchList = response.body().getBranches();

                Toast.makeText(getActivity(), branchList.toString(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<BranchResponse> call, Throwable t) {

            }
        });


       /* ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, branchNames);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        spnBranch.setAdapter(spinnerArrayAdapter);*/
/*
     ArrayAdapter<Branch> spinnerArrayAdapter = new ArrayAdapter<Branch>(getActivity(), android.R.layout.simple_spinner_item, branchList);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        spnBranch.setAdapter(spinnerArrayAdapter);*/

    /*  ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getActivity(), R.array.Branches, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnBranch.setAdapter(adapter);*/

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(
                getActivity(), R.array.SplitType, android.R.layout.simple_spinner_item);
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

}
