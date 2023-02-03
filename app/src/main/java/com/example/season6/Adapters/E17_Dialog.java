package com.example.season6.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatEditText;

import com.example.season6.Objects.E17Dialog_object;
import com.example.season6.R;

public class E17_Dialog extends AlertDialog implements View.OnClickListener {

    View color,separator;

    TextView title, message, negativeText, positiveText, neutralText;

    ImageView titleIcon, negativeIcon, positiveIcon, neutralIcon;

    LinearLayout positive, negative, neutral;

    AppCompatEditText input;

    E17Dialog_object object;
    Context context;

    public E17_Dialog(Context context , E17Dialog_object object) {
        super(context);
        this.object  = object;
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_e17_customdialog);
        init();
        getValue();
    }

    private void init() {
        color        = findViewById(R.id.color);
        separator    = findViewById(R.id.separator);

        title        = findViewById(R.id.title);
        message      = findViewById(R.id.message);
        negativeText = findViewById(R.id.negativeText);
        positiveText = findViewById(R.id.positiveText);
        neutralText  = findViewById(R.id.neutralText);

        titleIcon    = findViewById(R.id.titleIcon);
        negativeIcon = findViewById(R.id.negativeIcon);
        positiveIcon = findViewById(R.id.positiveIcon);
        neutralIcon  = findViewById(R.id.neutralIcon);

        positive     = findViewById(R.id.positive);
        negative     = findViewById(R.id.negative);
        neutral      = findViewById(R.id.neutral);

        input        = findViewById(R.id.input);


    }

    private void getValue() {
        title.setText(object.getTitle());
        title.setTextColor(context.getResources().getColor(object.getTitleColor()));
        titleIcon.setColorFilter(context.getResources().getColor(object.getTitleColor()));
        message.setText(object.getMessage());
        titleIcon.setImageResource(object.getTitleIcon());

        if (object.getPositive_boolean()){
            positive.setVisibility(View.VISIBLE);
            positiveText.setText(object.getPositive());
            positiveIcon.setImageResource(object.getPositiveIcon());
            positive.setBackgroundColor(context.getResources().getColor(object.getPositiveColor()));
        }

        if (object.getNegative_boolean()){
            negative.setVisibility(View.VISIBLE);
            negativeText.setText(object.getNegative());
            negativeIcon.setImageResource(object.getNegativeIcon());
            negative.setBackgroundColor(context.getResources().getColor(object.getNegativeColor()));
        }

        if (object.getNeutral_boolean()){
            neutral.setVisibility(View.VISIBLE);
            neutralText.setText(object.getNeutral());
            neutralIcon.setImageResource(object.getNeutralIcon());
            neutral.setBackgroundColor(context.getResources().getColor(object.getNeutralColor()));
        }

        if (object.getInput_boolean()){
            input.setVisibility(View.VISIBLE);
        }

        positive.setOnClickListener(this);
        negative.setOnClickListener(this);
        neutral.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == positive){
            object.getListener().onPositive(input.getText().toString());
        }else if(v == negative){
            object.getListener().onNegative(input.getText().toString());

        }else if (v == neutral){
            object.getListener().onNeutral(input.getText().toString());

        }else if (v == null){
            object.getListener().Dismiss(input.getText().toString());
        }



    }
}
