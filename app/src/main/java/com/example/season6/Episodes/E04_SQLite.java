package com.example.season6.Episodes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.season6.Adapters.E04_Adapter;
import com.example.season6.Adapters.E17_Dialog;
import com.example.season6.Objects.E04_Object;
import com.example.season6.Objects.E17Dialog_object;
import com.example.season6.R;
import com.example.season6.app.app;
import com.example.season6.app.dbConnector;
import com.example.season6.app.spref;
import com.example.season6.interfaces.E04multiListener;
import com.example.season6.interfaces.E17DialogListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class E04_SQLite extends AppCompatActivity implements
        View.OnClickListener, E04multiListener, E17DialogListener {

    List<E04_Object> list;
    E04_Adapter adapter;
    RecyclerView recyclerView;
    RelativeLayout parent , multiSelectedBar;
    dbConnector db;
    FloatingActionButton fab;
    AppCompatImageView sort_ic , setting_ic , search_ic , allUnSeen , allSeen , allDelete ;
    EditText searchBox;
    TextView counter;
    E17_Dialog dialog;
    public static String search_input = "";
    boolean resume = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_e04__db_connector);

        init();

        app.l(db.getDatabaseName());

    }

    private void init() {
        db               = new dbConnector(this , null , null , 0);
        fab              = findViewById(R.id.fab);
        sort_ic          = findViewById(R.id.sort_ic);
        setting_ic       = findViewById(R.id.setting_ic);
        search_ic        = findViewById(R.id.search_ic);
        searchBox        = findViewById(R.id.searchBox);
        recyclerView     = findViewById(R.id.recyclerView);
        parent           = findViewById(R.id.parent);
        multiSelectedBar = findViewById(R.id.multiSelectedBar);
        counter          = findViewById(R.id.counter);
        allUnSeen        = findViewById(R.id.allUnSeen);
        allSeen          = findViewById(R.id.allSeen);
        allDelete        = findViewById(R.id.allDelete);

        resume           = true;

        list             = prepareDate("");

        sort_ic.setOnClickListener(this);
        setting_ic.setOnClickListener(this);
        search_ic.setOnClickListener(this);
        fab.setOnClickListener(this);
        allDelete.setOnClickListener(this);
        allUnSeen.setOnClickListener(this);
        allSeen.setOnClickListener(this);
        adapter = new E04_Adapter(this ,list ,db , this );


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setLayoutAnimation(new LayoutAnimationController(AnimationUtils.loadAnimation(this , android.R.anim.slide_in_left)));
        recyclerView.setAdapter(adapter);


        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                search_input = s.toString();
                list.clear();
                list.addAll(prepareDate(search_input));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private List<E04_Object> prepareDate(String search_input) {
        List<E04_Object> list = new ArrayList<>();
        Cursor c ;
        if (searchBox.getVisibility() == View.VISIBLE){

            c = db.get().rawQuery("SELECT * FROM "+ app.main.NOTE_TABLE + " WHERE " + app.main.TABLE_NAME+" LIKE '%"+search_input+"%' OR "
                    +app.main.TABLE_MESSAGE+" LIKE '%"+search_input+"%' "
                    +" ORDER BY "
                    +app.main.TABLE_ID+" "+ spref.get(spref.tag.FORM)
                    .getString(spref.Form.ORDER,spref.Form.ORDER_VALUE), null );

        }else {

            c = db.get().rawQuery("SELECT * FROM "+ app.main.NOTE_TABLE + " ORDER BY "
                    +app.main.TABLE_ID+" "+ spref.get(spref.tag.FORM)
                    .getString(spref.Form.ORDER,spref.Form.ORDER_VALUE), null );
        }

        while (c.moveToNext()){

            int id         = c.getInt(c.getColumnIndex(app.main.TABLE_ID));
            int seen       = c.getInt(c.getColumnIndex(app.main.TABLE_SEEN));
            String name    = c.getString(c.getColumnIndex(app.main.TABLE_NAME));
            String message = c.getString(c.getColumnIndex(app.main.TABLE_MESSAGE));

            E04_Object object = new E04_Object();
            object.setId(id);
            object.setSeen(seen);
            object.setName(name);
            object.setMessage(message);
            object.setSearch(search_input);

            list.add(object);
        }


        c.close();
        return list;
    }



    @Override
    public void onClick(View v) {
        if (v == allSeen){

            String inString = "-1";
            for (E04_Object object : list){
                if (object.getMultiAction()){
                    inString += ","+object.getId();
                }
            }
            db.get().execSQL("UPDATE "+app.main.NOTE_TABLE + " SET "+app.main.TABLE_SEEN+" = 1 WHERE "+app.main.TABLE_ID+" IN ("+ inString +")" );

            toggleMultiAction(false);
            E04_Adapter.multiSelected = false;
            E04_Adapter.count = 0;
            list.clear();
            list.addAll(prepareDate(searchBox.getText().toString())) ;
            adapter.notifyDataSetChanged();

        }
        if (v == allUnSeen){

            String inString = "-1";
            for (E04_Object object : list){
                if (object.getMultiAction()){
                    inString += ","+object.getId();
                }
            }
            db.get().execSQL("UPDATE "+app.main.NOTE_TABLE + " SET "+app.main.TABLE_SEEN+" = 0 WHERE "+app.main.TABLE_ID+" IN ("+ inString +")" );

            toggleMultiAction(false);
            E04_Adapter.multiSelected = false;
            E04_Adapter.count = 0;
            list.clear();
            list.addAll(prepareDate(searchBox.getText().toString())) ;
            adapter.notifyDataSetChanged();


        }
        if (v == allDelete){
            delete();

        }
        if (v == fab){
            Intent intent = new Intent(E04_SQLite.this , E04_SQLiteSavePage.class);
            intent.putExtra(E04_SQLiteSavePage.ACTION , E04_SQLiteSavePage.ACTION_ADD);
            startActivity(intent);

        }else if (v == sort_ic){

            spref.get(spref.tag.FORM).edit().putString(spref.Form.ORDER , spref.get(spref.tag.FORM).getString(spref.Form.ORDER ,spref.Form.ORDER_VALUE).equals("ASC")?"DESC":"ASC").apply();
            list.clear();
            list.addAll(prepareDate(""));
            adapter.notifyDataSetChanged();
        }else if (v == setting_ic){
            startActivity(new Intent(E04_SQLite.this , E04_SQLiteSetting.class));
        }else if (v == search_ic) {

            if (searchBox.getVisibility() == View.VISIBLE) {

                list.clear();
                list.addAll(prepareDate(search_input));
                adapter.notifyDataSetChanged();


            } else {
                Animation animation = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
                searchBox.startAnimation(animation);
                searchBox.setVisibility(View.VISIBLE);
                searchBox.requestFocus();
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        setting_ic.setVisibility(View.GONE);
                        sort_ic.setVisibility(View.GONE);
                        search_ic.setColorFilter(Color.BLACK);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

            }
        }


    }

    @Override
    protected void onResume() {

        toggleMultiAction(false);

        E04_Adapter.multiSelected = false;
        E04_Adapter.count = 0;
        list.clear();
        list.addAll(prepareDate(searchBox.getText().toString())) ;
        adapter.notifyDataSetChanged();

        if (resume){
            if (searchBox.getVisibility() == View.VISIBLE){

                list.clear();
                list.addAll(prepareDate(search_input));
                if (spref.get(spref.tag.SETTING).getBoolean(spref.SETTING.DARK_MODE , false)){
                    parent.setBackgroundColor(Color.BLACK);
                }else parent.setBackgroundColor(Color.WHITE);
                adapter.notifyDataSetChanged();

            }else {
                E04_Adapter.multiSelected = false;
                list.clear();
                list.addAll(prepareDate(""));
                if (spref.get(spref.tag.SETTING).getBoolean(spref.SETTING.DARK_MODE , false)){
                    parent.setBackgroundColor(Color.BLACK);
                }else parent.setBackgroundColor(Color.WHITE);
                adapter.notifyDataSetChanged();
            }
        }
        super.onResume();
    }

    @Override
    public void onBackPressed() {

        if (searchBox.getVisibility() == View.VISIBLE){
            hide_search_box();
            list.clear();
            list.addAll(prepareDate(""));
            search_input = "";
            searchBox.setText("");
            adapter.notifyDataSetChanged();

        }else if (multiSelectedBar.getVisibility() == View.VISIBLE){
            toggleMultiAction(false);

            E04_Adapter.multiSelected = false;
            E04_Adapter.count = 0;
            list.clear();
            list.addAll(prepareDate(searchBox.getText().toString())) ;
            adapter.notifyDataSetChanged();
        }else super.onBackPressed();

    }


    private void hide_search_box(){
        Animation animation = AnimationUtils.loadAnimation(this , android.R.anim.slide_out_right);
        searchBox.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                searchBox.setVisibility(View.GONE);
                sort_ic.setVisibility(View.VISIBLE);
                setting_ic.setVisibility(View.VISIBLE);
                search_ic.setColorFilter(Color.WHITE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    public void onMultiSelected() {
        toggleMultiAction(true);

    }

    @Override
    public void onItemSelected(int position, int count) {

        counter.setText("Count: " + count);
        E04_Object object = list.get(position);
        object.setMultiAction(true);
        list.set(position , object);
    }

    @Override
    public void onItemDeselected(int position, int count) {
        counter.setText("Count: " + count);
        E04_Object object = list.get(position);
        object.setMultiAction(false);
        list.set(position , object);

    }




    private void toggleMultiAction(Boolean show) {
        if(show) {
            multiSelectedBar.setVisibility(View.VISIBLE);
            multiSelectedBar.startAnimation(AnimationUtils.loadAnimation(this , android.R.anim.slide_in_left));
        }
        else {
            if(multiSelectedBar.getVisibility() == View.GONE) return;
            Animation animation = AnimationUtils.loadAnimation(this , android.R.anim.slide_out_right);
            multiSelectedBar.startAnimation(animation);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {

                    multiSelectedBar.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }
    }


    private void delete() {
        E17Dialog_object object = new E17Dialog_object()
                .setTitle(getString(R.string.deleteFile))
                .setMessage(getString(R.string.delete_All_Message))
                .setColor(R.color.alertDialog)
                .setPositive(getString(R.string.positive))
                .setPositiveIcon(0)
                .setNegativeIcon(0)
                .setNegative(getString(R.string.negative))
                .setPositive_boolean(true)
                .setNegative_boolean(true)
                .setNeutral_boolean(false)
                .setDismiss_boolean(true)
                .setInput_boolean(false)
                .setTitleIcon(R.drawable.ic_baseline_delete_forever_24)
                .setTitleColor(R.color.alertDialog)
                .setPositiveColor(R.color.separator)
                .setNegativeColor(R.color.separator)
                .setListener(this);

        dialog = new E17_Dialog(this, object);
        dialog.show();
        dialog.setCancelable(true);
    }

    @Override
    public void onPositive(String input) {
        dialog.cancel();
    }

    @Override
    public void onNegative(String input) {

        String inString = "-1";
        for (E04_Object object : list){
            if (object.getMultiAction()){
                inString += ","+object.getId();
            }
        }
        db.get().execSQL(" DELETE FROM "+app.main.NOTE_TABLE +" WHERE "+app.main.TABLE_ID+" IN ("+ inString +")" );

        toggleMultiAction(false);
        E04_Adapter.multiSelected = false;
        E04_Adapter.count = 0;
        list.clear();
        list.addAll(prepareDate(searchBox.getText().toString())) ;
        adapter.notifyDataSetChanged();
        dialog.hide();
    }

    @Override
    public void onNeutral(String input) {

    }

    @Override
    public void Dismiss(String input) {

    }
}