package com.example.season6.Episodes;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.example.season6.R;
import com.example.season6.app.E05_dbConnector;
import com.example.season6.app.app;

public class E05_SQLite extends AppCompatActivity {

    E05_dbConnector myDB;
    SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_e05__s_q_lite);

        myDB = new E05_dbConnector();
        if (E05_dbConnector.checkDB()){
            app.l("ok");

            sqLiteDatabase = myDB.openDataBase();
            Cursor c = sqLiteDatabase.rawQuery(" SELECT * FROM Shippers ", null);

            while (c.moveToNext()){
                app.l(c.getString(c.getColumnIndex("ShipperName")));
                app.l(c.getString(c.getColumnIndex("Phone")));
                app.l("-----------------------------------------");
            }

        }else {
            app.l("not ready");
        }




    }
}