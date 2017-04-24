package me.jerryhanks.wpandroidclient.data.interfaces;

import android.support.annotation.NonNull;

import java.util.List;

import me.jerryhanks.wpandroidclient.data.model.Media;
import me.jerryhanks.wpandroidclient.data.model.WpErrorResponse;

/**
 * Created by Jerry < @Po10cio >  on 10/04/2017 for WPAndroid.
 * Copyright (C) Pacent Technologies Ltd.
 */

public class MediaCallback {
    public interface OnMediaListCallback {
        void onMediaList(@NonNull List<Media> medias);

        void onError(@NonNull WpErrorResponse errorResponse);

        void onFailure(@NonNull Throwable t);

    }

    public interface OnMediaCallback {
        void onMedia(@NonNull Media media);

        void onError(@NonNull WpErrorResponse errorResponse);

        void onFailure(@NonNull Throwable t);
    }
}
