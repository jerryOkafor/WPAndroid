package me.jerryhanks.parkgidi.home;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsClient;
import android.support.customtabs.CustomTabsSession;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.jerryhanks.parkgidi.R;
import me.jerryhanks.parkgidi.databinding.PostFragmentBinding;

/**
 * A placeholder fragment containing a simple view.
 */
public class HomeFragment extends Fragment {

    private static final String TAG = HomeFragment.class.getSimpleName();

    public static final String CUSTOM_TAB_PACKAGE_NAME = "com.android.chrome";  // Change when in stable

    private PostFragmentBinding mBinding;
    private CustomTabsClient mCustomTabsClient;
    private CustomTabsSession session;

    public HomeFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        mBinding = DataBindingUtil.inflate(inflater, R.layout.post_fragment, container, false);

        //set the retain instance state so that we can save some stateful data
        setRetainInstance(true);

        View rootView = mBinding.getRoot();

        return rootView;

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
