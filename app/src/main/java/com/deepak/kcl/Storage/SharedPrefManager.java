package com.deepak.kcl.Storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.deepak.kcl.models.BranchTrips;
import com.deepak.kcl.models.User;

public class SharedPrefManager {

    private static final String SHARED_PREF_NAME = "my_shared_account";
    private static final String SHARED_PREF_TRIP = "my_shared_trip";
    private static SharedPrefManager mInstance;
    private Context mContext;

    private SharedPrefManager(Context mContext) {
        this.mContext = mContext;
    }

    public static synchronized SharedPrefManager getInstance(Context mContext){
        if(mInstance == null)
        {
            mInstance = new SharedPrefManager(mContext);
        }
        return mInstance;
    }

    public void saveuser(User user){

        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("id", user.getUser_id());
        editor.putString("name",user.getFull_name());
        editor.putString("email",user.getEmail());
        editor.putString("mobile",user.getMobile());
        editor.putInt("branch_id",user.getBranch_id());
        editor.putString("branch_name",user.getBranch_name());
        editor.putString("IMEI1",user.getIMEI1());
        editor.putString("IMEI2",user.getIMEI2());
        editor.putString("profileImg",user.getU_img());

        editor.apply();
    }


    public boolean isLoggedIn(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getInt("id", -1) != -1;
    }

    public User getUser(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getInt("id",-1),
                sharedPreferences.getString("name",null),
                sharedPreferences.getString("email",null),
                sharedPreferences.getString("mobile",null),
                sharedPreferences.getInt("branch_id",-1),
                sharedPreferences.getString("branch_name",null),
                sharedPreferences.getString("IMEI1",null),
                sharedPreferences.getString("IMEI2",null),
                sharedPreferences.getString("profileImg",null)
        );
    }

    public void saveTrip(BranchTrips branchTrips){

        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_TRIP,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("trip_assign_id", branchTrips.getTrip_assign_id());
        editor.putInt("trip_id", branchTrips.getTrip_id());
        editor.putString("LR", branchTrips.getLR());
        editor.putString("vehicle_no", branchTrips.getVehicle_no());
        editor.putString("routeName", branchTrips.getRouteName());
        editor.putString("client_name", branchTrips.getClient_name());
        editor.putString("s_time", branchTrips.getS_time());
        editor.putString("e_time", branchTrips.getE_time());
        editor.putString("s_date", branchTrips.getS_date());
        editor.putString("e_date", branchTrips.getE_date());
        editor.putString("budget", branchTrips.getBudget());
        editor.putString("quantity", branchTrips.getQuantity());
        editor.putString("KM", branchTrips.getKM());

        editor.apply();
    }

    public BranchTrips getBranchTrips(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_TRIP,Context.MODE_PRIVATE);
        return new BranchTrips(
                sharedPreferences.getInt("trip_assign_id",-1),
                sharedPreferences.getInt("trip_id",-1),
                sharedPreferences.getString("LR",null),
                sharedPreferences.getString("vehicle_no",null),
                sharedPreferences.getString("routeName",null),
                sharedPreferences.getString("client_name",null),
                sharedPreferences.getString("s_time",null),
                sharedPreferences.getString("e_time",null),
                sharedPreferences.getString("s_date",null),
                sharedPreferences.getString("e_date",null),
                sharedPreferences.getString("budget",null),
                sharedPreferences.getString("quantity",null),
                sharedPreferences.getString("KM",null)
        );
    }

    public void clearTrip(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_TRIP,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.clear();
        editor.apply();
    }

    public void clear(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.clear();
        editor.apply();
    }
}
