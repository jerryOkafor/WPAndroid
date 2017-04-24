package me.jerryhanks.wpandroidclient.data.model;

import com.google.common.base.MoreObjects;

/**
 * Created by Jerry < @Po10cio >  on 10/04/2017 for WPAndroid.
 * Copyright (C) Pacent Technologies Ltd.
 */

public class MediaSize {
    private String file;
    private int width;
    private int height;
    private String sourceUrl;

    private MediaSize() {

    }

    public String getFile() {
        return file;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper("Media Size")
                .add("File", file)
                .add("Width", width)
                .add("Height", height)
                .add("Source Url", sourceUrl)
                .toString();
    }
}
