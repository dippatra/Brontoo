package com.brontoo.interfaces;

import android.view.View;

import com.brontoo.models.Movie;

/**
 * Created by Sang.24 on 3/19/2018.
 */

public interface GridListener {
    public void onItemListener(Movie movie);
    public void onMoreListener(Movie movie, View view);
}
