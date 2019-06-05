package com.deepak.kcl.TabFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.deepak.kcl.Adapter.TripListAdapter;
import com.deepak.kcl.R;
import com.deepak.kcl.models.TripList;

import java.util.ArrayList;
import java.util.List;


public class CompletedFragment extends Fragment {

    View view;
    RecyclerView upComingRecyclerView;
    TripListAdapter tripListAdapter;
    List<TripList> mTrips;

    public CompletedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_completed, container, false);
        initView();
        return view;
    }

    private void initView(){

        upComingRecyclerView = view.findViewById(R.id.completedRecyclerView);
        //mTrips = new ArrayList<>();
        //tripListAdapter = new TripListAdapter(getActivity(),mTrips);

        implementView();
    }

    private void implementView() {

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        upComingRecyclerView.setLayoutManager(mLayoutManager);;
        //upComingRecyclerView.setAdapter(tripListAdapter);

        loadJSON();
    }

    private void loadJSON() {
        List<TripList> mtTripList = new ArrayList<>();
        mtTripList.add(new TripList("18 May'19","19 May'19","ABCD Travels","123456","MH 05 GJ 4563","A123","Completed"));
        mtTripList.add(new TripList("19 May'19","19 May'19","XYZ Travels","123","MH 05 GJ 4445","A124","Completed"));
        mtTripList.add(new TripList("20 May'19","20 May'19","ABC Travels","136","MH 05 GJ 5563","A125","Completed"));
        mtTripList.add(new TripList("20 May'19","21 May'19","YOYO Travels","1256","MH 05 GJ 8563","A126","Completed"));
        upComingRecyclerView.setAdapter(new TripListAdapter(getActivity().getApplicationContext(), mtTripList));
        upComingRecyclerView.smoothScrollToPosition(0);
    }

}
