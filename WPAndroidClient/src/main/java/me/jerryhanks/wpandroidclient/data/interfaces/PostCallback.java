package me.jerryhanks.wpandroidclient.data.interfaces;

import android.support.annotation.NonNull;

import java.util.List;

import me.jerryhanks.wpandroidclient.data.model.Post;
import me.jerryhanks.wpandroidclient.data.model.WpErrorResponse;

/**
 * Created by @Po10cio on 06/04/2017 for WPAndroid.
 */

public class PostCallback {
    public interface OnPostCallback {

        void onPosts(@NonNull List<Post> posts);

        void onError(@NonNull WpErrorResponse errorResponse);

        void onFailure(@NonNull Throwable t);
    }
}
