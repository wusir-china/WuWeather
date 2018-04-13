package com.goldensoft.goldenlibrary.recyclerview;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;


/**
 * @author Vondear
 * @date 2017/6/8
 * 官方的BUG
 * 解决 IndexOutOfBoundsException: Inconsistency detected. Invalid view holder adapter
 */

public class GdLinearLayoutManager extends LinearLayoutManager {
    public GdLinearLayoutManager(Context context) {
        super(context);
    }

    public GdLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public GdLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        try {
            super.onLayoutChildren(recycler, state);
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }
}

