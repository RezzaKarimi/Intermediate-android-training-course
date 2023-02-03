package com.example.season6.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.season6.R;
import com.example.season6.app.app;

import java.nio.MappedByteBuffer;

public class E09_Adapter extends RecyclerView.Adapter<E09_Adapter.ViewHolder> {

    Activity activity;
    String[] list;

    public E09_Adapter(Activity activity , String[] list){
        this.activity = activity;
        this.list     = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.layout_e09_row , parent , false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.number.setText(position+1+"");
        holder.name.setText(list[position]);
    }

    @Override
    public int getItemCount() {
        return list.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        RelativeLayout parent;
        TextView number , name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.parent);
            number = itemView.findViewById(R.id.number);
            name   = itemView.findViewById(R.id.name);

            parent.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            try {
                Class<?> className = Class.forName("com.example.season6.Episodes."+list[getAdapterPosition()]);
                activity.startActivity(new Intent(activity , className));
            } catch (ClassNotFoundException e) {
                app.l(e.toString());
            }
        }
    }
}
