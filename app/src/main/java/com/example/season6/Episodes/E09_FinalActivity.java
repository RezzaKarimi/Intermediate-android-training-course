package com.example.season6.Episodes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.example.season6.Adapters.E09_Adapter;
import com.example.season6.R;

public class E09_FinalActivity extends AppCompatActivity {

    String[] finalActivity;
    RecyclerView recyclerView;
    E09_Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_e09__final);
        setTitle(this.getClass().getSimpleName());

        init();
    }

    private void init() {
        recyclerView  = findViewById(R.id.recyclerView);
        finalActivity = getResources().getStringArray(R.array.finalActivity);
        adapter       = new E09_Adapter(this , finalActivity);

        recyclerView.setLayoutAnimation(new LayoutAnimationController(AnimationUtils.loadAnimation(this , android.R.anim.slide_in_left)));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}