package me.jerryhanks.wpandroidclient.util;

import me.jerryhanks.wpandroidclient.WPAndroid;

/**
 * Created by Jerry < @Po10cio >  on 08/04/2017 for WPAndroid.
 * Copyright (C) Pacent Technologies Ltd.
 */

public class WPAndroidUtil {

    public static String buidlMedisUrl(long mediaId) {
        return WPAndroid.getBaseUrl() + "media/" + mediaId;
    }
}
