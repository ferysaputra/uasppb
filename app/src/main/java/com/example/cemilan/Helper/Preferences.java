package com.example.cemilan.Helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Preferences {

    static final String KEY_ID_SEDANG_LOGIN = "Id_logged_in";
    static final String KEY_ROLE_SEDANG_LOGIN = "Role_logged_in";

    private static SharedPreferences getSharedPreference(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void setLoginUser(Context context, Integer id,String role){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putInt(KEY_ID_SEDANG_LOGIN, id);
        editor.putString(KEY_ROLE_SEDANG_LOGIN, role);
        editor.apply();
    }
    public static String getRole(Context context){
        return getSharedPreference(context).getString(KEY_ROLE_SEDANG_LOGIN,"");
    }
    public static Integer getId(Context context){
        return getSharedPreference(context).getInt(KEY_ID_SEDANG_LOGIN,0);
    }

    public static void clearLoggedInUser (Context context){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.remove(KEY_ROLE_SEDANG_LOGIN);
        editor.remove(KEY_ID_SEDANG_LOGIN);
        editor.apply();
    }
}