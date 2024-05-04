package com.example.pizzadeliveryapp.models;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class preferences {
    private static final String DATA_LOGIN = "status_login";
    private static final String DATA_AS = "as";
    private static final String FULL_NAME = "username";
    private static final String EMAIL = "email";
    private static final String ADDRESS = "address";
    private static final String PHONE = "phone";


    private static SharedPreferences getSharedReferences(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void setDataAs(Context context, String data){
        SharedPreferences.Editor editor = getSharedReferences(context).edit();
        editor.putString(DATA_AS, data);
        editor.apply();
    }

    public static String getDataAs(Context context){
        return getSharedReferences(context).getString(DATA_AS, "");
    }

    public static void setDataLogin(Context context, boolean status){
        SharedPreferences.Editor editor = getSharedReferences(context).edit();
        editor.putBoolean(DATA_LOGIN, status);
        editor.apply();
    }

    public static boolean getDataLogin(Context context){
        return getSharedReferences(context).getBoolean(DATA_LOGIN, false);
    }

    public static void setFullName(Context context, String name){
        SharedPreferences.Editor editor = getSharedReferences(context).edit();
        editor.putString(FULL_NAME, name);
        editor.apply();
    }

    public static String getFullName(Context context){
        return getSharedReferences(context).getString(FULL_NAME, "");
    }

    public static void setEmail(Context context, String email){
        SharedPreferences.Editor editor = getSharedReferences(context).edit();
        editor.putString(EMAIL, email);
        editor.apply();
    }

    public static String getEmail(Context context){
        return getSharedReferences(context).getString(EMAIL, "");
    }

    public static void setPhone(Context context, String phone){
        SharedPreferences.Editor editor = getSharedReferences(context).edit();
        editor.putString(PHONE, phone);
        editor.apply();
    }

    public static String getPhone(Context context){
        return getSharedReferences(context).getString(PHONE, "");
    }

    public static void clearData(Context context){
        SharedPreferences.Editor editor = getSharedReferences(context).edit();
        editor.remove(DATA_AS);
        editor.remove(DATA_LOGIN);
        editor.remove(FULL_NAME);
        editor.remove(EMAIL);
        editor.remove(PHONE);
        editor.apply();
    }


}
