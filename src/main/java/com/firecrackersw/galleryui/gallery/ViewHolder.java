package com.firecrackersw.galleryui.gallery;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder {
    public CardView view;
    public ViewHolder(CardView view) {
        super(view);
        this.view = view;
    }
}