package com.deepak.kcl.Storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.deepak.kcl.models.User;

public class SharedPrefManager {

    private static final String SHARED_PREF_NAME = "my_shared_account";
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

        editor.putInt("id", user.getUid());
        editor.putString("name",user.getUname());
        editor.putString("email",user.getUemail());
        editor.putString("mobile",user.getUmobile());
        editor.putString("IMEI1",user.getUimei_no1());
        editor.putString("IMEI2",user.getUimei_no2());

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
                sharedPreferences.getString("IMEI1",null),
                sharedPreferences.getString("IMEI2",null)
        );
    }

    public void clear(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.clear();
        editor.apply();
    }
}