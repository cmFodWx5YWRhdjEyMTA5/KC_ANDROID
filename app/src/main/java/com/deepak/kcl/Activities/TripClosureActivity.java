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

import com.deepak.kcl.Adapter.LoadingRecyclerView;
import com.deepak.kcl.Adapter.TripExpenseRecylerView;
import com.deepak.kcl.Networking.RetrofitClient;
import com.deepak.kcl.R;
import com.deepak.kcl.Storage.SharedPrefManager;
import com.deepak.kcl.Utils.Common;
import com.deepak.kcl.Utils.TotalExpense;
import com.deepak.kcl.models.Branch;
import com.deepak.kcl.models.ExpenseType;
import com.deepak.kcl.models.ExpenseTypeResponse;
import com.deepak.kcl.models.LoadingDetails;
import com.deepak.kcl.models.LoadingResponse;
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
    ImageButton imgBtnAdd,imgBtnLoadingAdd;
    private static int RESULT_LOAD_IMAGE = 1;
    Bitmap bitmap;
    ImageView imgUploadview,imgUploadLoading;
    private List<ExpenseType> expenseList;
    private List<TripExpense> tripExpenses;
    private List<LoadingDetails> loadingDetails;
    private ArrayList<String> expenseNames = new ArrayList<String>();
    ArrayAdapter<String> spinnerArrayAdapter;
    Button btnChoose,btnSave,btnLoadChoose,btnLoadSave;
    ImageButton imgBtnClose,imgBtnLoadingClose;
    EditText edtExpAmount,edtLoadQty;
    Spinner spnExpenseType,spnLoadingType;
    User user;
    int flag = 0;
    RecyclerView recyclerView;
    RecyclerView recyclerViewLoading;
    TripExpenseRecylerView adapter;
    LoadingRecyclerView loadingAdapter;
    TextView txtTotalExpense,txtEmptyView,txtLoadingEmptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_closure);
        initView();
    }

    private void initView() {
        toolbar = findViewById(R.id.closureToolbar);
        imgBtnAdd = findViewById(R.id.tripclosure_imgbtnadd);
        imgBtnLoadingAdd = findViewById(R.id.tripclosure_imgbtnLoading);
        recyclerView = findViewById(R.id.recyclerview_add_exp);
        txtTotalExpense = findViewById(R.id.txt_total_tripExpense);
        txtEmptyView = findViewById(R.id.txtMsgEmptyView);
        txtLoadingEmptyView = findViewById(R.id.txtLoadingEmptyView);
        recyclerViewLoading = findViewById(R.id.recyclerView_loading);

        initializeView();
    }

    private void initializeView() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);

        user = SharedPrefManager.getInstance(this).getUser();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewLoading.setLayoutManager(new LinearLayoutManager(this));

        imgBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenAddExpense();
            }
        });

        imgBtnLoadingAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenLoadingDetails();
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

        fillLoading();
        fillRecyclerView();
    }

    private void OpenLoadingDetails() {
        final Dialog dialog=new Dialog(TripClosureActivity.this);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_loading_details);

        if (dialog.getWindow()!=null)
        {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
            WindowManager.LayoutParams params=dialog.getWindow().getAttributes();
            params.gravity= Gravity.CENTER_VERTICAL;
        }

        imgBtnLoadingClose = dialog.findViewById(R.id.dialog_loading_btnclose);
        btnLoadChoose = dialog.findViewById(R.id.dialog_loading_btnchoose);
        btnLoadSave = dialog.findViewById(R.id.dialog_loading_btnSave);
        edtLoadQty = dialog.findViewById(R.id.dialog_loading_edtQty);
        imgUploadLoading = dialog.findViewById(R.id.dialog_loading_imgview);
        spnLoadingType = dialog.findViewById(R.id.dialog_loading_spnchoose);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.LoadingType, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnLoadingType.setAdapter(adapter);


        imgBtnLoadingClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        btnLoadChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 1;
                openGallery();
            }
        });

        btnLoadSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtLoadQty.getText().toString().trim().isEmpty())
                {
                    edtLoadQty.setError("Quantity is Required");
                    edtLoadQty.requestFocus();
                    return;
                }else {
                    AddLoadingDetail();
                    dialog.cancel();
                }
            }
        });
        dialog.show();
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

    private void fillLoading() {

        Call<LoadingResponse> call = RetrofitClient.getInstance().getApi().getLoadUnload(1110);
        call.enqueue(new Callback<LoadingResponse>() {
            @Override
            public void onResponse(Call<LoadingResponse> call, Response<LoadingResponse> response) {

                loadingDetails = response.body().getLoad_unload();
                if(loadingDetails.size() == 0){
                    recyclerViewLoading.setVisibility(View.INVISIBLE);
                    txtLoadingEmptyView.setVisibility(View.VISIBLE);
                }else{
                    Toast.makeText(TripClosureActivity.this, "Hello", Toast.LENGTH_SHORT).show();
                    recyclerViewLoading.setVisibility(View.VISIBLE);
                    txtLoadingEmptyView.setVisibility(View.GONE);
                    loadingAdapter = new LoadingRecyclerView(TripClosureActivity.this,loadingDetails);
                    recyclerViewLoading.setAdapter(loadingAdapter);
                    loadingAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<LoadingResponse> call, Throwable t) {
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
                if(flag == 0)
                {
                    imgUploadview.setImageBitmap(bitmap);
                }else
                {
                    imgUploadLoading.setImageBitmap(bitmap);
                    flag = 0;
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

    private void AddLoadingDetail() {

        final int min = 1500;
        final int max = 9000;
        final int random = new Random().nextInt((max - min) + 1) + min;

        int id = user.getUid();
        int tripId = 1110;
        String LoadingType = spnLoadingType.getSelectedItem().toString();
        String imgName = tripId+"_"+LoadingType+"_"+random;
        String LoadingQuantity = edtLoadQty.getText().toString().trim();
        String LoadingImage = imageToString();

        Call<LoadingResponse> call = RetrofitClient.getInstance().getApi().createLoadingDetail(id,tripId,LoadingType,LoadingQuantity,LoadingImage,imgName);
        call.enqueue(new Callback<LoadingResponse>() {
            @Override
            public void onResponse(Call<LoadingResponse> call, Response<LoadingResponse> response) {
                Toast.makeText(TripClosureActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                fillLoading();
            }

            @Override
            public void onFailure(Call<LoadingResponse> call, Throwable t) {
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
