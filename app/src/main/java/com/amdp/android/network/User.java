package com.amdp.android.network;

import com.google.gson.Gson;

/**
 * Created by Arley Mauricio Duarte on 5/17/17.
 */

public abstract class User {

    private String userId = "";
    private String name = "";
    private String role = "";
    private String type = "";

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String json(){
        Gson gson = new Gson();
        return  gson.toJson(this);
    }


}
