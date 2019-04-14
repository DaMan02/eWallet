package com.innovvscript.ewallet.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.facebook.common.util.UriUtil;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.innovvscript.ewallet.R;
import com.innovvscript.ewallet.activities.LoggedInActivity;
import com.innovvscript.ewallet.httpsRequest.HttpsUtil;
import com.innovvscript.ewallet.model.Request;

import java.io.IOException;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_main);

        SimpleDraweeView simpleDraweeView = findViewById(R.id.my_image_view);
        Uri uri = new Uri.Builder()
                .scheme(UriUtil.LOCAL_RESOURCE_SCHEME)
                .path(String.valueOf(R.drawable.dark))
                .build();
        simpleDraweeView.setImageURI(uri);

    }

    public void login(View v) {

        try {
            HttpsUtil httpsUtil = new HttpsUtil(this,"login");
            HttpsURLConnection connection = httpsUtil.getHttpsConnection("POST","login");
            httpsUtil.connectAsync();
            Request.setHttpsURLConnection(connection);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("login pressed",e.getMessage());
        }

    }

    public void next(){
        startActivity(new Intent(this, LoggedInActivity.class));
        finish();
    }

}
