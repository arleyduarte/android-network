package com.amdp.android.apihandler;

import com.amdp.android.network.APIResourceHandler;
import com.amdp.android.network.APIResponse;

import java.util.HashMap;

/**
 * Created by arley on 9/23/16.
 */
public class LoginAPIHandler extends APIResourceHandler {

    protected String username;
    protected String password;

    public LoginAPIHandler(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public void handlerAPIResponse(APIResponse apiResponse) {

    }

    @Override
    public HashMap<String, String> getBodyParams() {

        HashMap<String, String>  params = new HashMap<>();

        params.put("grant_type",  "password");
        params.put("username",  username);
        params.put("password",  password);

        return  params;
    }

    public boolean isNeedAuthToken() {
        return false;
    }


}
