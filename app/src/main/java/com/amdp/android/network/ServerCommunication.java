/*******************************************************************************
 * Copyright (c) 2014. Zyght
 * All rights reserved.
 ******************************************************************************/
package com.amdp.android.network;

import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
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

    private static final String TAG = ServerCommunication.class.getSimpleName();
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
        try {
            url = new URL(serviceUrl);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod(method.toString());
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestProperty("Accept-Encoding", "gzip");

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(postDataParams));

            writer.flush();
            writer.close();
            os.close();
            int responseCode = conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }

                apiResponse.getStatus().setStatusCode(responseCode);
            } else {
                response = "";

            }
        } catch (Exception e) {
            e.printStackTrace();
        }




        Log.d(TAG, "Raw RESPONSE : " + response);
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

    /*
    protected APIResponse getAPIResponse(List<NameValuePair> nameValuePairs, HttpMethod method) {
        APIResponse apiResponse = new APIResponse();
        String rawResponse = "";
        try {

            Log.d(TAG, "Request: " + ServerURL);


            URL url = new URL(ServerURL);
            HttpURLConnection client = (HttpURLConnection) url.openConnection();


            client.setRequestProperty("Key", "Value");
            client.setDoOutput(true);


            //New
            client.setRequestProperty("Accept-Encoding", "gzip");

            if (isNeedSession) {
                Session session = Session.getInstance();
                client.setRequestProperty("Authorization", "Bearer " + session.getAccessToken());
            }


            OutputStream outputPost = new BufferedOutputStream(client.getOutputStream());

            outputPost.flush();
            outputPost.close();


            client.setFixedLengthStreamingMode(outputPost.getBytes().length);
            client.setChunkedStreamingMode(0);


            HttpResponse response = client.execute(httpClient);

            InputStream inputStream = response.getEntity().getContent();
            Header contentEncoding = response.getFirstHeader("Content-Encoding");
            if (contentEncoding != null && contentEncoding.getValue().equalsIgnoreCase("gzip")) {
                inputStream = new GZIPInputStream(inputStream);
            }


            rawResponse = convertStreamToString(inputStream);

            Log.d(TAG, "Raw RESPONSE : " + rawResponse);
            apiResponse.getStatus().setStatusCode(response.getStatusLine().getStatusCode());
            apiResponse.setRawResponse(rawResponse);

        } catch (Exception e) {
            Log.d(TAG, "Error IO Exception");

            apiResponse.getStatus().setErrorCode(APIErrors.CONNECTION_ERROR);
        }

        return apiResponse;
    }
*/

    //-----------------------------------------------------------

    private static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = "";
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }


}