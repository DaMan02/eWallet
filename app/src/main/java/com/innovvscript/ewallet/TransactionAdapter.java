package com.innovvscript.ewallet;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.innovvscript.ewallet.model.Request;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.MyViewHolder> {


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater
                .inflate(R.layout.transaction_row, parent,
                        false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        try {  Log.w("try","block");
            JSONArray jsonArray = new JSONArray(Request.getResponseString());
            for(int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);

                holder.id.setText("ID: " + object.getString("id"));
                holder.date.setText(object.getString("date"));
                holder.desc.setText(object.getString("description"));
                holder.amt.setText(object.getString("amount"));
                holder.currency.setText(object.getString("currency"));

            }
        } catch (JSONException | NullPointerException e) {
            e.printStackTrace();
            Log.w("exp",e.getMessage());
        }

    }


    @Override
    public int getItemCount() {
        JSONArray jsonArray = null;
        int i = 4;
        try {
            jsonArray = new JSONArray(Request.getResponseString());
            for(i = 0; i < jsonArray.length(); i++);
        } catch (JSONException | NullPointerException e) {
            e.printStackTrace();
        }
        return i;
    }

   public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView date,id,desc,amt,currency;
        public MyViewHolder(View view){
            super(view);

            id = view.findViewById(R.id.transac_id);
            date = view.findViewById(R.id.date_id);
            desc = view.findViewById(R.id.desc_id);
            amt = view.findViewById(R.id.amt_id);
            currency = view.findViewById(R.id.curr_id);
        }
    }
}
