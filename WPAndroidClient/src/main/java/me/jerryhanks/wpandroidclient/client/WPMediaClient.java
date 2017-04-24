package me.jerryhanks.wpandroidclient.client;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import me.jerryhanks.wpandroidclient.ServiceGenerator;
import me.jerryhanks.wpandroidclient.data.interfaces.MediaCallback;
import me.jerryhanks.wpandroidclient.data.model.Media;
import me.jerryhanks.wpandroidclient.data.model.WpErrorResponse;
import me.jerryhanks.wpandroidclient.service.MediaService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Jerry < @Po10cio >  on 10/04/2017 for WPAndroid.
 * Copyright (C) Pacent Technologies Ltd.
 */

public class WPMediaClient {

    private static final String TAG = WPMediaClient.class.getSimpleName();
    private static MediaService mService = ServiceGenerator.createService(MediaService.class);

    public static void getMedias(@NonNull final MediaCallback.OnMediaListCallback callback) {
        mService.getMedias().enqueue(new Callback<List<Media>>() {
            @Override
            public void onResponse(Call<List<Media>> call, Response<List<Media>> response) {
                if (response.isSuccessful()) {
                    callback.onMediaList(response.body());
                } else {
                    WpErrorResponse errorResponse = null;
                    try {
                        errorResponse = new Gson().fromJson(response.errorBody().string(), WpErrorResponse.class);
                        callback.onError(errorResponse);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<List<Media>> call, Throwable t) {
                Log.d(TAG, t.getMessage());
                callback.onFailure(t);

            }
        });


    }

    public static void getMedia(long id, @NonNull final MediaCallback.OnMediaCallback callback) {
        mService.getMedia(id, null, null)
                .enqueue(new Callback<Media>() {
                    @Override
                    public void onResponse(Call<Media> call, Response<Media> response) {
                        if (response.isSuccessful()) {
                            callback.onMedia(response.body());
                        } else {
                            WpErrorResponse errorResponse = null;
                            try {
                                errorResponse = new Gson().fromJson(response.errorBody().string(), WpErrorResponse.class);
                                callback.onError(errorResponse);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Media> call, Throwable t) {
                        Log.d(TAG, t.getMessage());
                        callback.onFailure(t);

                    }
                });
    }
}
