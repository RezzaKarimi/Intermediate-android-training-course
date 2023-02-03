package com.example.season6.Episodes;

import androidx.appcompat.app.AppCompatActivity;

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

public class E06_SQLiteTable extends AppCompatActivity {

    E05_dbConnector myDB;
    SQLiteDatabase sqLiteDatabase;
    TableLayout table;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_e06__s_q_lite_table);

        setTitle(getClass().getSimpleName());
        init();
        read_Data();

    }

    private void init() {

        myDB = new E05_dbConnector();
        if (E05_dbConnector.checkDB()) {
            app.l("ok");

            sqLiteDatabase = myDB.openDataBase();
        }else {
            app.l("not ready");
        }

        table = findViewById(R.id.table);
    }

    private void read_Data(){

        Cursor c = sqLiteDatabase.rawQuery(" SELECT * FROM "+ app.main.PRODUCTS_TABLE , null);
        while (c.moveToNext()){

            int    ProductID_Value      = c.getInt(c.getColumnIndex   (app.main.PRODUCT_ID));
            String ProductName_Value    = c.getString(c.getColumnIndex(app.main.PRODUCT_NAME));
            int    SupplierID_Value     = c.getInt(c.getColumnIndex   (app.main.SUPPLIER_ID));
            int    CategoryID_Value     = c.getInt(c.getColumnIndex   (app.main.CATEGORY_ID));
            String Unit_Value           = c.getString(c.getColumnIndex(app.main.UNIT));
            double Price_Value          = c.getDouble(c.getColumnIndex(app.main.PRICE));

            TableRow tableRow = (TableRow) LayoutInflater.from(this).inflate(R.layout.layout_e06_row , null);

            TextView ProductID   = tableRow.findViewById(R.id.ProductID);
            TextView ProductName = tableRow.findViewById(R.id.ProductName);
            TextView SupplierID  = tableRow.findViewById(R.id.SupplierID);
            TextView CategoryID  = tableRow.findViewById(R.id.CategoryID);
            TextView Unit        = tableRow.findViewById(R.id.Unit);
            TextView Price       = tableRow.findViewById(R.id.Price);

            ProductID.setText(ProductID_Value + "");
            ProductName.setText(ProductName_Value);
            SupplierID.setText(SupplierID_Value + "");
            CategoryID.setText(CategoryID_Value + "");
            Unit.setText(Unit_Value);
            Price.setText(Price_Value + "");

            table.addView(tableRow);
            table.requestLayout();

        }
    }
}