package com.example.daniel.mts;

import android.app.Application;
import android.content.Context;

/**
 * Created by Isaac on 10/23/16.
 */

public class MyApplication extends Application {

    private static Context context;

    public void onCreate() {
        super.onCreate();
        MyApplication.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return MyApplication.context;
    }
}
