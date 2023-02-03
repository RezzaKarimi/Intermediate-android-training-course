package com.example.season6.app;

import android.content.Context;

import pl.com.salsoft.sqlitestudioremote.SQLiteStudioService;

public class Application extends android.app.Application {

    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        SQLiteStudioService.instance().start(this);
        context = this;
    }

    public static Context getContext(){
        return context;
    }

    @Override
    public void onTerminate() {
        SQLiteStudioService.instance().stop();
        super.onTerminate();
    }
}
