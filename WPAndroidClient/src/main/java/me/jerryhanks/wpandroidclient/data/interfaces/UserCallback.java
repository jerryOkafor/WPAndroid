package me.jerryhanks.wpandroidclient.data.interfaces;

import java.util.List;

import me.jerryhanks.wpandroidclient.data.model.User;

/**
 * Created by @Po10cio on 29/03/2017 for WPAndroid.
 */

public class UserCallback {
    public interface OnUsersList {
        void onUsers(List<User> users);

        void onFailure(Throwable t);
    }
}
