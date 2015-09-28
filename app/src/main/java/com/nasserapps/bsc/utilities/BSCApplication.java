package com.nasserapps.bsc.utilities;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by Nasser on 1/10/15.
 */
public class BSCApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "BSQ2mqiK5nS6wI3TnWBNm6Mr475ynPYJKQjIX1rm", "zROL2ISZsbchJYDwwFzJik2QS72s0ovVV8Onl1fn");
    }
}
