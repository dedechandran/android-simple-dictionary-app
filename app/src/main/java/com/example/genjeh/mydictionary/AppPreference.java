package com.example.genjeh.mydictionary;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.genjeh.mydictionary.R;

public class AppPreference {
    private SharedPreferences sharedPreferences;
    private Context context;

    public AppPreference(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        this.context = context;
    }

    public void setFirstRun(boolean firstRun){
        sharedPreferences.edit().putBoolean(context.getResources().getString(R.string.first_run),firstRun).apply();
    }

    public boolean getFirstRun(){
        return sharedPreferences.getBoolean(context.getResources().getString(R.string.first_run),true);
    }
}
