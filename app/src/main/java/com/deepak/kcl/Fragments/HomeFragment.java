package com.deepak.kcl.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.deepak.kcl.R;
import com.deepak.kcl.TabFragments.CompletedFragment;
import com.deepak.kcl.TabFragments.OngoingFragment;
import com.deepak.kcl.TabFragments.UpcomingFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    View view;
    ViewPager viewPager;
    TabLayout tabLayout;
   Toolbar toolbar;

    OngoingFragment ongoingFragment;
    UpcomingFragment upcomingFragment;
    CompletedFragment completedFragment;
    MyPagerAdapter myPagerAdapter;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        initView();
        return view;

    }

    private void initView() {
        toolbar = view.findViewById(R.id.homeToolbar);
        viewPager = view.findViewById(R.id.viewPager);
        tabLayout = view.findViewById(R.id.tabLayout);

        initializeView();
    }

    private void initializeView() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ongoingFragment = new OngoingFragment();
        upcomingFragment = new UpcomingFragment();
        completedFragment = new CompletedFragment();

        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(new MyPagerAdapter(getChildFragmentManager()));
        viewPager.setCurrentItem(1);
    }

    public class MyPagerAdapter extends FragmentPagerAdapter {

        String[] fragmentNames = {"Upcoming","Ongoing","Completed"};

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return upcomingFragment;
                case 1:
                    return ongoingFragment;
                case 2:
                    return completedFragment;
            }
            return null;
        }

        @Override
        public int getCount() {
            return fragmentNames.length;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentNames[position];
        }
    }

}
