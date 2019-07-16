package com.deepak.kcl.Fragments;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.deepak.kcl.Activities.TripClosureActivity;
import com.deepak.kcl.Adapter.BranchExpenseAdapter;
import com.deepak.kcl.Adapter.TripExpenseRecylerView;
import com.deepak.kcl.Networking.RetrofitClient;
import com.deepak.kcl.R;
import com.deepak.kcl.Storage.SharedPrefManager;
import com.deepak.kcl.models.Branch;
import com.deepak.kcl.models.BranchExpense;
import com.deepak.kcl.models.BranchExpenseResponse;
import com.deepak.kcl.models.BranchResponse;
import com.deepak.kcl.models.TripExpense;
import com.deepak.kcl.models.User;
import com.github.ybq.android.spinkit.SpinKitView;

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
    private ArrayList<String> branchNames = new ArrayList<String>();
    ArrayAdapter<String> spinnerArrayAdapter;
    RecyclerView recyclerBranchExp;
    BranchExpenseAdapter branchExpenseAdapter;
    TextView txtEmptyView;
    User user;
    private List<BranchExpense> branchExpenses;
    Button btnSave,btnCancel;
    ImageButton imgBtnClose,imgBtnDate;
    Spinner spnSplit,spnLrNo,spnExpType;
    EditText spnBranch;
    EditText edtDate,edtAmount,edtDesc;
    TextView txtLrNo,txtExpType;
    SpinKitView progressBar;

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
        imgBtnAdd = view.findViewById(R.id.Branchexp_imgbtn_Add);
        recyclerBranchExp = view.findViewById(R.id.recyclerview_branchExpense);
        txtEmptyView = view.findViewById(R.id.txtBrachExpEmptyView);
        progressBar = view.findViewById(R.id.spin_kit_expense);
        initializeView();
    }

    private void initializeView() {

        user = SharedPrefManager.getInstance(getActivity()).getUser();
        recyclerBranchExp.setLayoutManager(new LinearLayoutManager(getActivity()));
        //recyclerBranchExp.setHasFixedSize(true);

        imgBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenAddExpenseDialog();
            }
        });


        Call<BranchResponse> call = RetrofitClient.getInstance().getApi().getAllBranch();
        call.enqueue(new Callback<BranchResponse>() {
            @Override
            public void onResponse(Call<BranchResponse> call, Response<BranchResponse> response) {
                branchNames.clear();
                branchList = response.body().getBranches();

                for (int i = 0; i < branchList.size(); i++){
                    branchNames.add(branchList.get(i).getBranch_name().toString());
                }
            }

            @Override
            public void onFailure(Call<BranchResponse> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        FillRecyclerView();
    }

    private void FillRecyclerView() {

        progressBar.setVisibility(View.VISIBLE);
        Call<BranchExpenseResponse> call = RetrofitClient.getInstance().getApi().getBranchExpense(user.getUser_id());
        call.enqueue(new Callback<BranchExpenseResponse>() {
            @Override
            public void onResponse(Call<BranchExpenseResponse> call, Response<BranchExpenseResponse> response) {
                branchExpenses = response.body().getBranchExpense();
                if(branchExpenses.size() == 0){
                    recyclerBranchExp.setVisibility(View.GONE);
                    txtEmptyView.setVisibility(View.VISIBLE);
                }else{
                    recyclerBranchExp.setVisibility(View.VISIBLE);
                    txtEmptyView.setVisibility(View.GONE);
                    branchExpenseAdapter = new BranchExpenseAdapter(getActivity(),branchExpenses);
                    recyclerBranchExp.setAdapter(branchExpenseAdapter);
                    branchExpenseAdapter.notifyDataSetChanged();
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<BranchExpenseResponse> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void OpenAddExpenseDialog() {

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
        spnSplit = dialog.findViewById(R.id.dialog_exp_spnExpense);
        txtLrNo = dialog.findViewById(R.id.dialog_bchexp_LRtext);
        txtExpType = dialog.findViewById(R.id.dialog_bchexp_ExpTypetext);
        spnLrNo = dialog.findViewById(R.id.dialog_bchexp_spnLRNo);
        spnExpType = dialog.findViewById(R.id.dialog_bchexp_spnExptype);
        imgBtnDate = dialog.findViewById(R.id.dialog_exp_imgBtnDate);
        edtDate = dialog.findViewById(R.id.dialog_exp_edtDate);
        edtAmount = dialog.findViewById(R.id.dialog_exp_amount);
        edtDesc = dialog.findViewById(R.id.dialog_exp_desc);
        edtDate.setKeyListener(null);

        txtLrNo.setVisibility(View.GONE);
        spnLrNo.setVisibility(View.GONE);
        txtExpType.setVisibility(View.GONE);
        spnExpType.setVisibility(View.GONE);

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
                //datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });


        /*spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, branchNames);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        spnBranch.setAdapter(spinnerArrayAdapter);*/
        spnBranch.setText(user.getBranch_name());

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(
                getActivity(), R.array.SplitType, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnSplit.setAdapter(adapter1);

        spnSplit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String data = spnSplit.getSelectedItem().toString().trim();
                if(data.equals("Trip Expenses")){
                    txtLrNo.setVisibility(View.VISIBLE);
                    spnLrNo.setVisibility(View.VISIBLE);
                    txtExpType.setVisibility(View.VISIBLE);
                    spnExpType.setVisibility(View.VISIBLE);
                }else
                {
                    txtLrNo.setVisibility(View.GONE);
                    spnLrNo.setVisibility(View.GONE);
                    txtExpType.setVisibility(View.GONE);
                    spnExpType.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(
                getActivity(), R.array.LrNumber, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnLrNo.setAdapter(adapter2);

        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(
                getActivity(), R.array.TripExpType, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnExpType.setAdapter(adapter3);

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
                AddBranchExpense();
                dialog.cancel();
            }
        });

        dialog.show();
    }

    private void AddBranchExpense() {

        int id = user.getUser_id();
        String expDate = edtDate.getText().toString().trim();
        String branchId = String.valueOf(user.getBranch_id());
        String expenseId = spnSplit.getSelectedItem().toString().trim();
        String lrNumber = spnLrNo.getSelectedItem().toString().trim();
        String tripExpenseType = spnExpType.getSelectedItem().toString().trim();
        String expAmount = edtAmount.getText().toString().trim();
        String expDesc = edtDesc.getText().toString().trim();

        Call<BranchExpenseResponse> call = RetrofitClient.getInstance().getApi().createBranchExpense(id,expDate,branchId,expenseId,lrNumber,tripExpenseType,expAmount,expDesc);
        call.enqueue(new Callback<BranchExpenseResponse>() {
            @Override
            public void onResponse(Call<BranchExpenseResponse> call, Response<BranchExpenseResponse> response) {
                Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                FillRecyclerView();
            }

            @Override
            public void onFailure(Call<BranchExpenseResponse> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}
