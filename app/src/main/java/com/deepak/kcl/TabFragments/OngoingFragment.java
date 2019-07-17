package com.deepak.kcl.TabFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.deepak.kcl.Adapter.BranchExpenseAdapter;
import com.deepak.kcl.Adapter.TripListAdapter;
import com.deepak.kcl.Networking.RetrofitClient;
import com.deepak.kcl.R;
import com.deepak.kcl.Storage.SharedPrefManager;
import com.deepak.kcl.models.BranchExpense;
import com.deepak.kcl.models.BranchTrips;
import com.deepak.kcl.models.BranchTripsResponse;
import com.deepak.kcl.models.TripList;
import com.deepak.kcl.models.User;
import com.github.ybq.android.spinkit.SpinKitView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OngoingFragment extends Fragment {

    View view;
    RecyclerView onGoingRecyclerView;
    TripListAdapter tripListAdapter;
    List<TripList> mTrips;
    SpinKitView progrssBar;
    TextView txtEmptyView;
    User user;
    private List<BranchTrips> branchTrips;

    public OngoingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragm
        view =  inflater.inflate(R.layout.fragment_ongoing, container, false);
        initView();
        return view;
    }

    private void initView(){

        onGoingRecyclerView = view.findViewById(R.id.onGoingRecyclerView);
        //mTrips = new ArrayList<>();
        //tripListAdapter = new TripListAdapter(getActivity(),mTrips);

        implementView();
    }

    private void implementView() {

        user = SharedPrefManager.getInstance(getActivity()).getUser();
        progrssBar = view.findViewById(R.id.spin_kit_ongoing);
        txtEmptyView = view.findViewById(R.id.txt_ongoing_emptyView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        onGoingRecyclerView.setLayoutManager(mLayoutManager);;
        //onGoingRecyclerView.setAdapter(tripListAdapter);

        loadJSON();
    }

    private void loadJSON() {

        progrssBar.setVisibility(View.VISIBLE);
        Call<BranchTripsResponse> call = RetrofitClient.getInstance().getApi().getTripByBranch(user.getBranch_id());
        call.enqueue(new Callback<BranchTripsResponse>() {
            @Override
            public void onResponse(Call<BranchTripsResponse> call, Response<BranchTripsResponse> response) {
                branchTrips = response.body().getBranchTrips();
                if(branchTrips.size() == 0){
                    onGoingRecyclerView.setVisibility(View.GONE);
                    txtEmptyView.setVisibility(View.VISIBLE);
                }else{
                    onGoingRecyclerView.setVisibility(View.VISIBLE);
                    txtEmptyView.setVisibility(View.GONE);
                    tripListAdapter = new TripListAdapter(getActivity(),branchTrips);
                    onGoingRecyclerView.setAdapter(tripListAdapter);
                    tripListAdapter.notifyDataSetChanged();
                }
                progrssBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<BranchTripsResponse> call, Throwable t) {

            }
        });
        //List<TripList> mtTripList = new ArrayList<>();
        //mtTripList.add(new TripList("22 May'19","23 May'19","ABCD Travels","123456","MH 05 GJ 4563","A123","OnGoing"));
       // mtTripList.add(new TripList("22 May'19","23 May'19","XYZ Travels","123","MH 05 GJ 4445","A124","OnGoing"));
       // mtTripList.add(new TripList("22 May'19","23 May'19","ABC Travels","136","MH 05 GJ 5563","A125","OnGoing"));
        //mtTripList.add(new TripList("22 May'19","23 May'19","YOYO Travels","1256","MH 05 GJ 8563","A126","OnGoing"));
        //onGoingRecyclerView.setAdapter(new TripListAdapter(getActivity().getApplicationContext(), mtTripList));
        //onGoingRecyclerView.smoothScrollToPosition(0);
    }
}
