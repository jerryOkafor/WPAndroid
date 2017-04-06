package me.jerryhanks.wpandroidclient.interactor;

import android.support.annotation.NonNull;

import java.util.List;

import me.jerryhanks.wpandroidclient.ServiceGenerator;
import me.jerryhanks.wpandroidclient.data.interfaces.UserCallback;
import me.jerryhanks.wpandroidclient.data.model.User;
import me.jerryhanks.wpandroidclient.service.UserService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by @Po10cio on 29/03/2017 for WPAndroid.
 */

public class UserInteractor {

    private static UserService mService = ServiceGenerator.craeteService(UserService.class);

    public static void getUsers(@NonNull final UserCallback.OnUsersList callback) {
        mService.listUsers()
                .enqueue(new Callback<List<User>>() {
                    @Override
                    public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                        callback.onUsers(response.body());
                    }

                    @Override
                    public void onFailure(Call<List<User>> call, Throwable t) {

                        callback.onFailure(t);
                    }
                });

    }
}
