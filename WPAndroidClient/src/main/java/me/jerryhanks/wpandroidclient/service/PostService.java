package me.jerryhanks.wpandroidclient.service;

import java.util.List;

import me.jerryhanks.wpandroidclient.data.model.Post;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by @Po10cio on 06/04/2017 for WPAndroid.
 */

public interface PostService {
    /**
     * List all the posts in the WP App
     */
    @GET("posts")
    Call<List<Post>> getPosts();
}
