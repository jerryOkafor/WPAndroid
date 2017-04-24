package me.jerryhanks.wpandroidclient.service;

import android.support.annotation.Nullable;

import java.util.List;

import me.jerryhanks.wpandroidclient.data.model.Media;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Jerry < @Po10cio >  on 10/04/2017 for WPAndroid.
 * Copyright (C) Pacent Technologies Ltd.
 */

public interface MediaService {

    /**
     * List all the media in the app
     */
    @GET("media")
    Call<List<Media>> getMedias();


    /**
     * Get a single Media (Media Item) using the media id
     */
    @GET("media/{id}")
    Call<Media> getMedia(@Path("id") long id, @Query("context") @Nullable String context, @Query("password") @Nullable String password);
}
