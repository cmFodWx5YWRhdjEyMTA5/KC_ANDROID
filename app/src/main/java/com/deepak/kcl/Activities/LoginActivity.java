package com.deepak.kcl.Activities;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.deepak.kcl.R;

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

    private void initView() {
        btnLogin = findViewById(R.id.btnlogin);
        txtImeinum = findViewById(R.id.txtImeiNum);
        txtLoginUnm = findViewById(R.id.txtloginUname);
        txtLoginUpass = findViewById(R.id.txtLoginUpass);
        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        initialize();
    }

    private void initialize() {

        txtImeinum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getImeiNumber();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unm = txtLoginUnm.getText().toString();
                upass = txtLoginUpass.getText().toString();

                startActivity(new Intent(LoginActivity.this,HomeActivity.class));
            }
        });
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
