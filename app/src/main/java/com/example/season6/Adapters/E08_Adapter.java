package com.example.season6.Adapters;

import android.app.Activity;
import android.content.LocusId;
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
import java.util.List;

public class E08_Adapter extends RecyclerView.Adapter<E08_Adapter.ViewHolder>  {

    Activity activity;
    List<String> list;

    public E08_Adapter(Activity activity , List<String> list){
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

    public class ViewHolder extends RecyclerView.ViewHolder  {
        RelativeLayout parent;
        TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            parent = itemView.findViewById(R.id.parent);
            name   = itemView.findViewById(R.id.name);

        }

    }
}
