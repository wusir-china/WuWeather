package com.goldensoft.goldenlibrary.utils;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by Administrator on 2018/3/13.
 */

public class SizeTransferUtil {
    public static int pixelToDp(Context context, int pixel) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return pixel < 0 ? pixel : Math.round(pixel / displayMetrics.density);
    }
    public static int dpToPixel(Context context, int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return dp < 0 ? dp : Math.round(dp * displayMetrics.density);
    }
}
