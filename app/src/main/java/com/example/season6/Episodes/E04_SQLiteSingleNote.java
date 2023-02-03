package com.example.season6.Episodes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.season6.Adapters.E17_Dialog;
import com.example.season6.Objects.E17Dialog_object;
import com.example.season6.R;
import com.example.season6.app.app;
import com.example.season6.app.dbConnector;
import com.example.season6.app.spref;
import com.example.season6.interfaces.E17DialogListener;

public class E04_SQLiteSingleNote extends AppCompatActivity implements View.OnClickListener , E17DialogListener {

    AppCompatImageView edit, delete, last_position , back , seen;
    TextView name , message;
    RelativeLayout parent;
    ScrollView scrollView;
    dbConnector db;
    int id = -1;
    int seenValue = 0;
    public static int position;
    E17_Dialog dialog;
    String nameString;
    Boolean onResume = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_e04__s_q_lite_onclick);

        init();
    }

    private void init() {
        edit          = findViewById(R.id.edit);
        delete        = findViewById(R.id.delete);
        back          = findViewById(R.id.back);
        seen          = findViewById(R.id.seen);
        name          = findViewById(R.id.name);
        message       = findViewById(R.id.message);
        scrollView    = findViewById(R.id.scrollView);
        last_position = findViewById(R.id.last_position);
        parent        = findViewById(R.id.parent);


        onResume= true;

        edit.setOnClickListener(this);
        delete.setOnClickListener(this);
        back.setOnClickListener(this);
        seen.setOnClickListener(this);
        last_position.setOnClickListener(this);

        db      = new dbConnector(this , null,null,1);

        prepareData();
    }

    private void prepareData() {
        if (getIntent().hasExtra(app.main.TABLE_ID)){
            id = getIntent().getIntExtra(app.main.TABLE_ID , 0);
            Cursor c = db.get().rawQuery(" SELECT * FROM "+app.main.NOTE_TABLE +" WHERE "+app.main.TABLE_ID + " = "+id, null);
            String colorFul = "<font color=RED>"+ E04_SQLite.search_input +"</font>";
            while (c.moveToNext()){
                nameString = c.getString(c.getColumnIndex(app.main.TABLE_NAME));
                String messageString = c.getString(c.getColumnIndex(app.main.TABLE_MESSAGE));
                if (E04_SQLite.search_input.equals("")){
                    name.setText(c.getString(c.getColumnIndex(app.main.TABLE_NAME)));
                    message.setText(c.getString(c.getColumnIndex(app.main.TABLE_MESSAGE)));
                }else {

                    Spanned nameSearch = Html.fromHtml(nameString.replace(E04_SQLite.search_input , colorFul));
                    name.setText(nameSearch);

                    Spanned messageSearch = Html.fromHtml(messageString.replace(E04_SQLite.search_input , colorFul));
                    message.setText(messageSearch);
                }

                seenValue = c.getInt(c.getColumnIndex(app.main.TABLE_SEEN));
                position = c.getInt(c.getColumnIndex(app.main.TABLE_POSITION));

                E04_SQLiteSetting.setFont(spref.get(spref.tag.SETTING).getInt(spref.SETTING.FONT,0) , this , name);
                E04_SQLiteSetting.setFont(spref.get(spref.tag.SETTING).getInt(spref.SETTING.FONT,0) , this , message);

                int size = spref.get(spref.tag.SETTING).getInt(spref.SETTING.FONT_SIZE , 0);
                name.setTextSize((size + 16f)*2);
                message.setTextSize(size + 16f);

                message.setLineSpacing(spref.get(spref.tag.SETTING).getInt(spref.SETTING.FONT_SPACING , 0) , 1);

                int color = spref.get(spref.tag.SETTING).getInt(spref.SETTING.COLOR , spref.SETTING.COLOR_DEFAULT);
                if (spref.get(spref.tag.SETTING).getBoolean(spref.SETTING.CUSTOM_COLOR , false)) {
                    name.setTextColor(color);
                    message.setTextColor(color);
                }else {
                    name.setTextColor(spref.SETTING.COLOR_DEFAULT);
                    message.setTextColor(spref.SETTING.COLOR_DEFAULT);
                }

                if (spref.get(spref.tag.SETTING).getBoolean(spref.SETTING.DARK_MODE , false)){
                    parent.setBackgroundColor(Color.BLACK);
                }else {
                    parent.setBackgroundColor(Color.WHITE);
                }

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (spref.get(spref.tag.SETTING).getBoolean(spref.SETTING.AUTO_BACK , false)){
                            scrollView.smoothScrollTo( 0 , position);
                        }
                    }
                }, 100);

                if (seenValue == 0){
                    seen.setImageResource(R.drawable.ic_baseline_remove_red_eye_24);
                    seen.setColorFilter(R.color.black);
                }else if (seenValue == 1){
                    seen.setImageResource(R.drawable.ic_baseline_check_24);
                    seen.setColorFilter(R.color.black);
                }
            }
            c.close();
        }


    }

    @Override
    public void onClick(View v) {
        if (v == edit){
            Intent intent = new Intent(E04_SQLiteSingleNote.this , E04_SQLiteSavePage.class);
            intent.putExtra(E04_SQLiteSavePage.ACTION , E04_SQLiteSavePage.ACTION_EDIT);
            intent.putExtra(app.main.TABLE_ID , id);
            startActivity(intent);

        } if (v == delete){

            delete();

        }if (v == back){
            onBackPressed();

        }else if (v == seen){

            seenValue = seenValue==0?1:0;

            if (seenValue == 0){
                seen.setImageResource(R.drawable.ic_baseline_remove_red_eye_24);
                seen.setColorFilter(R.color.black);
            }else if (seenValue == 1){
                seen.setImageResource(R.drawable.ic_baseline_check_24);
                seen.setColorFilter(R.color.black);
            }



            db.get().execSQL(" UPDATE "+ app.main.NOTE_TABLE+ " SET "+app.main.TABLE_SEEN +" = " +seenValue +
                    " WHERE " + app.main.TABLE_ID + " = " + id);

        }else if (v == last_position){
            scrollView.smoothScrollTo(0 , position );
        }
    }

    private void delete() {
        E17Dialog_object object = new E17Dialog_object()
                .setTitle(getString(R.string.deleteFile))
                .setMessage(getString(R.string.deleteMessage).replace("!" , nameString))
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
        dialog.cancel();
        db.get().execSQL(" DELETE FROM " + app.main.NOTE_TABLE + " WHERE " + app.main.TABLE_ID + " = " + id);
        app.t("Note deleted");
        finish();
    }

    @Override
    public void onNeutral(String input) {
        dialog.cancel();
    }

    @Override
    public void Dismiss(String input) {
        dialog.cancel();
    }

    @Override
    protected void onResume() {
        if (onResume){
          prepareData();
        }
        super.onResume();


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ContentValues values = new ContentValues();
        values.put(app.main.TABLE_POSITION , scrollView.getScrollY());
        String[] args = {id +""};
        db.get().update(app.main.NOTE_TABLE , values , app.main.TABLE_ID +" =? ", args);
        finish();
    }
}