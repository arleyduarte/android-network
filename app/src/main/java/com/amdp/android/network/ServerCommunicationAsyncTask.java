/*******************************************************************************
 * Copyright (c) 2014. Zyght
 * All rights reserved. 
 *
 ******************************************************************************/
package com.amdp.android.network;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

/**
 * @author Arley Mauricio Duarte Palomino
 */
class ServerCommunicationAsyncTask extends AsyncTask<Void, Void, APIResponse> {
    private APIResourceHandler resourceHandler;
    private ProgressDialog progressDialog = null;
    private APIResponse jsonResponse;
    private Context context = null;
    private ServerCommunication serverCommunication;
    private static final String TAG = ServerCommunicationAsyncTask.class.getSimpleName();
    private boolean showWaitingDialog = true;

    public ServerCommunicationAsyncTask(Context context, APIResourceHandler resourceHandler) {
        this.context = context;
        this.resourceHandler = resourceHandler;
        serverCommunication = new ServerCommunication(resourceHandler.getServiceURL(), resourceHandler.isNeedAuthToken());
    }

    @Override
    protected void onPreExecute() {
        if (isShowWaitingDialog() && context != null) {
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage(context.getString(R.string.please_wait));
            progressDialog.show();
        }
    }

    @Override
    protected APIResponse doInBackground(Void... params) {

        APIResponse apiResponse = null;
        try{
            apiResponse = serverCommunication.getAPIResponse(resourceHandler.getBodyParams(), resourceHandler.getHttpMethod());

        }catch (Exception e){
            Log.e(TAG, e.getMessage()) ;
            //APIResponse r = new APIResponse();
            //r.setRawResponse("");

           // return r;
        }finally {
            return apiResponse;
        }

       }

    @Override
    protected void onPostExecute(APIResponse jsonResponse) {

        try{
            if (progressDialog != null) {
                progressDialog.dismiss();
                progressDialog = null;
            }
        }catch (Exception e){
        }

        resourceHandler.handlerAPIResponse(jsonResponse);


    }

    public boolean isShowWaitingDialog() {
        return showWaitingDialog;
    }

    public void setShowWaitingDialog(boolean showWaitingDialog) {
        this.showWaitingDialog = showWaitingDialog;
    }

}