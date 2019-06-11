package com.deepak.kcl.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.deepak.kcl.Activities.LoginActivity;
import com.deepak.kcl.Activities.ProfileActivity;
import com.deepak.kcl.R;
import com.deepak.kcl.Storage.SharedPrefManager;
import com.deepak.kcl.models.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment {

    View view;
    Button btnSignOut;
    TextView txtAcUname,txtAcUemail,txtAcHome,txtAcEditprofile,txtAcTrips,txtAcReports;
    User user;
    public AccountFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_account, container, false);
        initView();
        return view;
    }

    private void initView() {
        txtAcUname = view.findViewById(R.id.txtAcUname);
        txtAcUemail = view.findViewById(R.id.txtAcUemail);
        txtAcHome = view.findViewById(R.id.txtAcHome);
        txtAcEditprofile = view.findViewById(R.id.txtAcEditProfile);
        txtAcTrips = view.findViewById(R.id.txtAcTrips);
        txtAcReports = view.findViewById(R.id.txtAcReports);

        btnSignOut = view.findViewById(R.id.btnSignOut);

        initializeView();
    }

    @Override
    public void onStart() {
        super.onStart();
        initializeView();
    }

    private void initializeView() {

        user = SharedPrefManager.getInstance(getActivity()).getUser();
        txtAcUname.setText(user.getUname());
        txtAcUemail.setText(user.getUemail());

        txtAcHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "You Clicked Home", Toast.LENGTH_SHORT).show();
            }
        });

        txtAcEditprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ProfileActivity.class));
                Toast.makeText(getActivity(), "You Clicked Edit Profile", Toast.LENGTH_SHORT).show();
            }
        });

        txtAcTrips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "You Clicked Trips", Toast.LENGTH_SHORT).show();
            }
        });

        txtAcReports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "You Clicked Reports", Toast.LENGTH_SHORT).show();
            }
        });

        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });
    }

    private void signOut() {
        SharedPrefManager.getInstance(getActivity()).clear();
        Intent intent = new Intent(getActivity(),LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

}
