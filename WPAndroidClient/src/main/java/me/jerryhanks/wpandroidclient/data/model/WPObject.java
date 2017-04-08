package me.jerryhanks.wpandroidclient.data.model;

import com.google.common.base.MoreObjects;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Jerry < @Po10cio >  on 08/04/2017 for WPAndroid.
 * Copyright (C) Pacent Technologies Ltd.
 */

public class WPObject {
    private String rendered;

    @SerializedName("protected")
    private boolean protectedItem;

    private WPObject() {

    }

    public String getRendered() {
        return rendered;
    }

    public boolean isProtectedItem() {
        return protectedItem;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper("WPObject")
                .add("Rendered", rendered)
                .add("IsProtected", protectedItem)
                .toString();
    }
}
