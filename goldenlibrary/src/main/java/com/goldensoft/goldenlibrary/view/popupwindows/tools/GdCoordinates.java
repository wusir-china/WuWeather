package com.goldensoft.goldenlibrary.view.popupwindows.tools;

import android.view.View;

/**
 * Created by golden on 2018/4/3.
 */

public class GdCoordinates {
    int left;
    int top;
    int right;
    int bottom;

    public GdCoordinates(View view){
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        left = location[0];
        right = left + view.getWidth();
        top = location[1];
        bottom = top + view.getHeight();
    }
}
