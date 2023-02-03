package com.example.season6.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;

import com.example.season6.R;

public class spref {

    private static SharedPreferences sp;

    public static SharedPreferences get (String tag){
        if (sp !=null)
            return sp;

         sp = Application.getContext().getSharedPreferences(tag , Context.MODE_PRIVATE);
         return sp;
    }


    public static class tag{
        public static final String FORM     = "FORM";
        public static final String SETTING  = "SETTING";
    }

    public static class Form {

        public static final String USERNAME                 = "userName";
        public static final String USERNAME_D               = "";
        public static final String PASSWORD                 = "password";
        public static final String PASSWORD_D               = "";
        public static final String REMEMBER_PASS            = "rememberPass";
        public static final Boolean REMEMBER_PASS_D         = true;
        public static final String AGE                      = "AGE";
        public static final int AGE_D                       = 18;
        public static final String COLOR_PAGE               = "COLOR_PAGE";
        public static final int COLOR_D                     = Application.getContext().getResources().getColor(R.color.colorAccent);

        public static final String ORDER                    = "SORT";
        public static final String ORDER_VALUE              = "ASC";

    }

    public static class SETTING{
        public static final String  LAST_POSITION            = "LAST_POSITION";
        public static final String  FONT                     = "FONT";
        public static final String  FONT_SIZE                = "FONT_SIZE";
        public static final String  FONT_SPACING             = "FONT_SPACING";
        public static final String  CUSTOM_COLOR             = "CUSTOM_COLOR";
        public static final String  COLOR                    = "COLOR";
        public static final int     COLOR_DEFAULT            = Color.BLACK;
        public static final String  DARK_MODE                = "DARK_MODE";
        public static final String  AUTO_BACK                = "AUTO_BACK";
    }

}
