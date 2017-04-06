package me.jerryhanks.wpandroidclient;


import android.support.annotation.NonNull;

/**
 * Created by @Po10cio on 29/03/2017 for WPAndroid.
 */

public class WPAndroid {
    private static String mHost;

    public static void init(@NonNull String host) {
        mHost = host;

    }

    public static String getBaseUrl() {
        return mHost + "wp-json/wp/v2/";
    }
}
