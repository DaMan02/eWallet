package com.innovvscript.ewallet;

import android.os.AsyncTask;
import android.util.Log;

import com.innovvscript.ewallet.model.Request;
import com.innovvscript.ewallet.presenter.MyPresenter;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

public class HttpsUtil {

    private MyPresenter presenter;
    private String action;

    public HttpsUtil(MyPresenter presenter,String action) {
        this.presenter = presenter;
        this.action = action;
    }
    private static final String BASE_URL = "https://interviewer-api.herokuapp.com/";

    public HttpsURLConnection getHttpsConnection(String reqType,String endPoint) throws IOException {

            URL url = new URL(BASE_URL + endPoint);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod(reqType);
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoInput(true);
            connection.setDoOutput(false);
        return connection;
    }

    public void connectAsync(){
        new RequestAsyncTask().execute();
    }

    private class RequestAsyncTask extends AsyncTask<String,Void,String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected void onPostExecute(String s)  {
            super.onPostExecute(s);
            Log.w("onPostExecute()","response:" + Request.getResponseCode());
            Log.w("onPostExecute()","body:" + Request.getResponseJson());

            try {
                presenter.executionCompleted();
            } catch (JSONException | NullPointerException e) {
                e.printStackTrace();
            }

        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                Request.getHttpsURLConnection().connect();
                if(action.equalsIgnoreCase("transactions")) {
                    presenter.gotResponseString(getArray());
                }else {
                    presenter.gotResponseJson(getBody());
                }
                    Request.setResponseCode(Request.getHttpsURLConnection().getResponseCode());
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return "Executed";
        }

    }

    private JSONObject getBody() throws IOException, JSONException {

        BufferedReader br = new BufferedReader(new InputStreamReader(Request.getHttpsURLConnection().getInputStream()));
        String json = br.readLine();
        return new JSONObject(json);
    }

    private String getArray() throws IOException{
        Log.w("getArray()","called");
        BufferedReader br = new BufferedReader(new InputStreamReader(Request.getHttpsURLConnection().getInputStream()));
        return br.readLine();
    }
}
