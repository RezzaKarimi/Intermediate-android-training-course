package com.example.season6.app;

import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class E05_dbConnector extends SQLiteOpenHelper {

    public static SQLiteDatabase dbTest;
    public static E05_dbConnector myDbHelper;
    public static final String DbName = "database.db";
    public SQLiteDatabase database;

    public E05_dbConnector() {
        super(Application.getContext(), DbName , null, 1);
    }

    public static Boolean checkDB(){
        app.l("checkDB");

        myDbHelper = new E05_dbConnector();

        try {

            dbTest = myDbHelper.getReadableDatabase();

        }
        catch (SQLiteException e){

            return copyDB();
        }
        catch (RuntimeException e){

            return copyDB();
        }
        try {  //one of the ways to check db is exist or not
            Cursor c = dbTest.rawQuery(" SELECT CustomerID FROM CUSTOMERS LIMIT 1" , null);
            if (c.getCount()==1){
                return true;
            }
        }
        catch (SQLiteException e){
            app.l(e.toString());
            return copyDB();
        }

        return false;
    }


    private static Boolean copyDB(){
        app.l("copyDB");

        AssetManager assetManager = Application.getContext().getAssets();

        InputStream  in  = null;
        OutputStream out = null;

        String fileName = "database.db";

        Boolean result = false;

        try {

            in            = assetManager.open(DbName);
            File database = new File("/data/data/com.example.season6/databases/"+fileName);

            out           = new FileOutputStream(database);

            copyFile(in , out);

            result = true;

        } catch (IOException e) {
            app.l(e.toString());


        }

        finally {

            if (in != null){
                try {
                    in.close();
                }catch (IOException e){
                    app.l(e.toString());
                }
            }
            if (out != null){
                try {
                    out.close();
                }catch (IOException e){
                    app.l(e.toString());
                }
            }

        }

        return result;
    }

    public static void copyFile(InputStream in , OutputStream out) throws IOException {
        app.l("copyFile");

        byte[] buffer = new byte[1024];
        int read;

        while ((read = in.read(buffer)) != -1){

            out.write(buffer , 0 , read);               // Copy method can use every where
        }
    }

    public SQLiteDatabase openDataBase(){

        String dbPath = "/data/data/com.example.season6/databases/"+DbName;
        try {
            database = SQLiteDatabase.openDatabase(dbPath , null , SQLiteDatabase.OPEN_READWRITE);
        }
        catch (SQLiteException e){
            app.l(e.toString());
            return null;
        }

        return database;
    }





















    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
