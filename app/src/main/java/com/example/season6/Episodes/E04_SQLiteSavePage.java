package com.example.season6.Episodes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.season6.R;
import com.example.season6.app.app;
import com.example.season6.app.dbConnector;

public class E04_SQLiteSavePage extends AppCompatActivity implements View.OnClickListener {

    AppCompatImageView back , save;
    dbConnector db;
    EditText name , message;
    TextView title;
    int id;
    public static final String ACTION_ADD = "ACTION_ADD";
    public static final String ACTION_EDIT = "ACTION_EDIT";

    public static  String ACTION = ACTION_ADD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_e04__sqlite_listener);
        init();
    }

    private void init() {
        back    = findViewById(R.id.back);
        save    = findViewById(R.id.edit);
        name    = findViewById(R.id.name);
        message = findViewById(R.id.message);
        title   = findViewById(R.id.Title);

        db      = new dbConnector(this , null , null , 1);

        if (getIntent().hasExtra(ACTION)){
            ACTION = getIntent().getExtras().getString(ACTION);

        }
        if (ACTION.equals(ACTION_EDIT)) {


            id = getIntent().getIntExtra(app.main.TABLE_ID , 0);
            Cursor c = db.get().rawQuery(" SELECT * FROM "+app.main.NOTE_TABLE+
                    " WHERE "+app.main.TABLE_ID+
                    " = "+id, null);

            while (c.moveToNext()){
                name.setText(c.getString(c.getColumnIndex(app.main.TABLE_NAME)));
                message.setText(c.getString(c.getColumnIndex(app.main.TABLE_MESSAGE)));
                title.setText(R.string.editNote);
            }
            c.close();
        }
        back.setOnClickListener(this);
        save.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == back){
            finish();
        }else if (v == save){
            if (ACTION.equals(ACTION_ADD)) {
                save();
            }else if (ACTION.equals(ACTION_EDIT)){
                edit();
            }
        }
    }

    private void edit() {
        if (name.getText().toString().equals("")) {
            app.t("Name is Null!");
            return;
        }
        if (message.getText().toString().equals("")) {
            app.t("Message is Null!");
            return;
        }
        ContentValues values = new ContentValues();
        values.put(app.main.TABLE_NAME , name.getText().toString());
        values.put(app.main.TABLE_MESSAGE , message.getText().toString());
        String[] args= {id+""};
        db.get().update(app.main.NOTE_TABLE ,values, app.main.TABLE_ID+" = ? " , args);
        finish();
        app.t("Saved");
    }

    private void save() {
        if (name.getText().toString().equals("")) {
            app.t("Name is Null!");
            return;
        }
        if (message.getText().toString().equals("")) {
            app.t("Message is Null!");
            return;
        }

        ContentValues values = new ContentValues();
        values.put(app.main.TABLE_NAME    , name.getText().toString());
        values.put(app.main.TABLE_MESSAGE , message.getText().toString());

        try {


            long id = db.get().insert(app.main.NOTE_TABLE , null , values);
            app.t("Data inserted Id "+ id);
            finish();

        }catch (SQLiteException e){
            app.t("Error message :"+ e.getMessage());
        }

    }
}