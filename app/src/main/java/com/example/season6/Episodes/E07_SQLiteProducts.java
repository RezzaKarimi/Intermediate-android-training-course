package com.example.season6.Episodes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.season6.R;
import com.example.season6.app.E05_dbConnector;
import com.example.season6.app.app;

public class E07_SQLiteProducts extends AppCompatActivity {

    String orderID;
    E05_dbConnector myDB;
    SQLiteDatabase sqLiteDatabase;
    TableLayout table;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_e06__s_q_lite_table);

        init();
        readData();
        setTitle("Orders: "+orderID);
    }


    private void init() {
        if (getIntent().hasExtra(app.ORDERS.ORDER_ID)){
            orderID = getIntent().getStringExtra(app.ORDERS.ORDER_ID);
        }

        myDB = new E05_dbConnector();
        if (E05_dbConnector.checkDB()){
            sqLiteDatabase = myDB.openDataBase();
            app.l("Ok");
        }else {
            app.l("Not Ok");
        }

        table = findViewById(R.id.table);
    }


    private void readData() {

        Cursor c = sqLiteDatabase.rawQuery("SELECT * FROM "+app.main.PRODUCTS_TABLE+
                                               " WHERE "+app.main.PRODUCT_ID+" IN (SELECT "+app.main.PRODUCT_ID+
                                               " FROM "+app.tables.ORDERS_DETAILS+" WHERE "+ app.ORDERS.ORDER_ID+
                                               " = "+ orderID+")", null);



        while (c.moveToNext()){

            int    productID     = c.getInt(c.getColumnIndex(app.main.PRODUCT_ID));
            String productName   = c.getString(c.getColumnIndex(app.main.PRODUCT_NAME));
            int    supplierID    = c.getInt(c.getColumnIndex(app.main.SUPPLIER_ID));
            int    categoryID    = c.getInt(c.getColumnIndex(app.main.CATEGORY_ID));
            String unit          = c.getString(c.getColumnIndex(app.main.UNIT));
            Double price         = c.getDouble(c.getColumnIndex(app.main.PRICE));


            TableRow tableRow    = (TableRow) LayoutInflater.from(this).inflate(R.layout.layout_e06_row , null);

            TextView ProductID   = tableRow.findViewById(R.id.ProductID);
            TextView ProductName = tableRow.findViewById(R.id.ProductName);
            TextView SupplierID  = tableRow.findViewById(R.id.SupplierID);
            TextView CategoryID  = tableRow.findViewById(R.id.CategoryID);
            TextView Unit        = tableRow.findViewById(R.id.Unit);
            TextView Price       = tableRow.findViewById(R.id.Price);

            ProductID.setText(productID+"");
            ProductName.setText(productName);
            SupplierID.setText(supplierID+"");
            CategoryID.setText(categoryID+"");
            Unit.setText(unit);
            Price.setText(price+"");


            table.addView(tableRow);
            table.requestLayout();

        }
    }


}