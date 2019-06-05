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

public class UpcomingFragment extends Fragment {

    View view;
    RecyclerView upComingRecyclerView;
    TripListAdapter tripListAdapter;
    List<TripList> mTrips;

    public UpcomingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view =  inflater.inflate(R.layout.fragment_upcoming, container, false);
        initView();
        return view;
    }

    private void initView(){

        upComingRecyclerView = view.findViewById(R.id.upcomingRecyclerView);
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
        mtTripList.add(new TripList("24 May'19","25 May'19","ABCD Travels","123456","MH 05 GJ 4563","A123","UpComing"));
        mtTripList.add(new TripList("26 May'19","27 May'19","XYZ Travels","123","MH 05 GJ 4445","A124","UpComing"));
        mtTripList.add(new TripList("26 May'19","26 May'19","ABC Travels","136","MH 05 GJ 5563","A125","UpComing"));
        mtTripList.add(new TripList("28 May'19","28 May'19","YOYO Travels","1256","MH 05 GJ 8563","A126","UpComing"));
        upComingRecyclerView.setAdapter(new TripListAdapter(getActivity().getApplicationContext(), mtTripList));
        upComingRecyclerView.smoothScrollToPosition(0);
    }
}