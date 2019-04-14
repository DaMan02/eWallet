package com.innovvscript.ewallet.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.common.util.UriUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import com.innovvscript.ewallet.R;
import com.innovvscript.ewallet.httpsRequest.HttpsUtil;
import com.innovvscript.ewallet.model.Request;
import org.json.JSONException;
import java.io.IOException;
import javax.net.ssl.HttpsURLConnection;

public class LoggedInActivity extends AppCompatActivity {

    private TextView balanceTV;
    private static final String TAG = "LoggedInAct.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);

        balanceTV = findViewById(R.id.balance_id);

        SimpleDraweeView simpleDraweeView = findViewById(R.id.my_bg);
        Uri uri = new Uri.Builder()
                .scheme(UriUtil.LOCAL_RESOURCE_SCHEME)
                .path(String.valueOf(R.drawable.fogblur))
                .build();
        simpleDraweeView.setImageURI(uri);


    }

    @Override
    protected void onStart() {
        super.onStart();
        setCurrentBalance();
    }

    private void setCurrentBalance() {

        HttpsUtil httpsUtil = new HttpsUtil(this,"balance");
        try {
            HttpsURLConnection connection = httpsUtil.getHttpsConnection("GET","balance");
            connection.setRequestProperty("Authorization", "Bearer " + Request.getToken());
            Log.w(TAG,"token:"+ Request.getToken());
            Request.setHttpsURLConnection(connection);
            httpsUtil.connectAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void displayBalance(){
        try {
            Log.w("displayBalance()", Request.getResponseJson().getString("balance"));
            String text = Request.getResponseJson().getString("balance") + " " + Request.getResponseJson().getString("currency");
            balanceTV.setText(text);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.w(TAG,e.getMessage());
        }
    }

    public void showTransactions(View v){

        Log.w(TAG,"showTransactions()");

        HttpsUtil httpsUtil = new HttpsUtil(this,"transactions");
        try {
            HttpsURLConnection connection = httpsUtil.getHttpsConnection("GET","transactions");
            connection.setRequestProperty("Authorization", "Bearer " + Request.getToken());
//            Log.w(TAG,"token:"+ Request.getToken());
            Request.setHttpsURLConnection(connection);
            httpsUtil.connectAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void next(){
        startActivity(new Intent(this,MyTransactionsActivity.class));
    }

    public void spend(View v){
        Toast.makeText(this, "Service Unavailable", Toast.LENGTH_SHORT).show();
    }
}
