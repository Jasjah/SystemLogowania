package com.parse.jasjah.systemlogowania;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class SystemApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "KEY for APP", "KEY");

    }




}
