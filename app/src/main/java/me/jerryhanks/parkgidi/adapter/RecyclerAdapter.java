package me.jerryhanks.parkgidi.adapter;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jerry < @Po10cio >  on 07/04/2017 for WPAndroid.
 * Copyright (C) Pacent Technologies Ltd.
 */

public abstract class RecyclerAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    private static final String TAG = RecyclerAdapter.class.getSimpleName();
    private final Class<T> mModelClass;
    private List<T> mItems;
    private final int mModelLayout;
    private final Class<VH> mViewHolder;

    public RecyclerAdapter(Class<T> modelClass,
                           @LayoutRes int modelLayout,
                           Class<VH> viewHolder) {
        mItems = new ArrayList<>();
        mModelClass = modelClass;
        mModelLayout = modelLayout;
        mViewHolder = viewHolder;

    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        try {
            Constructor<VH> constructor = mViewHolder.getConstructor(View.class);
            return constructor.newInstance(view);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        T model = getItem(position);
        populateViewHolder(holder, model, position);


    }


    private T getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }


    protected abstract void populateViewHolder(VH holder, T model, int position);

    public void swapData(List<T> newData) {
        mItems = newData;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return mModelLayout;
    }
}
