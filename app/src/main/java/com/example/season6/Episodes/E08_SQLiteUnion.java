package com.example.season6.Episodes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.example.season6.Adapters.E07_Adapter;
import com.example.season6.Adapters.E07_Adapter_Orders;
import com.example.season6.Adapters.E08_Adapter;
import com.example.season6.R;
import com.example.season6.app.E05_dbConnector;
import com.example.season6.app.app;

import java.util.ArrayList;
import java.util.List;

public class E08_SQLiteUnion extends AppCompatActivity {

    E05_dbConnector myDB;
    SQLiteDatabase sqLiteDatabase;
    RecyclerView recyclerView;
    E08_Adapter adapter;
    List<String> list;
    int size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_e08__s_q_lite_union);
        setTitle(getClass().getSimpleName());

        init();

    }

    private void init() {
        myDB = new E05_dbConnector();
        if (E05_dbConnector.checkDB()){
            sqLiteDatabase = myDB.openDataBase();
            app.l("Ok");
        }else {
            app.l("Not Ok");
        }

        recyclerView = findViewById(R.id.recyclerView);
        list         = prepareData();
        adapter      = new E08_Adapter(this , list);
        recyclerView.setLayoutAnimation(new LayoutAnimationController(AnimationUtils.loadAnimation(this , android.R.anim.slide_in_left)));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        app.l("Number : "+size);
    }

    private List<String> prepareData() {
        List<String> list = new ArrayList<>();

        Cursor c = sqLiteDatabase.rawQuery("SELECT "+app.CUSTOMERS.CUSTOMER_NAME+" AS Name , 'Customers' AS Type FROM "+app.tables.CUSTOMERS+
                                    " UNION ALL SELECT  "+app.main.PRODUCT_NAME      +" AS Name , 'Products' AS Type FROM "+app.main.PRODUCTS_TABLE , null);

        while (c.moveToNext()){
            String name = c.getString(c.getColumnIndex("Name"));
            String type = c.getString(c.getColumnIndex("Type"));

            list.add(type+" : "+name);
        }

        size = list.size();
        return list;
    }
}