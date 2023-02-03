package com.example.season6.Episodes;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.appcompat.widget.AppCompatSpinner;

import com.example.season6.Adapters.E17_Dialog;
import com.example.season6.Objects.E17Dialog_object;
import com.example.season6.R;
import com.example.season6.app.app;
import com.example.season6.app.dbConnector;
import com.example.season6.app.spref;
import com.example.season6.interfaces.E17DialogListener;
import com.skydoves.colorpickerview.ColorEnvelope;
import com.skydoves.colorpickerview.ColorPickerView;
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener;

public class E04_SQLiteSetting extends AppCompatActivity implements
                                                                    View.OnClickListener,
                                                                    AdapterView.OnItemSelectedListener,
                                                                    SeekBar.OnSeekBarChangeListener,
                                                                    CompoundButton.OnCheckedChangeListener,
                                                                    E17DialogListener {

    AppCompatImageView back;
    AppCompatSpinner font_spinner;
    AppCompatSeekBar fontSize , fontSpacing;
    TextView sampleText , clearData;

    ColorPickerView colorPickerView;

    AppCompatCheckBox customColor , darkMode , autoBack;

    LinearLayout parent;

    dbConnector db;
    E17_Dialog dialog;

    public static final int DEFAULT = 0;
    public static final int DEJA = 1;
    public static final int SEGOE = 2;
    public static final int TIMES = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_e04_sqlite_setting);

        initViews();
        listeners();
        restoredData();

    }



    private void initViews() {
        back             = findViewById(R.id.back);
        font_spinner     = findViewById(R.id.font_spinner);
        fontSize         = findViewById(R.id.fontSize_seekBar);
        fontSpacing      = findViewById(R.id.fontSpacing_seekBar);
        sampleText       = findViewById(R.id.sampleText);
        clearData        = findViewById(R.id.clearData);
        colorPickerView  = findViewById(R.id.colorPickerView);
        customColor      = findViewById(R.id.customColor_CheckBox);
        autoBack         = findViewById(R.id.autoBack_CheckBox);
        darkMode         = findViewById(R.id.darkMode_CheckBox);
        parent           = findViewById(R.id.parent);
        db               = new dbConnector(this , null , null , 1);
    }


    private void listeners() {
        back        .setOnClickListener(this);
        font_spinner.setOnItemSelectedListener(this);
        fontSize    .setOnSeekBarChangeListener(this);
        fontSpacing .setOnSeekBarChangeListener(this);
        customColor .setOnCheckedChangeListener(this);
        autoBack    .setOnCheckedChangeListener(this);
        darkMode    .setOnCheckedChangeListener(this);
        clearData   .setOnClickListener(this);

        colorPickerView.setColorListener(new ColorEnvelopeListener() {
            @Override
            public void onColorSelected(ColorEnvelope envelope, boolean fromUser) {

                if (customColor.isChecked()) {
                    spref.get(spref.tag.SETTING).edit().putInt(spref.SETTING.COLOR, envelope.getColor()).apply();
                    sampleText.setTextColor(envelope.getColor());
                }
            }
        });

    }

    private void restoredData(){

        int fontValue = spref.get(spref.tag.SETTING).getInt(spref.SETTING.FONT , 0);
        setFont(fontValue , this , sampleText);
        font_spinner.setSelection(fontValue);

        int size = spref.get(spref.tag.SETTING).getInt(spref.SETTING.FONT_SIZE , 0);
        fontSize.setProgress(size);
        sampleText.setTextSize(size + 16f);

        int spacing = spref.get(spref.tag.SETTING).getInt(spref.SETTING.FONT_SPACING , 0);
        fontSpacing.setProgress(spacing);
        sampleText.setLineSpacing(spacing , 1);

        boolean check = spref.get(spref.tag.SETTING).getBoolean(spref.SETTING.CUSTOM_COLOR , false);
        customColor.setChecked(check);

        int color = spref.get(spref.tag.SETTING).getInt(spref.SETTING.COLOR , spref.SETTING.COLOR_DEFAULT);
        sampleText.setTextColor(spref.SETTING.COLOR_DEFAULT);
        if (customColor.isChecked()){
            colorPickerView.setInitialColor(color);
            sampleText.setTextColor(color);
        }

        if (spref.get(spref.tag.SETTING).getBoolean(spref.SETTING.DARK_MODE , false)){
            parent.setBackgroundColor(Color.BLACK);
            darkMode.setChecked(true);
        }else {
            parent.setBackgroundColor(getResources().getColor(R.color.white));
            darkMode.setChecked(false);
        }
        if (spref.get(spref.tag.SETTING).getBoolean(spref.SETTING.AUTO_BACK , false)){
            autoBack.setChecked(true);
        }else autoBack.setChecked(false);
    }



    @Override
    public void onClick(View v) {

        if (v == back){

            finish();
            return;

        }else if ( v == clearData){
            delete();

        }

    }

    private void delete() {
            E17Dialog_object object = new E17Dialog_object()
                    .setTitle(getString(R.string.deleteAll))
                    .setMessage(getString(R.string.delete_All_Message))
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
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        spref.get(spref.tag.SETTING).edit().putInt(spref.SETTING.FONT , position).apply();
        setFont(position , this , sampleText);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        if (seekBar == fontSize) {
            sampleText.setTextSize(progress + 16f);
        }else if (seekBar == fontSpacing){
            sampleText.setLineSpacing(progress , 1);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

        if (seekBar == fontSize){
            spref.get(spref.tag.SETTING).edit().putInt(spref.SETTING.FONT_SIZE , seekBar.getProgress()).apply();
        }else if (seekBar == fontSpacing){
            spref.get(spref.tag.SETTING).edit().putInt(spref.SETTING.FONT_SPACING , seekBar.getProgress()).apply();
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        if (buttonView == customColor){
            if (buttonView.isChecked()){
                spref.get(spref.tag.SETTING).edit().putBoolean(spref.SETTING.CUSTOM_COLOR , true).apply();
                colorPickerView.setVisibility(View.VISIBLE);
                sampleText.setTextColor(spref.get(spref.tag.SETTING).getInt(spref.SETTING.COLOR , spref.SETTING.COLOR_DEFAULT));
                colorPickerView.setInitialColor(spref.get(spref.tag.SETTING).getInt(spref.SETTING.COLOR , spref.SETTING.COLOR_DEFAULT));
            }else{
                spref.get(spref.tag.SETTING).edit().putBoolean(spref.SETTING.CUSTOM_COLOR , false).apply();
                colorPickerView.setVisibility(View.GONE);
                sampleText.setTextColor(spref.SETTING.COLOR_DEFAULT);
            }
        }else if (buttonView == darkMode){
            if (buttonView.isChecked()){
                spref.get(spref.tag.SETTING).edit().putBoolean(spref.SETTING.DARK_MODE , true).apply();
                parent.setBackgroundColor(Color.BLACK);
            }else {
                spref.get(spref.tag.SETTING).edit().putBoolean(spref.SETTING.DARK_MODE , false).apply();
                parent.setBackgroundColor(getResources().getColor(R.color.white));
            }
        }else if (buttonView == autoBack){
            if (buttonView.isChecked()){
                spref.get(spref.tag.SETTING).edit().putBoolean(spref.SETTING.AUTO_BACK , true).apply();
            }else {
                spref.get(spref.tag.SETTING).edit().putBoolean(spref.SETTING.AUTO_BACK , false).apply();
            }
        }
    }


    public static void setFont(int font , Activity activity , TextView textView){

        Typeface typeface;
        switch (font){

            case DEFAULT:{
                typeface = Typeface.DEFAULT;
                break;
            }
            case DEJA:{
                typeface = Typeface.createFromAsset(activity.getAssets() , "fonts/deja.ttf");
                break;
            }
            case SEGOE:{
                typeface = Typeface.createFromAsset(activity.getAssets() , "fonts/segoe.ttf");
                break;
            }
            case TIMES:{
                typeface = Typeface.createFromAsset(activity.getAssets() , "fonts/times.ttf");
                break;
            }
            default: typeface = Typeface.DEFAULT;
            break;
        }

        textView.setTypeface(typeface);

    }


    @Override
    public void onPositive(String input) {
        dialog.cancel();
    }

    @Override
    public void onNegative(String input) {
        dialog.cancel();
        db.get().execSQL(" DELETE FROM " + app.main.NOTE_TABLE );
        app.t("its empty");
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
}
