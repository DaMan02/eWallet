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
import com.innovvscript.ewallet.model.Request;
import com.innovvscript.ewallet.presenter.MyPresenter;
import org.json.JSONException;

public class LoggedInActivity extends AppCompatActivity implements MyPresenter.MyView{

    private TextView balanceTV;
    private static final String TAG = "LoggedInAct.";
    private MyPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);

        balanceTV = findViewById(R.id.balance_id);
        presenter = new MyPresenter(this);

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
        presenter.setBalance();
    }

    public void showTransactions(View v){

        Log.w(TAG,"showTransactions()");
        presenter.showTransactions();
    }


    public void spend(View v){
        Toast.makeText(this, "Service Unavailable", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetToken() {

    }

    @Override
    public void onGetBalance() {
        try {
            Log.w("displayBalance()", Request.getResponseJson().getString("balance"));
            String text = Request.getResponseJson().getString("balance") + " " + Request.getResponseJson().getString("currency");
            balanceTV.setText(text);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.w(TAG,e.getMessage());
        }
    }

    @Override
    public void onGetTransactions() {
        startActivity(new Intent(this,MyTransactionsActivity.class));
    }
}
