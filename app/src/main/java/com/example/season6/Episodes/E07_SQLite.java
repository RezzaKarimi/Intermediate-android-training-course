package com.example.season6.Episodes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.example.season6.Adapters.E07_Adapter;
import com.example.season6.R;
import com.example.season6.app.E05_dbConnector;
import com.example.season6.app.app;

import java.util.ArrayList;
import java.util.List;

public class E07_SQLite extends AppCompatActivity {

    E05_dbConnector myDB;
    SQLiteDatabase sqLiteDatabase;
    RecyclerView recyclerView;
    E07_Adapter adapter;
    List<String[]> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_e07__s_q_lite);
        setTitle(app.tables.CUSTOMERS);

        init();
    }

    private void init() {
        myDB = new E05_dbConnector();
        if (E05_dbConnector.checkDB()){
            sqLiteDatabase = myDB.openDataBase();
            app.l("ok");
        }else {
            app.l("Not Ok");
        }
        recyclerView = findViewById(R.id.recyclerView);

        list = prePareData();
        adapter      = new E07_Adapter(this , list );
        recyclerView.setLayoutAnimation(new LayoutAnimationController(AnimationUtils.loadAnimation(this , android.R.anim.slide_in_left)));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private List<String[]> prePareData(){
        List<String[]> list = new ArrayList<>();

        Cursor c = sqLiteDatabase.rawQuery("SELECT "+ app.CUSTOMERS.CUSTOMER_ID+" , "+app.CUSTOMERS.CUSTOMER_NAME+" FROM "+app.tables.CUSTOMERS , null);



        while (c.moveToNext()){
            String[] item = new String[2];
            String name = c.getString(c.getColumnIndex(app.CUSTOMERS.CUSTOMER_NAME));
            String id   = c.getString(c.getColumnIndex(app.CUSTOMERS.CUSTOMER_ID));

            item[0] = id;
            item[1] = name;

            list.add(item);

        }


        return list;
    }
}