package com.example.dummytestapprichit.utils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public abstract class GenericAdapter<T> extends RecyclerView.Adapter<GenericAdapter.ViewHolder> {

    private List<T> mFilterList = Collections.emptyList();
    private List<T> mList = Collections.emptyList();
    private Context mContext;
    private OnViewHolderClick mOnViewHolderClickListener;
    private OnBottomReachedListener mOnBottomReachedListener;


    public GenericAdapter(Context context) {
        mContext = context;
    }

    protected abstract View createView(Context context, ViewGroup viewGroup, int viewType);

    protected abstract void bindView(T item, ViewHolder viewHolder);

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        return new ViewHolder(createView(mContext, viewGroup, viewType), mOnViewHolderClickListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        bindView(getItem(position), viewHolder);
        if (mOnBottomReachedListener != null) {
            if (position == getList().size() - 1) {
                mOnBottomReachedListener.onBottomReached(position);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mFilterList.size();
    }

    public T getItem(int index) {
        return ((mFilterList != null && index < mFilterList.size()) ? mFilterList.get(index) : null);
    }

    public Context getContext() {
        return mContext;
    }

    public List<T> getList() {
        return mList;
    }

    public void setList(List<T> list) {
        mList = list;
        mFilterList = list;
    }

    public List<T> getFilterList() {
        return mFilterList;
    }

    public void setFilterList(List<T> list) {
        mFilterList = list;
    }

    public void setClickListener(OnViewHolderClick listener) {
        mOnViewHolderClickListener = listener;
    }

    public void setOnBottomReachedListener(OnBottomReachedListener onBottomReachedListener) {
        mOnBottomReachedListener = onBottomReachedListener;
    }

    public void remove(int position) {
        try {
            mFilterList.remove(position);
            notifyItemRemoved(position);
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public interface OnViewHolderClick {
        void onClick(View view, int position);
    }

    public interface OnBottomReachedListener {
        void onBottomReached(int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Map<Integer, View> mMapView;
        private OnViewHolderClick mListener;

        public ViewHolder(View view, OnViewHolderClick listener) {
            super(view);
            mMapView = new HashMap<>();
            mMapView.put(0, view);
            mListener = listener;

            if (mListener != null)
                view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mListener != null)
                mListener.onClick(view, getAdapterPosition());
        }

        public void initViewList(int[] idList) {
            for (int id : idList)
                initViewById(id);
        }

        public void initViewById(int id) {
            View view = (getView() != null ? getView().findViewById(id) : null);

            if (view != null)
                mMapView.put(id, view);
        }

        public View getView() {
            return getView(0);
        }

        public View getView(int id) {
            if (mMapView.containsKey(id))
                return mMapView.get(id);
            else
                initViewById(id);

            return mMapView.get(id);
        }
    }
}