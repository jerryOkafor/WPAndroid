package me.jerryhanks.wpandroidclient.service;

import android.support.annotation.NonNull;

import java.util.List;

import me.jerryhanks.wpandroidclient.data.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by @Po10cio on 29/03/2017 for WPAndroid.
 */

public interface UserService {

    /**
     * Lists all the users in the wp
     */
    @GET("users")
    Call<List<User>> listUsers();

    /**
     * Gets a single user with id {id}
     */
    @GET("users/{id}")
    Call<User> getuser(@Path("id") long id);

    /**
     * Creates a new User
     */
    @POST("users")
    Call<Void> createUser(@Body @NonNull User user);
}
