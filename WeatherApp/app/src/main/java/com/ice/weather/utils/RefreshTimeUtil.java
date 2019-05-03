package com.ice.weather.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 刷新时间工具类
 */
public class RefreshTimeUtil {

    private static final String Name = "config";

    public static void putString(Context mContext,String key,String value){
        SharedPreferences sp = mContext.getSharedPreferences(Name,Context.MODE_PRIVATE);
        sp.edit().putString(key, value).apply();
    }

    public static String getString(Context mContext,String key,String value){
        SharedPreferences sp = mContext.getSharedPreferences(Name,Context.MODE_PRIVATE);
        return sp.getString(key,value);
    }
}
