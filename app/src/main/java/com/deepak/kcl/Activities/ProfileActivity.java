package com.deepak.kcl.Activities;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.FaceDetector;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.deepak.kcl.Networking.RetrofitClient;
import com.deepak.kcl.R;
import com.deepak.kcl.Storage.SharedPrefManager;
import com.deepak.kcl.Utils.Common;
import com.deepak.kcl.models.LoginResponse;
import com.deepak.kcl.models.User;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

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
    ImageView imgUpload;
    Bitmap bitmap;
    private static final String IMAGE_DIRECTORY = "/KCL_IMAGE";
    private int GALLERY = 1, CAMERA = 2;
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
        imgProfile = findViewById(R.id.dialog_upload_imgUpload);

        initializeView();
    }

    private void initializeView() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);

        user = SharedPrefManager.getInstance(this).getUser();
        edtUpdName.setText(user.getFull_name());
        edtUpdEmail.setText(user.getEmail());
        edtUpdMobile.setText(user.getMobile());
        edtIMEI1.setText(user.getIMEI1());
        edtIMEI2.setText(user.getIMEI2());

        Picasso.with(this)
                .load(Common.Image_url+user.getU_img())
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .placeholder(R.mipmap.profile_placeholder)
                .into(imgProfile);

        btnPhotoUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenUploadImageDialog();
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

        /*if(IMEI1.isEmpty()){
            edtIMEI1.setError("IMEI1 is Required");
            edtIMEI1.requestFocus();
            return;
        }*/

        /*if(!IMEI1.isEmpty() && IMEI1.length() < 15)
        {
            edtIMEI1.setError("IMEI number lenght is minimum 15");
            edtIMEI1.requestFocus();
            return;
        }

        if(!IMEI2.isEmpty() &&IMEI2.length() < 15)
        {
            edtIMEI2.setError("IMEI number lenght is minimum 15");
            edtIMEI2.requestFocus();
            return;
        }*/

        Call<LoginResponse> call = RetrofitClient.getInstance().getApi().updateUser(user.getUser_id(),nm,email,mob,IMEI1,IMEI2);
        
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

    private void OpenUploadImageDialog() {
        Button btnChoose,btnUpload;
        ImageButton imgBtnClose;

        final Dialog dialog=new Dialog(ProfileActivity.this);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_upload_image);
        if (dialog.getWindow()!=null)
        {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
            WindowManager.LayoutParams params=dialog.getWindow().getAttributes();
            params.gravity= Gravity.CENTER_VERTICAL;
        }

        imgBtnClose = dialog.findViewById(R.id.dialog_upload_imgbtnclose);
        btnChoose = dialog.findViewById(R.id.dialog_upload_btnChoose);
        btnUpload = dialog.findViewById(R.id.dialog_upload_btnUpload);
        imgUpload = dialog.findViewById(R.id.dialog_upload_imgUpload);
        btnUpload.setEnabled(false);

        imgBtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage();
                dialog.cancel();

            }
        });

        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //openGallery();
                requestMultiplePermissions();
                showPictureDialog();
                btnUpload.setEnabled(true);
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
                    imgUpload.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(ProfileActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            bitmap = (Bitmap) data.getExtras().get("data");
            imgUpload.setImageBitmap(bitmap);
            saveImage(bitmap);
            Toast.makeText(ProfileActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
        }
    }

    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
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
       ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
       bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
       byte[] imgByte = byteArrayOutputStream.toByteArray();
       return Base64.encodeToString(imgByte,Base64.DEFAULT);
   }

   private void uploadImage()
   {
       String profileImage = imageToString();
       int id = user.getUser_id();

       Call<LoginResponse> call = RetrofitClient.getInstance().getApi().updateProfile(id,profileImage);
       call.enqueue(new Callback<LoginResponse>() {
           @Override
           public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

               Toast.makeText(ProfileActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
               finish();
               startActivity(getIntent());
           }

           @Override
           public void onFailure(Call<LoginResponse> call, Throwable t) {
               Toast.makeText(ProfileActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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
}