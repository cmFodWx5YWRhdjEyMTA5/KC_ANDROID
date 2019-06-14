package com.deepak.kcl.Activities;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.deepak.kcl.Networking.RetrofitClient;
import com.deepak.kcl.R;
import com.deepak.kcl.Storage.SharedPrefManager;
import com.deepak.kcl.models.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin;
    TextView txtImeinum;
    TelephonyManager telephonyManager;
    private int STORAGE_PERMISSION_CODE = 1;
    String IMEI;
    TextInputEditText txtLoginUnm,txtLoginUpass;
    String unm,upass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    private void initView() {
        btnLogin = findViewById(R.id.btnlogin);
        txtImeinum = findViewById(R.id.txtImeiNum);
        txtLoginUnm = findViewById(R.id.txtloginUname);
        txtLoginUpass = findViewById(R.id.txtLoginUpass);
        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        initialize();
    }

    private void initialize() {

        getImei();

        txtImeinum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getImeiNumber();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserLogin();
            }
        });
    }

    private void UserLogin() {
        unm = txtLoginUnm.getText().toString().trim();
        upass = txtLoginUpass.getText().toString().trim();
        getImei();

        if(unm.isEmpty()){
            txtLoginUnm.setError("Email is Required");
            txtLoginUnm.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(unm).matches()){
            txtLoginUnm.setError("Enter valid Email.");
            txtLoginUnm.requestFocus();
            return;
        }

        if(upass.isEmpty()){
            txtLoginUpass.setError("Password is Required");
            txtLoginUpass.requestFocus();
            return;
        }

        Call<LoginResponse> call = RetrofitClient.getInstance().getApi().userLogin(unm,upass,IMEI);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse = response.body();

                if(!loginResponse.isError()){

                    SharedPrefManager.getInstance(LoginActivity.this).saveuser(loginResponse.getUser());

                    Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                }else
                {
                    Toast.makeText(LoginActivity.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getImei() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            IMEI = telephonyManager.getDeviceId().toString();
        }
        else{
            requestReadPhoneState();
        }
    }

    private void getImeiNumber() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            IMEI = telephonyManager.getDeviceId().toString();
            new AlertDialog.Builder(this)
                    .setTitle("IMEI NUMBER")
                    .setMessage(IMEI)
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create()
                    .show();
        }
        else{
            requestReadPhoneState();
        }
    }

    private void requestReadPhoneState() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_PHONE_STATE)) {

            new AlertDialog.Builder(this)
                    .setTitle("Permission Request")
                    .setMessage("This Permission is needed for read device IMEI.")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            ActivityCompat.requestPermissions(LoginActivity.this,
                                    new String[]{Manifest.permission.READ_PHONE_STATE},
                                    STORAGE_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create()
                    .show();
        }
        else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE},
                    STORAGE_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getImeiNumber();
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}