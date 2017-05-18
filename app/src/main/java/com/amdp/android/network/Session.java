package com.amdp.android.network;


import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;

public class Session {

    private static final String PREFS_NAME = "Session";
    private static final String TOKEN_NAME = "token";
    private static final String USER_JSON = "user_json";
    private static Session ourInstance = new Session();
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static Session getInstance() {
        return ourInstance;
    }

    private Session() {
    }

    public String accessToken = "";

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken, Context context) {
        this.accessToken = accessToken;
        if(context != null)
            save(context);
    }

    public void restore(Context context){
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        accessToken  = settings.getString(TOKEN_NAME, "");

        String jsonUser = settings.getString(USER_JSON, "");

        if(!TextUtils.isEmpty(jsonUser)){

            Gson gson = new Gson();
            user = gson.fromJson(jsonUser, User.class);
        }

    }

    private void save(Context context){
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(TOKEN_NAME, accessToken);
        if(user != null){
            editor.putString(USER_JSON, user.json());
        }


        editor.commit();
    }
}
