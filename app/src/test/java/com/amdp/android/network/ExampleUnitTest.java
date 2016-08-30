package com.amdp.android.network;

import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        ServerCommunication sc = new ServerCommunication("https://demo-zyght-api.azurewebsites.net/api/token",  false);
        HashMap<String, String> params = new HashMap<String, String>();

        params.put("grant_type",  "password");
        params.put("username",  "admin.faena@zyght.com");
        params.put("password",  "demo1234");

        APIResponse apiResponse = sc.getAPIResponse(params, HttpMethod.POST);

        assertTrue(apiResponse.getRawResponse().contains("access_token"));

    }


    @Test
    public void token_isCorrect() throws Exception {
        String token = "SCg6WZu0nrymVjqOLGnRU3T0yzvn80p1XJfa6FiF3oIjgRnh3fu8bIWdGMiIteQ3HES9z7yR-PeD09cx5wM8bD9kNYFy08igCS-0-x9NNVm_n57INFlODnxeYJ590squUXh9VIU8q2rcm5VyroyfFzZxcTc7AJ2SEqwM5gvzqWQccFgHxy5be0X8xrlfvVuu-39f1PZZGD-7yNLdcZqC3u6PNmsM98e_66V_-a-tlkDJ8u59NSORb-ybiyzwW0vI6e4sfBYcIkfB_65fbKbwYELzNIzqeE0fej2ofhLeAYCbZ_mgEqc8Rdk1V47R72e9";

        Session.getInstance().setAccessToken(token, null);

        ServerCommunication sc = new ServerCommunication("https://demo-zyght-api.azurewebsites.net/api/InitialData",  true);
        HashMap<String, String> params = new HashMap<String, String>();

        params.put("Platform",  "APIAndroid");
        APIResponse apiResponse = sc.getAPIResponse(params, HttpMethod.POST);

        assertTrue(apiResponse.getRawResponse().contains("setting"));

    }


    @Test
    public void notValidUser() throws Exception {
        ServerCommunication sc = new ServerCommunication("https://demo-zyght-api.azurewebsites.net/api/token",  false);
        HashMap<String, String> params = new HashMap<String, String>();

        params.put("grant_type",  "password");
        params.put("username",  "admin.faena@zyght.comX");
        params.put("password",  "demo1234");

        APIResponse apiResponse = sc.getAPIResponse(params, HttpMethod.POST);

        assertTrue(apiResponse.getStatus().isSuccess() == false);

    }
}