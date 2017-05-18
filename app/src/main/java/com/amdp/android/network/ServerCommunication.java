/*******************************************************************************
 * Copyright (c) 2014. Zyght
 * All rights reserved.
 ******************************************************************************/
package com.amdp.android.network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import javax.net.ssl.HttpsURLConnection;

/**
 * @author Arley Mauricio Duarte Palomino
 */
class ServerCommunication {

    private String serviceUrl;
    private boolean isNeedSession = false;

    public ServerCommunication(String url, boolean isNeedSession) {
        this.isNeedSession = isNeedSession;
        this.serviceUrl = url;
    }


    public APIResponse getAPIResponse(HashMap<String, String> postDataParams, HttpMethod method) {
        APIResponse apiResponse = new APIResponse();
        URL url;
        String response = "";
        int responseCode = 0;
        try {
            url = new URL(serviceUrl);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod(method.toString());
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestProperty("Accept-Encoding", "gzip");


            if (isNeedSession) {
                conn.setRequestProperty("Authorization", "Bearer " + Session.getInstance().getAccessToken());
            }

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(postDataParams));

            writer.flush();
            writer.close();
            os.close();
            responseCode = conn.getResponseCode();


            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                InputStream inStream = new GZIPInputStream(conn.getInputStream());
                BufferedReader br = new BufferedReader(new InputStreamReader(inStream));
                while ((line = br.readLine()) != null) {
                    response += line;
                }

                apiResponse.getStatus().setStatusCode(responseCode);
            } else {
                response = "";

            }
        } catch (Exception e) {
            apiResponse.getStatus().setErrorCode(responseCode);
            e.printStackTrace();
        }

        apiResponse.setRawResponse(response);
        return apiResponse;
    }


    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }


}