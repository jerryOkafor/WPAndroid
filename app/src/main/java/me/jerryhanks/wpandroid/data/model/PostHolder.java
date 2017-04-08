package me.jerryhanks.wpandroid.data.model;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import me.jerryhanks.wpandroid.databinding.ItemPostBinding;

/**
 * Created by Jerry < @Po10cio >  on 07/04/2017 for WPAndroid.
 * Copyright (C) Pacent Technologies Ltd.
 */

public class PostHolder extends RecyclerView.ViewHolder {
    private final ItemPostBinding mBinding;

    public PostHolder(View itemView) {
        super(itemView);
        mBinding = DataBindingUtil.bind(itemView);
    }

    public ItemPostBinding getBinding() {
        return mBinding;
    }
}
