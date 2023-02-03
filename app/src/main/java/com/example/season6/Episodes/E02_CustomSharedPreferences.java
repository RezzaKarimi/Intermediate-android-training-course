package com.example.season6.Episodes;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.season6.R;
import com.example.season6.app.spref;
import com.skydoves.colorpickerview.ColorEnvelope;
import com.skydoves.colorpickerview.ColorPickerView;
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener;

public class E02_CustomSharedPreferences extends AppCompatActivity implements View.OnClickListener {

    EditText userName , password , age;
    CheckBox rememberPass;
    TextView clearData;
    Button logIn;
    RelativeLayout relative;
    ColorPickerView colorPicker;
    int color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_e02__custom_shared_preferences);
        setTitle(getClass().getSimpleName());
        init();
        setData();

    }


    private void init() {

        userName     = findViewById(R.id.userName);
        password     = findViewById(R.id.password);
        age          = findViewById(R.id.age);
        rememberPass = findViewById(R.id.rememberPass);
        clearData    = findViewById(R.id.clearData);
        relative     = findViewById(R.id.relative);
        colorPicker  = findViewById(R.id.colorPickerView);
        logIn        = findViewById(R.id.logIn);

        logIn.setOnClickListener(this);
        clearData.setOnClickListener(this);
        colorPicker.setColorListener(new ColorEnvelopeListener() {
            @Override
            public void onColorSelected(ColorEnvelope envelope, boolean fromUser) {
                relative.setBackgroundColor(envelope.getColor());
                color = envelope.getColor();
            }
        });
    }

    private void setData() {
        if (spref.get(spref.tag.FORM).getBoolean(spref.Form.REMEMBER_PASS, spref.Form.REMEMBER_PASS_D)) {

            userName.setText(spref.get(spref.tag.FORM).getString(spref.Form.USERNAME, spref.Form.USERNAME_D));
            password.setText(spref.get(spref.tag.FORM).getString(spref.Form.PASSWORD, spref.Form.PASSWORD_D));
            age.setText(spref.get(spref.tag.FORM).getInt(spref.Form.AGE, spref.Form.AGE_D) + "");
            colorPicker.setInitialColor(spref.get(spref.tag.FORM).getInt(spref.Form.COLOR_PAGE , spref.Form.COLOR_D));
            rememberPass.setChecked(spref.get(spref.tag.FORM).getBoolean(spref.Form.REMEMBER_PASS, spref.Form.REMEMBER_PASS_D));

        }

    }

    @Override
    public void onClick(View v) {

        if (v == logIn) {
           spref.get(spref.tag.FORM).edit()
                   .putString(spref.Form.USERNAME       , userName.getText().toString())
                   .putString(spref.Form.PASSWORD       , password.getText().toString())
                   .putInt(spref.Form.AGE , Integer.parseInt(age.getText().toString()))
                   .putBoolean(spref.Form.REMEMBER_PASS , rememberPass.isChecked())
                   .putInt(spref.Form.COLOR_PAGE        , color)
                    .apply();
        }else {
            spref.get(spref.tag.FORM).edit().clear().apply();
        }
    }



}