/*******************************************************************************
 * Copyright (c) 2014. Zyght
 * All rights reserved. 
 *
 ******************************************************************************/

package com.amdp.android.network;


/**
 * Created by Arley Mauricio Duarte
 */
public class Status {

    private int statusCode;
    private String error = "";
    private int errorCode;

    public boolean isSuccess() {
        boolean success = false;
        //Success HTTP Codes
        if (statusCode >= 200 && statusCode < 300) {
            success = true;
        }

        return success;
    }



    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
