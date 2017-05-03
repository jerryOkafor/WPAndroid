package me.jerryhanks.parkgidi.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import de.hdodenhof.circleimageview.CircleImageView;
import me.jerryhanks.parkgidi.R;

/**
 * Created by Potencio on 5/3/2017. @ 11:05 AM
 * For ParkGidi
 */

public class ProgressCircularImageView extends RelativeLayout {
    private static final String KEY_IMAGE_SOURCE = "image_src";
    private String mImageSource;
    private CircleImageView mCircleImageView;
    private ProgressBar mLoadingBar;

    public ProgressCircularImageView(Context context) {
        this(context, null);
    }

    public ProgressCircularImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressCircularImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ProgressCircularImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ProgressCircleImageView, 0, 0);
            mImageSource = a.getString(R.styleable.ProgressCircleImageView_imageSource);
            a.recycle();
        }
        init();

    }


    private void init() {
        LayoutInflater.from(getContext())
                .inflate(R.layout.progress_circle_image_view, this, true);

        mCircleImageView = (CircleImageView) getChildAt(0);
        mLoadingBar = (ProgressBar) getChildAt(1);

        loadImage();

    }

    private void loadImage() {
        //Hide the Progress bar for now
        mLoadingBar.setVisibility(VISIBLE);

        Glide.with(getContext())
                .load("https://firebasestorage.googleapis.com/v0/b/parkgidi-f7b9f.appspot.com/o/avatar%2Fphoto.jpg?alt=media&token=36bcfcc9-27b5-4188-9e09-069792fcf35b")
                .dontAnimate()
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        mLoadingBar.setVisibility(GONE);
                        Log.e("Glide: ", e.getMessage());
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        mLoadingBar.setVisibility(GONE);
                        Log.i("Glide: ", "Image loaded!");
                        return false;
                    }
                })
                .placeholder(R.drawable.avater)
                .into(mCircleImageView);
    }


    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("superState", super.onSaveInstanceState());
        bundle.putString(KEY_IMAGE_SOURCE, mImageSource);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) // implicit null check
        {
            Bundle bundle = (Bundle) state;
            mImageSource = bundle.getString(KEY_IMAGE_SOURCE);
            state = bundle.getParcelable("superState");
        }
        super.onRestoreInstanceState(state);
        loadImage();
    }

    public void setmImageSource(String imageSource) {
        mImageSource = imageSource;
        loadImage();
    }


}
