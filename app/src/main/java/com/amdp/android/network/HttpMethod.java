/*******************************************************************************
 * Copyright (c) 2014. Zyght
 * All rights reserved. 
 * 
 ******************************************************************************/
package com.amdp.android.network;

/**
 * Enumeration of HTTP methods supported by Request
 */
public enum HttpMethod {
    /**
     * Use HTTP method "GET" for the request
     */
    GET{
        @Override
        public String toString() {
            return "GET";
        }
    },

    /**
     * Use HTTP method "POST" for the request
     */
    POST{
        @Override
        public String toString() {
            return "POST";
        }
    },

    /**
     * Use HTTP method "DELETE" for the request
     */
    DELETE{
        @Override
        public String toString() {
            return "DELETE";
        }
    },

    PUT{
        @Override
        public String toString() {
            return "PUT";
        }
    },
}
