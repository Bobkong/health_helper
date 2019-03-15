package com.example.bob.health_helper.Util;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.bob.health_helper.Data.Bean.User;
import com.example.bob.health_helper.MyApplication;
import com.google.gson.Gson;

public class SharedPreferenceUtil {
    public static final String KEY_USER="user";

    private static SharedPreferences getPreferences(){
        return PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext());
    }

    public static void saveUser(User user){
        getPreferences().edit().putString(KEY_USER,new Gson().toJson(user)).apply();
    }

    public static void clearData(){
        getPreferences().edit().clear().commit();
    }

    public static User getUser(){
        return new Gson().fromJson(getPreferences().getString(KEY_USER,null),User.class);
    }

    public static void saveBooleanKeyValue(String key, Boolean value) {
        getPreferences().edit().putBoolean(key, value).apply();
    }

    public static boolean getBooleanKeyValue(String key) {
        return getPreferences().getBoolean(key, false);
    }

    public static void saveKeyValue(String key, String value) {
        getPreferences().edit().putString(key, value).apply();
    }

    public static String getKeyValue(String key) {
        return getPreferences().getString(key, "");
    }

    public static void saveIntKeyValue(String key, int value) {
        getPreferences().edit().putInt(key, value).apply();
    }

    public static int getIntKeyValue(String key, int defVal) {
        return getPreferences().getInt(key, defVal);
    }

    public static void saveFloatKeyValue(String key, float value) {
        getPreferences().edit().putFloat(key, value).apply();
    }

    public static float getFloatKeyValue(String key, float defVal) {
        return getPreferences().getFloat(key,defVal);
    }
}
