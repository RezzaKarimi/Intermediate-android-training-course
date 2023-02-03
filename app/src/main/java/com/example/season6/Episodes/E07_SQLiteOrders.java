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
import com.example.season6.R;
import com.example.season6.app.E05_dbConnector;
import com.example.season6.app.app;

import java.util.ArrayList;
import java.util.List;

public class E07_SQLiteOrders extends AppCompatActivity {

    E05_dbConnector myDB;
    SQLiteDatabase sqLiteDatabase;
    RecyclerView recyclerView;
    List<String> list;
    E07_Adapter_Orders adapter;
    String CustomerName , CustomerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_e07__s_q_lite_ordera);

        init();
        setTitle(CustomerName);
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

        if (getIntent().hasExtra(app.CUSTOMERS.CUSTOMER_NAME)){
            CustomerName = getIntent().getStringExtra(app.CUSTOMERS.CUSTOMER_NAME);
        }if (getIntent().hasExtra(app.CUSTOMERS.CUSTOMER_ID)){
            CustomerId   = getIntent().getStringExtra(app.CUSTOMERS.CUSTOMER_ID);
        }

        list = prepareData();
        adapter = new E07_Adapter_Orders(this , list);
        recyclerView.setLayoutAnimation(new LayoutAnimationController(AnimationUtils.loadAnimation(this ,android.R.anim.slide_in_left)));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }


    private List<String> prepareData(){
        List<String> list = new ArrayList<>();

        Cursor c = sqLiteDatabase.rawQuery("SELECT "+app.ORDERS.ORDER_ID+" FROM "+app.tables.ORDERS+
                " WHERE "+app.ORDERS.CUSTOMER_ID+ " = "+CustomerId, null);


        while (c.moveToNext()){
            String orderID = c.getString(c.getColumnIndex(app.ORDERS.ORDER_ID));

            list.add(orderID);
        }

        return list;
    }
}