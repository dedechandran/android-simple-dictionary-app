package com.example.genjeh.mydictionary.CustomListener;

import android.view.View;

public class OnCustomClickListener implements View.OnClickListener {
    private int position;
    private OnItemClickListener onItemClickListener;

    public OnCustomClickListener(int position, OnItemClickListener onItemClickListener) {
        this.position = position;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public void onClick(View v) {
        onItemClickListener.onItemClicked(v,position);
    }

    public interface OnItemClickListener{
        void onItemClicked(View v,int position);
    }
}
