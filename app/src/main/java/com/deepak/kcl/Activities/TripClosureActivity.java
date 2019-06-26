package com.deepak.kcl.Activities;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.deepak.kcl.Adapter.TripExpenseRecylerView;
import com.deepak.kcl.Networking.RetrofitClient;
import com.deepak.kcl.R;
import com.deepak.kcl.Storage.SharedPrefManager;
import com.deepak.kcl.Utils.Common;
import com.deepak.kcl.Utils.TotalExpense;
import com.deepak.kcl.models.Branch;
import com.deepak.kcl.models.ExpenseType;
import com.deepak.kcl.models.ExpenseTypeResponse;
import com.deepak.kcl.models.TripExpense;
import com.deepak.kcl.models.TripExpenseResponse;
import com.deepak.kcl.models.User;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TripClosureActivity extends AppCompatActivity implements TotalExpense {

    Toolbar toolbar;
    ImageButton imgBtnAdd,imgBtnLoded,imgBtnUnloded;
    ImageView imgLoded,imgUnLoded;
    private static int RESULT_LOAD_IMAGE = 1;
    int flag = 0, flag1 = 0;
    Bitmap bitmap;
    ImageView imgUploadview;
    private List<ExpenseType> expenseList;
    private List<TripExpense> tripExpenses;
    private ArrayList<String> expenseNames = new ArrayList<String>();
    ArrayAdapter<String> spinnerArrayAdapter;
    Button btnChoose,btnSave;
    ImageButton imgBtnClose;
    EditText edtExpAmount;
    Spinner spnExpenseType;
    User user;
    RecyclerView recyclerView;
    TripExpenseRecylerView adapter;
    TextView txtTotalExpense,txtEmptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_closure);
        initView();
    }

    private void initView() {
        toolbar = findViewById(R.id.closureToolbar);
        imgBtnAdd = findViewById(R.id.tripclosure_imgbtnadd);
        imgBtnLoded = findViewById(R.id.imgbtnloded);
        imgBtnUnloded = findViewById(R.id.imgbtnunloded);
        imgLoded = findViewById(R.id.imgLoded);
        imgUnLoded = findViewById(R.id.imgUnloded);
        recyclerView = findViewById(R.id.recyclerview_add_exp);
        txtTotalExpense = findViewById(R.id.txt_total_tripExpense);
        txtEmptyView = findViewById(R.id.txtMsgEmptyView);

        initializeView();
    }

    private void initializeView() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);

        user = SharedPrefManager.getInstance(this).getUser();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        imgBtnLoded.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        imgBtnUnloded.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 1;
                openGallery();
            }
        });

        imgBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenAddExpense();
            }
        });

        Call<ExpenseTypeResponse> call = RetrofitClient.getInstance().getApi().getExpenseType();
        call.enqueue(new Callback<ExpenseTypeResponse>() {
            @Override
            public void onResponse(Call<ExpenseTypeResponse> call, Response<ExpenseTypeResponse> response) {
                expenseNames.clear();
                expenseList = response.body().getExpenses();

                for (int i = 0; i < expenseList.size(); i++){
                    expenseNames.add(expenseList.get(i).getExpense_type().toString());
                }
            }

            @Override
            public void onFailure(Call<ExpenseTypeResponse> call, Throwable t) {
                Toast.makeText(TripClosureActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        fillRecyclerView();
    }

    private void fillRecyclerView() {

        Call<TripExpenseResponse> call = RetrofitClient.getInstance().getApi().getTripExpenses(1110);
        call.enqueue(new Callback<TripExpenseResponse>() {
            @Override
            public void onResponse(Call<TripExpenseResponse> call, Response<TripExpenseResponse> response) {
                tripExpenses = response.body().getTripExpense();
                if(tripExpenses.size() == 0){
                    recyclerView.setVisibility(View.GONE);
                    txtEmptyView.setVisibility(View.VISIBLE);
                }else{
                    recyclerView.setVisibility(View.VISIBLE);
                    txtEmptyView.setVisibility(View.GONE);
                    adapter = new TripExpenseRecylerView(TripClosureActivity.this,tripExpenses);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<TripExpenseResponse> call, Throwable t) {
                Toast.makeText(TripClosureActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void openGallery()
    {
        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(i, RESULT_LOAD_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            try{
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),selectedImage);
                if(flag1 == 0) {
                    if (flag == 1) {
                        imgUnLoded.setImageBitmap(bitmap);
                        flag = 0;
                    } else {
                        imgLoded.setImageBitmap(bitmap);
                    }
                }else{
                    imgUploadview.setImageBitmap(bitmap);
                    flag1=0;
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    private String imageToString()
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imgByte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgByte,Base64.DEFAULT);
    }

    private void OpenAddExpense() {

        final Dialog dialog=new Dialog(TripClosureActivity.this);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_add_expense);
        if (dialog.getWindow()!=null)
        {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
            WindowManager.LayoutParams params=dialog.getWindow().getAttributes();
            params.gravity= Gravity.CENTER_VERTICAL;
        }

        imgBtnClose = dialog.findViewById(R.id.dialog_addexp_imgBtnClose);
        btnChoose = dialog.findViewById(R.id.dialog_addexp_btnchoose);
        btnSave = dialog.findViewById(R.id.dialog_addexp_btnSave);
        imgUploadview = dialog.findViewById(R.id.dialog_addexp_imgUpload);
        edtExpAmount = dialog.findViewById(R.id.dialog_addexp_edtAmount);
        spnExpenseType = dialog.findViewById(R.id.dialog_addexp_spnExpenseType);

        spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, expenseNames);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        spnExpenseType.setAdapter(spinnerArrayAdapter);


        imgBtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtExpAmount.getText().toString().trim().isEmpty())
                {
                    edtExpAmount.setError("Amount is Required");
                    edtExpAmount.requestFocus();
                    return;
                }else {
                    AddTripExpense();
                    dialog.cancel();
                }
            }
        });

        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag1 = 1;
                openGallery();
            }
        });

        dialog.show();
    }

    private void AddTripExpense() {

        final int min = 2000;
        final int max = 8000;
        final int random = new Random().nextInt((max - min) + 1) + min;

        int id = user.getUid();
        int tripId = 1110;
        String imgName = tripId+"_"+random;
        String ExpenseType = spnExpenseType.getSelectedItem().toString();
        String ExpenseAmount = edtExpAmount.getText().toString().trim();
        String ExpenseImage = imageToString();

        Call<TripExpenseResponse> call = RetrofitClient.getInstance().getApi().createTripExpense(id,tripId,ExpenseType,ExpenseAmount,ExpenseImage,imgName);
        call.enqueue(new Callback<TripExpenseResponse>() {
            @Override
            public void onResponse(Call<TripExpenseResponse> call, Response<TripExpenseResponse> response) {
                Toast.makeText(TripClosureActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                fillRecyclerView();
            }

            @Override
            public void onFailure(Call<TripExpenseResponse> call, Throwable t) {
                Toast.makeText(TripClosureActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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

    @Override
    public void updateExpenseAmount(String amount) {
        txtTotalExpense.setText("Rs."+amount+"/-");
    }


}
