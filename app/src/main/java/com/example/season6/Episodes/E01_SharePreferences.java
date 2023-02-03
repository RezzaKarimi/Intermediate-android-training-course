package com.example.season6.Episodes;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.season6.R;
import com.example.season6.app.app;

public class E01_SharePreferences extends AppCompatActivity implements View.OnClickListener {

    EditText userName , password;
    TextView clearData;
    CheckBox rememberPass;
    Button   logIn;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_e01__shared_preferences);
        setTitle(getClass().getSimpleName());
        init();
    }

    private void init() {

        userName     = findViewById(R.id.userName);
        password     = findViewById(R.id.password);
        rememberPass = findViewById(R.id.rememberPass);
        logIn        = findViewById(R.id.logIn);
        clearData    = findViewById(R.id.clearData);

        logIn.setOnClickListener(this);
        clearData.setOnClickListener(this);

        sharedPreferences = getSharedPreferences(app.main.Tag , MODE_PRIVATE);

        if (sharedPreferences.getBoolean("rememberPass" , false)){
            userName.setText(sharedPreferences.getString("userName" , ""));
            password.setText(sharedPreferences.getString("password" , ""));
            rememberPass.setChecked(sharedPreferences.getBoolean("rememberPass" , false));
        }
    }

    @Override
    public void onClick(View v) {

        if (v == logIn) {
            sharedPreferences.edit().putString("userName", userName.getText().toString()).apply();
            sharedPreferences.edit().putString("password", password.getText().toString()).apply();
            sharedPreferences.edit().putBoolean("rememberPass", rememberPass.isChecked()).apply();
        }else {
            sharedPreferences.edit().clear().apply();
        }

    }
}