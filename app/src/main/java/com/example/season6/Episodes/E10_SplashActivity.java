package com.example.season6.Episodes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AnimationUtils;

import com.example.season6.R;

public class E10_SplashActivity extends AppCompatActivity {

    AppCompatImageView icon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_e10__splash);

        icon = findViewById(R.id.icon);
        icon.startAnimation(AnimationUtils.loadAnimation(this , android.R.anim.slide_in_left));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(E10_SplashActivity.this , E09_FinalActivity.class));
                icon.startAnimation(AnimationUtils.loadAnimation(E10_SplashActivity.this , android.R.anim.fade_out));
            }
        },2000);
    }
}