package com.innovvscript.ewallet.model;

import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;

public class Request {


    private static String this_token;
    private static int this_responseCode;
    private static HttpsURLConnection httpsURLConnection;
    private static JSONObject responseJson;
    private static String responseString;

    public static String getResponseString() {
        return responseString;
    }

    public static void setResponseString(String responseString) {
        Request.responseString = responseString;
    }

    public static JSONObject getResponseJson() {
        return responseJson;
    }

    public static void setResponseJson(JSONObject responseJson) {
        Request.responseJson = responseJson;
    }

    public static HttpsURLConnection getHttpsURLConnection() {
        return httpsURLConnection;
    }

    public static void setHttpsURLConnection(HttpsURLConnection httpsURLConnection) {
        Request.httpsURLConnection = httpsURLConnection;
    }

    public static int getResponseCode() {
        return this_responseCode;
    }

    public static void setResponseCode(int responseCode) {
        this_responseCode = responseCode ;
    }

    public static String getToken() {
        return this_token;
    }

    public static void setToken(String token) {
        this_token = token;
    }

}
