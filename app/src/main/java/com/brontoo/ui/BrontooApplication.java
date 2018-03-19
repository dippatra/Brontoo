package com.brontoo.ui;

import android.app.Application;

import com.brontoo.controllers.TopExceptionHandler;



public class BrontooApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Thread.setDefaultUncaughtExceptionHandler(new TopExceptionHandler(getApplicationContext()));
    }
}
