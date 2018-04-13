package com.goldensoft.goldenlibrary.view.popupwindows.tools;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;

import com.goldensoft.goldenlibrary.R;

/**
 * @author Vondear
 */
class GdPopupViewBackgroundConstructor {

    /**
     * Select which background will be assign to the tip view
     */
    static void setBackground(View tipView, GdPopupView rxPopupView) {

        // show tool tip without arrow. no need to continue
        if (rxPopupView.hideArrow()) {
            setToolTipNoArrowBackground(tipView, rxPopupView.getBackgroundColor());
            return;
        }

        // show tool tip according to requested position
        switch (rxPopupView.getPosition()) {
            case GdPopupView.POSITION_ABOVE:
                setToolTipAboveBackground(tipView, rxPopupView);
                break;
            case GdPopupView.POSITION_BELOW:
                setToolTipBelowBackground(tipView, rxPopupView);
                break;
            case GdPopupView.POSITION_LEFT_TO:
                setToolTipLeftToBackground(tipView, rxPopupView.getBackgroundColor());
                break;
            case GdPopupView.POSITION_RIGHT_TO:
                setToolTipRightToBackground(tipView, rxPopupView.getBackgroundColor());
                break;
            default:
                break;
        }

    }

    private static void setToolTipAboveBackground(View tipView, GdPopupView rxPopupView) {
        switch (rxPopupView.getAlign()) {
            case GdPopupView.ALIGN_CENTER:
                setTipBackground(tipView, R.drawable.tooltip_arrow_down, rxPopupView.getBackgroundColor());
                break;
            case GdPopupView.ALIGN_LEFT:
                setTipBackground(tipView,
                        !GdPopupViewTool.isRtl() ?
                                R.drawable.tooltip_arrow_down_left :
                                R.drawable.tooltip_arrow_down_right
                        , rxPopupView.getBackgroundColor());
                break;
            case GdPopupView.ALIGN_RIGHT:
                setTipBackground(tipView,
                        !GdPopupViewTool.isRtl() ?
                                R.drawable.tooltip_arrow_down_right :
                                R.drawable.tooltip_arrow_down_left
                        , rxPopupView.getBackgroundColor());
                break;
            default:
                break;
        }
    }

    private static void setToolTipBelowBackground(View tipView, GdPopupView rxPopupView) {

        switch (rxPopupView.getAlign()) {
            case GdPopupView.ALIGN_CENTER:
                setTipBackground(tipView, R.drawable.tooltip_arrow_up, rxPopupView.getBackgroundColor());
                break;
            case GdPopupView.ALIGN_LEFT:
                setTipBackground(tipView,
                        !GdPopupViewTool.isRtl() ?
                                R.drawable.tooltip_arrow_up_left :
                                R.drawable.tooltip_arrow_up_right
                        , rxPopupView.getBackgroundColor());
                break;
            case GdPopupView.ALIGN_RIGHT:
                setTipBackground(tipView,
                        !GdPopupViewTool.isRtl() ?
                                R.drawable.tooltip_arrow_up_right :
                                R.drawable.tooltip_arrow_up_left
                        , rxPopupView.getBackgroundColor());
                break;
            default:
                break;
        }

    }

    private static void setToolTipLeftToBackground(View tipView, int color) {
        setTipBackground(tipView, !GdPopupViewTool.isRtl() ?
                        R.drawable.tooltip_arrow_right : R.drawable.tooltip_arrow_left,
                color);
    }

    private static void setToolTipRightToBackground(View tipView, int color) {
        setTipBackground(tipView, !GdPopupViewTool.isRtl() ?
                        R.drawable.tooltip_arrow_left : R.drawable.tooltip_arrow_right,
                color);
    }

    private static void setToolTipNoArrowBackground(View tipView, int color) {
        setTipBackground(tipView, R.drawable.tooltip_no_arrow, color);
    }

    private static void setTipBackground(View tipView, int drawableRes, int color) {
        Drawable paintedDrawable = getTintedDrawable(tipView.getContext(),
                drawableRes, color);
        setViewBackground(tipView, paintedDrawable);
    }

    private static void setViewBackground(View view, Drawable drawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.setBackground(drawable);
        } else {
            view.setBackgroundDrawable(drawable);
        }
    }

    private static Drawable getTintedDrawable(Context context, int drawableRes, int color) {
        Drawable drawable;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            drawable = context.getResources().getDrawable(drawableRes, null);
            if (drawable != null) {
                drawable.setTint(color);
            }
        } else {
            drawable = context.getResources().getDrawable(drawableRes);
            if (drawable != null) {
                drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
            }
        }

        return drawable;
    }

}

