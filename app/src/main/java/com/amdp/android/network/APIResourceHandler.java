/*******************************************************************************
 * Copyright (c) 2014. Zyght
 * All rights reserved. 
 *
 ******************************************************************************/
package com.amdp.android.network;

import android.content.Context;

import java.util.HashMap;


/**
 * @author Arley Mauricio Duarte Palomino
 */
public abstract class APIResourceHandler {

    private ResponseActionDelegate responseActionDelegate;
    private Context context;
    private boolean showWaitingDialog = true;

    protected Context getContext() {
        return context;
    }


    public void setRequestHandle(ResponseActionDelegate responseActionDelegate, Context context) {
        this.responseActionDelegate = responseActionDelegate;
        this.context = context;
        ServerCommunicationAsyncTask serverCommunicationAsyncTask = new ServerCommunicationAsyncTask(context, this);
        serverCommunicationAsyncTask.setShowWaitingDialog(isShowWaitingDialog());
        serverCommunicationAsyncTask.execute();
    }


    public HashMap<String, String> getValueParams() {
        return null;
    }

    protected ResponseActionDelegate getResponseActionDelegate() {
        return responseActionDelegate;
    }


    public boolean isNeedAuthToken() {
        return true;
    }

    public String getServiceURL() {
        return "";
    }

    public HttpMethod getHttpMethod() {
        return HttpMethod.POST;
    }

    public void setShowWaitingDialog(boolean showWaitingDialog) {
        this.showWaitingDialog = showWaitingDialog;
    }

    protected boolean isShowWaitingDialog() {
        return showWaitingDialog;
    }


    public abstract void handlerAPIResponse(APIResponse apiResponse);

}
