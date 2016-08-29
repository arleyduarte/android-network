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
}