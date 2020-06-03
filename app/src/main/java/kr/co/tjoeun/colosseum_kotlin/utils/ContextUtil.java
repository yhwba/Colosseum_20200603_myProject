package kr.co.tjoeun.colosseum_kotlin.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class ContextUtil {

    private static final String prefName = "APIPracticePref";

    private static final String LOGIN_USER_TOKEN = "LOGIN_USER_TOKEN";
    private static final String IS_AUTO_LOGIN = "IS_AUTO_LOGIN";

    public static void setLoginUserToken(Context context, String token) {
        SharedPreferences pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        pref.edit().putString(LOGIN_USER_TOKEN, token).apply();
    }

    public static String getLoginUserToken(Context context) {
        SharedPreferences pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        return pref.getString(LOGIN_USER_TOKEN, "");
    }

    public static void setAutoLogin(Context context, boolean autoLogin) {
        SharedPreferences pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        pref.edit().putBoolean(IS_AUTO_LOGIN, autoLogin).apply();
    }

    public static boolean isAutoLogin(Context context) {
        SharedPreferences pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        return pref.getBoolean(IS_AUTO_LOGIN, false);
    }

}
