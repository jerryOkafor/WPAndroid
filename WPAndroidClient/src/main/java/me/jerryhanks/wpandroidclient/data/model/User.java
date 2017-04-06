package me.jerryhanks.wpandroidclient.data.model;

import com.google.common.base.MoreObjects;

/**
 * Created by @Po10cio on 29/03/2017 for WPAndroid.
 */

public class User {

    @Override
    public String toString() {
        return MoreObjects.toStringHelper("User")
                .add("Name", "I am coming")
                .toString();
    }
}
