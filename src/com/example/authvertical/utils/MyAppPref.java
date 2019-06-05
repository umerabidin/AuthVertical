package com.example.authvertical.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class MyAppPref {
    public static MyAppPref instance = null;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public MyAppPref(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences ( context );
        editor = sharedPreferences.edit ();
    }

    public synchronized static MyAppPref getInstance(Context context) {
        if (instance == null)
            instance = new MyAppPref( context );
        return instance;
    }

    public void saveInt(String key , int value) {
        editor.putInt ( key , value );
        editor.commit ();
    }

    public void saveBoolean(String key , Boolean value) {
        editor.putBoolean ( key , value );
        editor.commit ();
    }

    public void saveString(String key , String value) {
        editor.putString ( key , value );
        editor.commit ();
    }

    public String getPref(String key , String value) {
        return sharedPreferences.getString ( key , value );
    }

    public Boolean getPref(String key , boolean value) {
        return sharedPreferences.getBoolean ( key , value );
    }

    public int getPref(String key , int value) {
        return sharedPreferences.getInt ( key , value );
    }

    public void removePref(String is_login) {
        editor.remove ( is_login );
        editor.commit ();
    }



}
