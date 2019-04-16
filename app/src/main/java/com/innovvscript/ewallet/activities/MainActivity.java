package com.innovvscript.ewallet.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import com.facebook.common.util.UriUtil;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.innovvscript.ewallet.R;
import com.innovvscript.ewallet.presenter.MyPresenter;

public class MainActivity extends AppCompatActivity implements MyPresenter.MyView {

    private MyPresenter presenter;

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

        presenter = new MyPresenter(this);

    }

    public void login(View v) {

        presenter.tryLogin();

    }

    @Override
    public void onGetToken() {
        Intent intent = new Intent(this, LoggedInActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onGetBalance() {

    }

    @Override
    public void onGetTransactions() {

    }
}
