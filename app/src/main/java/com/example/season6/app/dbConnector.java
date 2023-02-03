package com.example.season6.app;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class dbConnector extends SQLiteOpenHelper {


    SQLiteDatabase sqLiteDatabase;

    public dbConnector(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, app.main.DB_NAME , factory, app.main.DB_VERSION);
        this.CreateTable();
        app.l("dbConnector");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.sqLiteDatabase = db;
        app.l("onCreate");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public SQLiteDatabase get(){
        return this.getWritableDatabase();
    }

    private void CreateTable(){

//        get().execSQL(" DROP TABLE IF EXISTS "+app.main.NOTE_TABLE);

        String noteTable =
                " CREATE TABLE IF NOT EXISTS "   +
                        app.main.NOTE_TABLE      + "  (                                   " +
                        app.main.TABLE_ID        + " INTEGER PRIMARY KEY AUTOINCREMENT  , " +
                        app.main.TABLE_NAME      + " VARCHAR (30)                       , " +
                        app.main.TABLE_MESSAGE   + " VARCHAR (10000)                    , " +
                        app.main.TABLE_SEEN      + " INTEGER DEFAULT (0)                , " +
                        app.main.TABLE_POSITION  + " INTEGER DEFAULT (0)                  " +
                " );";
        get().execSQL(noteTable);
        app.l("Table notes Created");
    }
}
