package com.gymassistant;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by KamilH on 2016-06-29.
 */
public class Preferences {
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public Preferences(Context context){
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = preferences.edit();
    }

    public boolean isFirstTime(){
        return preferences.getBoolean("isFirstTime", false);
    }

    public void setFirstTime(boolean isFirstTime){
        editor.putBoolean("isFirstTime", isFirstTime).apply();
    }

    public String getLengthUnit(){
        return preferences.getString("lengthUnit", "cm");
    }

    public void setLengthUnit(String lengthUnit){
        editor.putString("lengthUnit", lengthUnit).apply();
    }

    public String getWeightUnit(){
        return preferences.getString("weightUnit", "kg");
    }

    public void setWeightUnit(String weightUnit){
        editor.putString("weightUnit", weightUnit).apply();
    }
}
