package com.innovvscript.ewallet.httpsRequest;


import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import com.innovvscript.ewallet.activities.LoggedInActivity;
import com.innovvscript.ewallet.activities.MainActivity;
import com.innovvscript.ewallet.model.Request;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

public class HttpsUtil {

    private Context context;
    private String action;

    public HttpsUtil(Context context,String action) {
        this.context = context;
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

            switch(action){
                case "login": ((MainActivity)context).next();
                    try {
                        Request.setToken(Request.getResponseJson().getString("token"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    return;
                case "balance": ((LoggedInActivity)context).displayBalance();  return;
                case "transactions": ((LoggedInActivity)context).next();  return;
                case "spend": return;
                default:   Log.w("onPostExecute()","default");
            }
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                Request.getHttpsURLConnection().connect();
                if(action.equalsIgnoreCase("transactions")) {
                    Log.w("action",action);
                    Request.setResponseString(getArray());
                }else {
                    Log.w("action",action);
                    Request.setResponseJson(getBody());
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
//        Log.w("error",Request.getHttpsURLConnection().getErrorStream().toString());
//        StringBuilder sb = new StringBuilder();
//        String line;
//        while ((line = br.readLine()) != null) {
//            sb.append(line+"\n");
//        }
//        br.close();
        return new JSONObject(json);
    }

    private String getArray() throws IOException, JSONException {
        Log.w("getArray()","called");
        BufferedReader br = new BufferedReader(new InputStreamReader(Request.getHttpsURLConnection().getInputStream()));
        return br.readLine();
    }
}
