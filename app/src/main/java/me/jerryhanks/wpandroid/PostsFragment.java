package me.jerryhanks.wpandroid;

import android.content.ComponentName;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsCallback;
import android.support.customtabs.CustomTabsClient;
import android.support.customtabs.CustomTabsIntent;
import android.support.customtabs.CustomTabsServiceConnection;
import android.support.customtabs.CustomTabsSession;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import me.jerryhanks.wpandroid.adapter.RecyclerAdapter;
import me.jerryhanks.wpandroid.data.model.PostHolder;
import me.jerryhanks.wpandroid.databinding.PostFragmentBinding;
import me.jerryhanks.wpandroidclient.client.WPMediaClient;
import me.jerryhanks.wpandroidclient.client.WPPostClient;
import me.jerryhanks.wpandroidclient.data.interfaces.MediaCallback;
import me.jerryhanks.wpandroidclient.data.interfaces.PostCallback;
import me.jerryhanks.wpandroidclient.data.model.Media;
import me.jerryhanks.wpandroidclient.data.model.Post;
import me.jerryhanks.wpandroidclient.data.model.WpErrorResponse;

/**
 * A placeholder fragment containing a simple view.
 */
public class PostsFragment extends Fragment {

    private static final String TAG = PostsFragment.class.getSimpleName();

    public static final String CUSTOM_TAB_PACKAGE_NAME = "com.android.chrome";  // Change when in stable

    private PostFragmentBinding mBinding;
    private CustomTabsClient mCustomTabsClient;
    private CustomTabsSession session;

    public PostsFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        mBinding = DataBindingUtil.inflate(inflater, R.layout.post_fragment, container, false);

        View rootView = mBinding.getRoot();

        CustomTabsServiceConnection connection = new CustomTabsServiceConnection() {
            @Override
            public void onCustomTabsServiceConnected(ComponentName name, CustomTabsClient client) {
                mCustomTabsClient = client;
                mCustomTabsClient.warmup(1000);

                session = mCustomTabsClient.newSession(new CustomTabsCallback() {
                });
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };

        CustomTabsClient.bindCustomTabsService(getContext(), CUSTOM_TAB_PACKAGE_NAME, connection);

        RecyclerView recyclerView = mBinding.postsRecyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));


        final RecyclerAdapter<Post, PostHolder> mAdapter = new RecyclerAdapter<Post, PostHolder>(Post.class, R.layout.item_post, PostHolder.class) {
            @Override
            protected void populateViewHolder(final PostHolder holder, final Post model, int position) {
                //Do  nothing for now.

                WPMediaClient.getMedia(model.getFeaturedMedia(), new MediaCallback.OnMediaCallback() {
                    @Override
                    public void onMedia(@NonNull Media media) {
                        Glide.with(getActivity())
                                .load(media.getSourceUrl())
                                .into(holder.getBinding().featuredImage);

                    }

                    @Override
                    public void onError(@NonNull WpErrorResponse errorResponse) {
                        Log.d(TAG, "Error: " + errorResponse);

                    }

                    @Override
                    public void onFailure(@NonNull Throwable t) {
                        Log.d(TAG, "Error:" + t.getMessage());

                    }
                });


                holder.getBinding().postTitle.setText(Html.fromHtml(model.getTitle().getRendered()), TextView.BufferType.SPANNABLE);
                holder.getBinding().postAuthor.setText("Auto Id:" + model.getAuthor());
                DateFormat format = new SimpleDateFormat("dd MMM yyyy");
                holder.getBinding().postDate.setText(format.format(model.getDate()));
//                holder.getBinding().postExcerpt.setText(model.getExcerpt().getRendered());

                holder.getBinding().getRoot().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //open the link in a chrome custom tab
                        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder(session);
                        builder.setToolbarColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
                        builder.setStartAnimations(getContext(), R.anim.slide_in_right, R.anim.slide_out_left);
                        builder.setExitAnimations(getContext(), R.anim.slide_in_left, R.anim.slide_out_right);

                        CustomTabsIntent customTabsIntent = builder.build();
                        customTabsIntent.launchUrl(getContext(), Uri.parse(model.getLink()));
                    }
                });


            }
        };


        mBinding.progressBar.setVisibility(View.VISIBLE);
        WPPostClient.getPosts(new PostCallback.OnPostCallback() {
            @Override
            public void onPosts(@NonNull List<Post> posts) {
                mBinding.progressBar.setVisibility(View.GONE);
                mAdapter.swapData(posts);
            }

            @Override
            public void onError(@NonNull WpErrorResponse errorResponse) {
                mBinding.progressBar.setVisibility(View.GONE);
                Log.d(TAG, errorResponse.toString());

            }

            @Override
            public void onFailure(@NonNull Throwable t) {
                mBinding.progressBar.setVisibility(View.GONE);
                Log.d(TAG, t.getMessage());
            }
        });

        recyclerView.setAdapter(mAdapter);

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
