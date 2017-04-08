package me.jerryhanks.wpandroidclient.client;

import android.support.annotation.NonNull;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import me.jerryhanks.wpandroidclient.ServiceGenerator;
import me.jerryhanks.wpandroidclient.data.interfaces.PostCallback;
import me.jerryhanks.wpandroidclient.data.model.Post;
import me.jerryhanks.wpandroidclient.data.model.WpErrorResponse;
import me.jerryhanks.wpandroidclient.service.PostService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by @Po10cio on 06/04/2017 for WPAndroid.
 */

public class WPPostClient {
    private static PostService mService = ServiceGenerator.createService(PostService.class);

    public static void getPosts(@NonNull final PostCallback.OnPostCallback callback) {
        mService.getPosts().enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.isSuccessful()) {
                    callback.onPosts(response.body());
                } else {
                    try {
                        WpErrorResponse errorResponse = new Gson().fromJson(response.errorBody().string(), WpErrorResponse.class);
                        callback.onError(errorResponse);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                callback.onFailure(t);

            }
        });

    }
}
