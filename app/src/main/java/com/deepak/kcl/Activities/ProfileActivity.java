package com.deepak.kcl.Activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.deepak.kcl.Networking.RetrofitClient;
import com.deepak.kcl.R;
import com.deepak.kcl.Storage.SharedPrefManager;
import com.deepak.kcl.models.LoginResponse;
import com.deepak.kcl.models.User;

import java.io.FileDescriptor;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    Toolbar toolbar;
    ImageButton btnPhotoUpload;
    CircleImageView imgProfile;
    private static int RESULT_LOAD_IMAGE = 1;
    EditText edtUpdName,edtUpdMobile,edtUpdEmail,edtIMEI1,edtIMEI2;
    Button btnUpdate,btnCancel;
    String nm,mob,email,IMEI1,IMEI2;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(!SharedPrefManager.getInstance(this).isLoggedIn()){
            Intent intent = new Intent(ProfileActivity.this,LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    private void initView() {
        toolbar = findViewById(R.id.profileToolbar);
        edtUpdName = findViewById(R.id.edt_upd_name);
        edtUpdMobile = findViewById(R.id.edt_upd_mobile);
        edtUpdEmail = findViewById(R.id.edt_upd_email);
        edtIMEI1 = findViewById(R.id.edt_upd_imei1);
        edtIMEI2 = findViewById(R.id.edt_upd_imei2);
        btnUpdate = findViewById(R.id.btn_upd_update);
        btnCancel = findViewById(R.id.btn_upd_cancel);
        btnPhotoUpload = findViewById(R.id.btn_photo_upload);
        imgProfile = findViewById(R.id.img_profile);

        initializeView();
    }

    private void initializeView() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);

        user = SharedPrefManager.getInstance(this).getUser();
        edtUpdName.setText(user.getUname());
        edtUpdEmail.setText(user.getUemail());
        edtUpdMobile.setText(user.getUmobile());
        edtIMEI1.setText(user.getUimei_no1());
        edtIMEI2.setText(user.getUimei_no2());

        btnPhotoUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               updateProfile();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void updateProfile() {
        nm = edtUpdName.getText().toString().trim();
        mob = edtUpdMobile.getText().toString().trim();
        email = edtUpdEmail.getText().toString().trim();
        IMEI1 = edtIMEI1.getText().toString().trim();
        IMEI2 = edtIMEI2.getText().toString().trim();


        if(nm.isEmpty()){
            edtUpdName.setError("Name is Required");
            edtUpdName.requestFocus();
            return;
        }

        if(email.isEmpty()){
            edtUpdEmail.setError("Email is Required");
            edtUpdEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            edtUpdEmail.setError("Enter valid Email.");
            edtUpdEmail.requestFocus();
            return;
        }

        if(mob.isEmpty()){
            edtUpdMobile.setError("Mobile No. is Required");
            edtUpdMobile.requestFocus();
            return;
        }

        if(mob.length() < 10){
            edtUpdMobile.setError("Enter Valid Mobile No.");
            edtUpdMobile.requestFocus();
            return;
        }

        if(IMEI1.isEmpty()){
            edtIMEI1.setError("IMEI1 is Required");
            edtIMEI1.requestFocus();
            return;
        }

        if(IMEI1.length() < 15)
        {
            edtIMEI1.setError("IMEI number lenght is minimum 15");
            edtIMEI1.requestFocus();
            return;
        }

        if(IMEI2.length() < 15)
        {
            edtIMEI2.setError("IMEI number lenght is minimum 15");
            edtIMEI2.requestFocus();
            return;
        }
        Log.d("mob",String.valueOf(user.getUid()));
        Log.d("mob",nm);
        Log.d("mob",email);
        Log.d("mob",mob);
        Log.d("mob",IMEI1);
        Log.d("mob",IMEI2);


        Call<LoginResponse> call = RetrofitClient.getInstance().getApi().updateUser(user.getUid(),nm,email,mob,IMEI1,IMEI2);
        
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse updateResponse = response.body();

                Toast.makeText(ProfileActivity.this, updateResponse.getMessage(), Toast.LENGTH_SHORT).show();

                if(!updateResponse.isError()) {
                    SharedPrefManager.getInstance(ProfileActivity.this).saveuser(updateResponse.getUser());
                    finish();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(ProfileActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void openGallery()
    {
        Intent i = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);

        startActivityForResult(i, RESULT_LOAD_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            Bitmap bmp = null;
            try {
                bmp = getBitmapFromUri(selectedImage);
            } catch (IOException e) {
                e.printStackTrace();
            }

            imgProfile.setImageBitmap(bmp);
        }
    }

    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor = getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}