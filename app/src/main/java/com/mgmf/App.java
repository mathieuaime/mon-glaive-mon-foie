package com.mgmf;

import android.app.Application;
import android.content.Context;

/**
 * Created by Mathieu Aimé on 24/01/18.
 */

public class App extends Application {
    private static Context context;

    public void onCreate() {
        super.onCreate();
        App.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return App.context;
    }
}
