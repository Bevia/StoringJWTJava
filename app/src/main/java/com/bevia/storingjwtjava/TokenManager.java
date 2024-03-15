package com.bevia.storingjwtjava;
import android.content.Context;
import android.content.SharedPreferences;

public class TokenManager {

    private static final String SHARED_PREF_NAME = "your_shared_pref_name";
    private static final String TOKEN_KEY = "jwt_token";

    public static void saveToken(Context context, String token) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(TOKEN_KEY, token);
            editor.apply();

    }

    public static String getToken(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(TOKEN_KEY, null);
    }
}
