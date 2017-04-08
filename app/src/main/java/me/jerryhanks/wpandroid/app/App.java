package me.jerryhanks.wpandroid.app;

import android.app.Application;

import me.jerryhanks.wpandroidclient.WPAndroid;

/**
 * Created by @Po10cio on 29/03/2017 for WPAndroid.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        WPAndroid.init("http://jerryhanks.me/");
    }
}
