package com.innovvscript.ewallet.presenter;

import android.util.Log;
import com.innovvscript.ewallet.HttpsUtil;
import com.innovvscript.ewallet.model.Request;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;


import javax.net.ssl.HttpsURLConnection;

public class MyPresenter {

    private Request request;
    private MyView view;
    private String requestType;
    private HttpsUtil httpsUtil;

    public MyPresenter(MyView view) {
        this.request = new Request();
        this.view = view;
        this.httpsUtil = new HttpsUtil(this,"noTransaction");
    }

    public void tryLogin(){
        try {
            this.requestType = "login";
            HttpsURLConnection connection = httpsUtil.getHttpsConnection("POST","login");
            httpsUtil.connectAsync();
            request.setHttpsURLConnection(connection);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("trying login",e.getMessage());
        }
    }

    public void executionCompleted() throws JSONException,NullPointerException {
        switch (requestType){
            case "login": Request.setToken(request.getResponseJson().getString("token"));
              view.onGetToken();
              break;
            case "balance": view.onGetBalance();  break;
            case "transactions": view.onGetTransactions();  break;
        }

    }

    public void gotResponseString(String array){
       Request.setResponseString(array);
    }

    public void gotResponseJson(JSONObject json){
        Request.setResponseJson(json);
    }

    public void setBalance() {
        this.requestType = "balance";
        try {
            HttpsURLConnection connection = httpsUtil.getHttpsConnection("GET","balance");
            connection.setRequestProperty("Authorization", "Bearer " + Request.getToken());
            Request.setHttpsURLConnection(connection);
            httpsUtil.connectAsync();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("setBalance()",e.getMessage());
        }

    }

    public void showTransactions() {
        this.requestType = "transactions";
        HttpsUtil httpsUtil = new HttpsUtil(this,"transactions");
        try {
            HttpsURLConnection connection = httpsUtil.getHttpsConnection("GET","transactions");
            connection.setRequestProperty("Authorization", "Bearer " + Request.getToken());
            Request.setHttpsURLConnection(connection);
            httpsUtil.connectAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public interface MyView {
        void onGetToken();
        void onGetBalance();
        void onGetTransactions();
    }
}
