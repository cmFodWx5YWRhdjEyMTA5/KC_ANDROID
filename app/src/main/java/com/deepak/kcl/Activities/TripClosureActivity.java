package com.deepak.kcl.Activities;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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
import android.widget.AdapterView;
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
import com.deepak.kcl.models.BranchTrips;
import com.deepak.kcl.models.ExpenseType;
import com.deepak.kcl.models.ExpenseTypeResponse;
import com.deepak.kcl.models.LoadingDetails;
import com.deepak.kcl.models.LoadingResponse;
import com.deepak.kcl.models.TripExpense;
import com.deepak.kcl.models.TripExpenseResponse;
import com.deepak.kcl.models.User;
import com.github.ybq.android.spinkit.SpinKitView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TripClosureActivity extends AppCompatActivity implements TotalExpense {

    Toolbar toolbar;
    ImageButton imgBtnAdd,imgBtnLoadingAdd;
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
    private static final String IMAGE_DIRECTORY = "/KCL_IMAGE";
    private int GALLERY = 1, CAMERA = 2;
    SpinKitView progressBar1;
    int a;
    BranchTrips branchTrips;
    TextView txtlrnumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_closure);
        initView();
    }

    private void initView() {
        toolbar = findViewById(R.id.closureToolbar);
        txtlrnumber=findViewById(R.id.trip_closure_txtlrno);
        imgBtnAdd = findViewById(R.id.tripclosure_imgbtnadd);
        imgBtnLoadingAdd = findViewById(R.id.tripclosure_imgbtnLoading);
        recyclerView = findViewById(R.id.recyclerview_add_exp);
        txtTotalExpense = findViewById(R.id.txt_total_tripExpense);
        txtEmptyView = findViewById(R.id.txtMsgEmptyView);
        txtLoadingEmptyView = findViewById(R.id.txtLoadingEmptyView);
        recyclerViewLoading = findViewById(R.id.recyclerView_loading);
        progressBar1 = findViewById(R.id.spin_kit1);

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

    private void fillRecyclerView() {

        progressBar1.setVisibility(View.VISIBLE);
        Call<TripExpenseResponse> call = RetrofitClient.getInstance().getApi().getTripExpenses(branchTrips.getTrip_id());
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
                progressBar1.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<TripExpenseResponse> call, Throwable t) {
                Toast.makeText(TripClosureActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void fillLoading() {

        Call<LoadingResponse> call = RetrofitClient.getInstance().getApi().getLoadUnload(branchTrips.getTrip_id());
        call.enqueue(new Callback<LoadingResponse>() {
            @Override
            public void onResponse(Call<LoadingResponse> call, Response<LoadingResponse> response) {

                loadingDetails = response.body().getLoad_unload();
                if(loadingDetails.size() == 0){
                    recyclerViewLoading.setVisibility(View.INVISIBLE);
                    txtLoadingEmptyView.setVisibility(View.VISIBLE);
                }else{
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
                requestMultiplePermissions();
                showPictureDialog();
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

    private void showPictureDialog(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera" };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri selectedImage = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),selectedImage);
                    if(flag == 0)
                    {
                        imgUploadview.setImageBitmap(bitmap);
                    }else
                    {
                        imgUploadLoading.setImageBitmap(bitmap);
                        flag = 0;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(TripClosureActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            bitmap = (Bitmap) data.getExtras().get("data");
            if(flag == 0)
            {
                imgUploadview.setImageBitmap(bitmap);
            }else
            {
                imgUploadLoading.setImageBitmap(bitmap);
                flag = 0;
            }
            saveImage(bitmap);
            Toast.makeText(TripClosureActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
        }
    }

    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::---&gt;" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

    private String imageToString()
    {
        if(bitmap==null){
            return "NO_IMAGE";
        }
        else {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] imgByte = byteArrayOutputStream.toByteArray();
            return Base64.encodeToString(imgByte, Base64.DEFAULT);
        }
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

        spnExpenseType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                a = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
                requestMultiplePermissions();
                showPictureDialog();
            }
        });

        dialog.show();
    }

    private void AddTripExpense() {

        final int min = 2000;
        final int max = 8000;
        final int random = new Random().nextInt((max - min) + 1) + min;

        int id = user.getUser_id();
        int tripId = branchTrips.getTrip_id();
        String imgName = tripId+"_"+random;
        int ExpenseType = a+1;
        String ExpenseAmount = edtExpAmount.getText().toString().trim();
        String ExpenseImage = imageToString();
        if(ExpenseImage == "NO_IMAGE")
        {
            imgName = "No_image";
        }

        Call<TripExpenseResponse> call = RetrofitClient.getInstance().getApi().createTripExpense(id,tripId,ExpenseType,ExpenseAmount,ExpenseImage,imgName);
        call.enqueue(new Callback<TripExpenseResponse>() {
            @Override
            public void onResponse(Call<TripExpenseResponse> call, Response<TripExpenseResponse> response) {
                Toast.makeText(TripClosureActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                fillRecyclerView();
                bitmap=null;
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

        int id = user.getUser_id();
        int tripId = branchTrips.getTrip_id();
        String LoadingType = spnLoadingType.getSelectedItem().toString();
        String imgName = tripId+"_"+LoadingType+"_"+random;
        String LoadingQuantity = edtLoadQty.getText().toString().trim();
        String LoadingImage = imageToString();
        if(LoadingImage == "NO_IMAGE")
        {
            imgName = "No_image";
        }

        Call<LoadingResponse> call = RetrofitClient.getInstance().getApi().createLoadingDetail(id,tripId,LoadingType,LoadingQuantity,LoadingImage,imgName);
        call.enqueue(new Callback<LoadingResponse>() {
            @Override
            public void onResponse(Call<LoadingResponse> call, Response<LoadingResponse> response) {
                Toast.makeText(TripClosureActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                fillLoading();
                bitmap=null;
            }

            @Override
            public void onFailure(Call<LoadingResponse> call, Throwable t) {
                Toast.makeText(TripClosureActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void  requestMultiplePermissions(){
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            //Toast.makeText(getApplicationContext(), "All permissions are granted by user!", Toast.LENGTH_SHORT).show();
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            //openSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getApplicationContext(), "Some Error! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
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
