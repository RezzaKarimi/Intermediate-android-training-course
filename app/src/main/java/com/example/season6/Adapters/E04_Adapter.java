package com.example.season6.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.season6.Episodes.E04_SQLiteSetting;
import com.example.season6.Episodes.E04_SQLiteSingleNote;
import com.example.season6.Objects.E04_Object;
import com.example.season6.R;
import com.example.season6.app.Application;
import com.example.season6.app.app;
import com.example.season6.app.dbConnector;
import com.example.season6.app.spref;
import com.example.season6.interfaces.E04multiListener;

import java.util.List;

public class E04_Adapter extends RecyclerView.Adapter<E04_Adapter.ViewHolder> {

    List<E04_Object> objects;
    dbConnector db;
    Activity activity;
    E04multiListener multiListener;
    public static int count = 0 ;
    public static Boolean multiSelected = false;

    public E04_Adapter(Activity activity ,List<E04_Object> objects , dbConnector db , E04multiListener multiListener ){
        this.activity = activity;
        this.objects = objects;
        this.db = db;
        this.multiListener = multiListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(Application.getContext()).inflate(R.layout.layout_e04_row , parent , false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        E04_Object object = objects.get(position);
        holder.multiActionParent.setBackgroundColor(Color.WHITE);

        holder.number .setText((position+1)+"");
        if (object.getSearch().equals("")){
            holder.name   .setText(object.getName());
            holder.message.setText(object.getMessage());
        }else {//Upper & Lower case Bog//
            String colorFul = "<font color=RED >"+ object.getSearch() + "</font>";
            Spanned nameString = Html.fromHtml(object.getName().replace(object.getSearch() , colorFul));
            holder.name   .setText(nameString);

            Spanned messageString = Html.fromHtml(object.getMessage().replace(object.getSearch() , colorFul));
            holder.message.setText(messageString);
        }



        E04_SQLiteSetting.setFont(holder.fontValue , activity , holder.number);
        E04_SQLiteSetting.setFont(holder.fontValue , activity , holder.name);
        E04_SQLiteSetting.setFont(holder.fontValue , activity , holder.message);

        int size = spref.get(spref.tag.SETTING).getInt(spref.SETTING.FONT_SIZE , 0);
        holder.number.setTextSize(size + 16f);
        holder.name.setTextSize(size + 16f);
        holder.message.setTextSize(size + 16f);

        if (object.getSeen() == 0){
            holder.seen.setImageResource(R.drawable.ic_baseline_remove_red_eye_24);
            holder.seen.setColorFilter(R.color.black);
        }else if (object.getSeen() == 1){
            holder.seen.setImageResource(R.drawable.ic_baseline_check_24);
            holder.seen.setColorFilter(R.color.black);
        }

        int color = spref.get(spref.tag.SETTING).getInt(spref.SETTING.COLOR , spref.SETTING.COLOR_DEFAULT);
        if (spref.get(spref.tag.SETTING).getBoolean(spref.SETTING.CUSTOM_COLOR , false)) {
            holder.number.setTextColor(color);
            holder.name.setTextColor(color);
            holder.message.setTextColor(color);
        }else {
            holder.number.setTextColor(spref.SETTING.COLOR_DEFAULT);
            holder.name.setTextColor(spref.SETTING.COLOR_DEFAULT);
            holder.message.setTextColor(spref.SETTING.COLOR_DEFAULT);
        }

    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView number , name , message;
        AppCompatImageView seen;
        RelativeLayout parent , multiActionParent;
        int fontValue = spref.get(spref.tag.SETTING).getInt(spref.SETTING.FONT , 0);


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            number            = itemView.findViewById(R.id.number);
            name              = itemView.findViewById(R.id.name);
            message           = itemView.findViewById(R.id.message);
            seen              = itemView.findViewById(R.id.seen);
            parent            = itemView.findViewById(R.id.parent);
            multiActionParent = itemView.findViewById(R.id.multiActionParent);

            parent.setOnClickListener(this);
            parent.setOnLongClickListener(this);
            seen.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            E04_Object object = objects.get(getAdapterPosition());
            if (multiSelected) {
                if (count > 0) {
                    if (object.getMultiAction()){

                        objects.get(getAdapterPosition()).setMultiAction(false);
                        parent.setBackgroundColor(Color.WHITE);
                        multiListener.onItemDeselected(getAdapterPosition(), --count);
                    }else {

                        objects.get(getAdapterPosition()).setMultiAction(true);
                        parent.setBackgroundColor(activity.getResources().getColor(R.color.multiSelected));
                        multiListener.onItemSelected(getAdapterPosition(), ++count);

                    }
                    return;
                }

                else multiSelected = false;

            }
            if (v == seen) {

                    int seenValue = object.getSeen();
                    seenValue = seenValue == 0 ? 1 : 0;

                    if (seenValue == 0) {
                        seen.setImageResource(R.drawable.ic_baseline_remove_red_eye_24);
                        seen.setColorFilter(R.color.black);
                    } else if (seenValue == 1) {
                        seen.setImageResource(R.drawable.ic_baseline_check_24);
                        seen.setColorFilter(R.color.black);
                    }

                    object.setSeen(seenValue);
                    objects.set(getAdapterPosition(), object);

                    db.get().execSQL("UPDATE " + app.main.NOTE_TABLE
                            + " SET " + app.main.TABLE_SEEN + " = " + seenValue
                            + " WHERE ID = " + object.getId());

//                ContentValues values = new ContentValues();
//                values.put(app.main.TABLE_SEEN , seenValue);
//                String[] args = { object.getId() +""};
//                db.get().update(app.main.NOTE_TABLE , values , app.main.TABLE_ID + "= ? " , args);
                }
            if (v == parent) {
                    Intent intent = new Intent(activity, E04_SQLiteSingleNote.class);
                    intent.putExtra(app.main.TABLE_ID, objects.get(getAdapterPosition()).getId());
                    activity.startActivity(intent);

                }

        }

        @Override
        public boolean onLongClick(View v) {
            if (!multiSelected || count==0) {
                multiListener.onMultiSelected();
                multiListener.onItemSelected(getAdapterPosition() , ++count);
                multiSelected = true;
                parent.setBackgroundColor(activity.getResources().getColor(R.color.multiSelected));
                objects.get(getAdapterPosition()).setMultiAction(true);

            }
            return true;
        }
    }
}
