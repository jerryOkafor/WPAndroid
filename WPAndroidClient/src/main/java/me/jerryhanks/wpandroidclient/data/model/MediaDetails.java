package me.jerryhanks.wpandroidclient.data.model;

import com.google.common.base.MoreObjects;

/**
 * Created by Jerry < @Po10cio >  on 10/04/2017 for WPAndroid.
 * Copyright (C) Pacent Technologies Ltd.
 */

public class MediaDetails {
    private int width;
    private int height;
    private String file;

    private MediaDetails() {

    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getFile() {
        return file;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper("Media Details")
                .add("Width", width)
                .add("Height", height)
                .add("File", file)
                .toString();
    }
}
