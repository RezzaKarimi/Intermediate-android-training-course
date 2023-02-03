package com.example.season6.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.text.style.AlignmentSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.season6.Episodes.E07_SQLiteOrders;
import com.example.season6.Episodes.E07_SQLiteProducts;
import com.example.season6.R;
import com.example.season6.app.app;

import java.util.List;

public class E07_Adapter_Orders extends RecyclerView.Adapter<E07_Adapter_Orders.ViewHolder> {


    Activity activity;
    List<String> list;

    public E07_Adapter_Orders(Activity activity ,List<String> list ){
        this.activity = activity;
        this.list     = list;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.layout_e07_row , parent , false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        RelativeLayout parent;
        TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            parent = itemView.findViewById(R.id.parent);
            name   = itemView.findViewById(R.id.name);

            parent.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            Intent intent = new Intent(activity , E07_SQLiteProducts.class);
            intent.putExtra(app.ORDERS.ORDER_ID , list.get(getAdapterPosition()) );
            activity.startActivity(intent);

        }
    }
}
