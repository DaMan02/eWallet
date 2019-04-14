package com.innovvscript.ewallet.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.View;

import com.innovvscript.ewallet.R;
import com.innovvscript.ewallet.TransactionAdapter;

public class MyTransactionsActivity extends AppCompatActivity {

    private SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_transactions);

    RecyclerView recyclerView = findViewById(R.id.recycler_view_id);

    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    recyclerView.setHasFixedSize(true);
        TransactionAdapter transactionAdapter = new TransactionAdapter();
        recyclerView.setAdapter(transactionAdapter);
        transactionAdapter.notifyDataSetChanged();
    }


    public void onBackClick(View v){
        onBackPressed();
    }
}
