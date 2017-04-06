package me.jerryhanks.wpandroid;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import me.jerryhanks.wpandroidclient.data.interfaces.UserCallback;
import me.jerryhanks.wpandroidclient.data.model.User;
import me.jerryhanks.wpandroidclient.interactor.UserInteractor;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private static final String TAG = MainActivityFragment.class.getSimpleName();

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        UserInteractor.getUsers(new UserCallback.OnUsersList() {
            @Override
            public void onUsers(List<User> users) {

                for (User user : users) {
                    Log.d(TAG, "User" + user.toString());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d(TAG, "Error: " + t.getMessage());

            }
        });
        return inflater.inflate(R.layout.fragment_main, container, false);


    }
}
