package com.example.season6.Objects;

import android.graphics.Color;

import androidx.annotation.ColorRes;

import com.example.season6.R;
import com.example.season6.app.app;
import com.example.season6.interfaces.E17DialogListener;
import com.example.season6.R;
import com.example.season6.app.app;
import com.example.season6.interfaces.E17DialogListener;

public class E17Dialog_object {





    String  title;
    String  message;
    String  positive;
    String  negative;
    String  neutral;


    Boolean positive_boolean;
    Boolean negative_boolean;
    Boolean neutral_boolean;

    int titleIcon    = R.drawable.ic_baseline_arrow_back_24;
    int positiveIcon = R.drawable.ic_baseline_arrow_back_24;
    int negativeIcon = R.drawable.ic_baseline_arrow_back_24;
    int neutralIcon  = R.drawable.ic_baseline_arrow_back_24;

    @ColorRes int color         = R.color.colorPrimary;
    @ColorRes int titleColor    = R.color.colorPrimary;
    @ColorRes int positiveColor = R.color.colorPrimary;
    @ColorRes int negativeColor = R.color.colorPrimary;
    @ColorRes int neutralColor  = R.color.colorPrimary;


    Boolean dismiss_boolean = true ;
    Boolean input_boolean   = false;

    E17DialogListener listener;

    public E17Dialog_object setTitle(String title){
        this.title = title;
        return this;
    }
    public String getTitle(){return this.title;}

    public E17Dialog_object setMessage(String message){
        this.message = message;
        return this;
    }
    public String getMessage(){return this.message;}

    public E17Dialog_object setPositive(String positive){
        this.positive = positive;
        return this;
    }
    public String getPositive(){return this.positive;}


    public E17Dialog_object setNegative(String negative){
        this.negative = negative;
        return this;
    }
    public String getNegative(){return this.negative;}


    public E17Dialog_object setNeutral(String neutral){
        this.neutral = neutral;
        return this;
    }
    public String getNeutral(){return this.neutral;}


    public E17Dialog_object setColor(@ColorRes int color){
           this.color = color;

        return this;
    }
    public int getColor(){return this.color;}


    public E17Dialog_object setPositive_boolean(boolean positive_boolean){
        this.positive_boolean = positive_boolean;
        return this;
    }
    public Boolean getPositive_boolean(){return this.positive_boolean;}

    public E17Dialog_object setNegative_boolean(boolean negative_boolean){
        this.negative_boolean = negative_boolean;
        return this;
    }
    public Boolean getNegative_boolean(){return this.negative_boolean;}


    public E17Dialog_object setNeutral_boolean(boolean neutral_boolean){
        this.neutral_boolean = neutral_boolean;
        return this;
    }
    public Boolean getNeutral_boolean(){return this.neutral_boolean;}

    public E17Dialog_object setDismiss_boolean(boolean dismiss_boolean){
        this.dismiss_boolean = dismiss_boolean;
        return this;
    }
    public Boolean getDismiss_boolean(){return this.dismiss_boolean;}


    public E17Dialog_object setInput_boolean(boolean input_boolean){
        this.input_boolean = input_boolean;
        return this;
    }
    public Boolean getInput_boolean(){return this.input_boolean;}

    public E17Dialog_object setTitleIcon(int titleIcon){
        this.titleIcon = titleIcon;
        return this;
    }
    public int getTitleIcon(){return this.titleIcon;}


    public E17Dialog_object setPositiveIcon(int positiveIcon){
        this.positiveIcon = positiveIcon;
        return this;
    }
    public int getPositiveIcon(){return this.positiveIcon;}


    public E17Dialog_object setNegativeIcon(int negativeIcon){
        this.negativeIcon = negativeIcon;
        return this;
    }
    public int getNegativeIcon(){return this.negativeIcon;}


    public E17Dialog_object setNeutralIcon(int neutralIcon){
        this.neutralIcon = neutralIcon;
        return this;
    }
    public int getNeutralIcon(){return this.neutralIcon;}


    public E17Dialog_object setTitleColor(@ColorRes int titleColor){
        this.titleColor = titleColor;
        return this;
    }
    public int getTitleColor(){return this.titleColor;}


    public E17Dialog_object setPositiveColor(@ColorRes int positiveColor){
        this.positiveColor = positiveColor;
        return this;
    }
    public int getPositiveColor(){return this.positiveColor;}


    public E17Dialog_object setNegativeColor(@ColorRes int negativeColor){
        this.negativeColor = negativeColor;
        return this;
    }
    public int getNegativeColor(){return this.negativeColor;}


    public E17Dialog_object setNeutralColor(@ColorRes int neutralColor){
        this.neutralColor = neutralColor;
        return this;
    }
    public int getNeutralColor(){return this.neutralColor;}

    public E17Dialog_object setListener(E17DialogListener listener){
        this.listener = listener;
        return this;
    }
    public E17DialogListener getListener(){return this.listener;}

}
