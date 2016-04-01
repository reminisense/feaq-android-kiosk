package com.reminisense.featherq.kiosk;

import android.app.Application;
import android.content.Context;

/**
 * Singleton to access context anywhere in the app.
 * Created by Nigs on 2016-04-01.
 */
public class FeatherQKiosk extends Application {
    private static FeatherQKiosk instance;

    public static FeatherQKiosk getInstance() {
        return instance;
    }

    public static Context getContext() {
        // might return the activity context? not sure.
        // return instance.getApplicationContext();
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
