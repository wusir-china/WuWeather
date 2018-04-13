package com.goldensoft.goldenlibrary.view.popupwindows.tools;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.graphics.Outline;
import android.graphics.Point;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.TextView;

import com.goldensoft.goldenlibrary.utils.GdAnimationTool;

import java.util.HashMap;
import java.util.Map;

/**
 * @author vondear
 */
public class GdPopupViewManager {

    private static final String TAG = GdPopupViewManager.class.getSimpleName();

    private static final int DEFAULT_ANIM_DURATION = 400;

    // Parameter for managing tip creation or reuse
    private Map<Integer, View> mTipsMap = new HashMap<>();

    private int mAnimationDuration;
    private TipListener mListener;

    public GdPopupViewManager() {
        mAnimationDuration = DEFAULT_ANIM_DURATION;
    }

    public GdPopupViewManager(TipListener listener) {
        mAnimationDuration = DEFAULT_ANIM_DURATION;
        mListener = listener;
    }

    public View show(GdPopupView GdPopupView) {
        View tipView = create(GdPopupView);
        if (tipView == null) {
            return null;
        }

        // animate tip visibility
        GdAnimationTool.popup(tipView, mAnimationDuration).start();

        return tipView;
    }

    private View create(GdPopupView GdPopupView) {

        if (GdPopupView.getAnchorView() == null) {
            Log.e(TAG, "Unable to create a tip, anchor view is null");
            return null;
        }

        if (GdPopupView.getRootView() == null) {
            Log.e(TAG, "Unable to create a tip, root layout is null");
            return null;
        }

        // only one tip is allowed near an anchor view at the same time, thus
        // reuse tip if already exist
        if (mTipsMap.containsKey(GdPopupView.getAnchorView().getId())) {
            return mTipsMap.get(GdPopupView.getAnchorView().getId());
        }

        // init tip view parameters
        TextView tipView = createTipView(GdPopupView);

        // on RTL languages replace sides
        if (GdPopupViewTool.isRtl()) {
            switchToolTipSidePosition(GdPopupView);
        }

        // set tool tip background / shape
        GdPopupViewBackgroundConstructor.setBackground(tipView, GdPopupView);

        // add tip to root layout
        GdPopupView.getRootView().addView(tipView);

        // find where to position the tool tip
        Point p = GdPopupViewCoordinatesFinder.getCoordinates(tipView, GdPopupView);

        // move tip view to correct position
        moveTipToCorrectPosition(tipView, p);

        // set dismiss on click
        tipView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss(view, true);
            }
        });

        // bind tipView with anchorView id
        int anchorViewId = GdPopupView.getAnchorView().getId();
        tipView.setTag(anchorViewId);

        // enter tip to map by 'anchorView' id
        mTipsMap.put(anchorViewId, tipView);

        return tipView;

    }

    private void moveTipToCorrectPosition(TextView tipView, Point p) {
        GdCoordinates tipViewGdCoordinates = new GdCoordinates(tipView);
        int translationX = p.x - tipViewGdCoordinates.left;
        int translationY = p.y - tipViewGdCoordinates.top;
        if (!GdPopupViewTool.isRtl()) tipView.setTranslationX(translationX);
        else tipView.setTranslationX(-translationX);
        tipView.setTranslationY(translationY);
    }

    @NonNull
    private TextView createTipView(GdPopupView GdPopupView) {
        TextView tipView = new TextView(GdPopupView.getContext());
        tipView.setTextColor(GdPopupView.getTextColor());
        tipView.setTextSize(GdPopupView.getTextSize());
        tipView.setText(GdPopupView.getMessage() != null ? GdPopupView.getMessage() : GdPopupView.getSpannableMessage());
        tipView.setVisibility(View.INVISIBLE);
        tipView.setGravity(GdPopupView.getTextGravity());
        setTipViewElevation(tipView, GdPopupView);
        return tipView;
    }

    private void setTipViewElevation(TextView tipView, GdPopupView GdPopupView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (GdPopupView.getElevation() > 0) {
                ViewOutlineProvider viewOutlineProvider = new ViewOutlineProvider() {
                    @SuppressLint("NewApi")
                    @Override
                    public void getOutline(View view, Outline outline) {
                        outline.setEmpty();
                    }
                };
                tipView.setOutlineProvider(viewOutlineProvider);
                tipView.setElevation(GdPopupView.getElevation());
            }
        }
    }

    private void switchToolTipSidePosition(GdPopupView GdPopupView) {
        if (GdPopupView.positionedLeftTo()) {
            GdPopupView.setPosition(GdPopupView.POSITION_RIGHT_TO);
        } else if (GdPopupView.positionedRightTo()) {
            GdPopupView.setPosition(GdPopupView.POSITION_LEFT_TO);
        }
    }

    public void setAnimationDuration(int duration) {
        mAnimationDuration = duration;
    }

    public boolean dismiss(View tipView, boolean byUser) {
        if (tipView != null && isVisible(tipView)) {
            int key = (int) tipView.getTag();
            mTipsMap.remove(key);
            animateDismiss(tipView, byUser);
            return true;
        }
        return false;
    }

    public boolean dismiss(Integer key) {
        return mTipsMap.containsKey(key) && dismiss(mTipsMap.get(key), false);
    }

    public View find(Integer key) {
        if (mTipsMap.containsKey(key)) {
            return mTipsMap.get(key);
        }
        return null;
    }

    public boolean findAndDismiss(final View anchorView) {
        View view = find(anchorView.getId());
        return view != null && dismiss(view, false);
    }

    public void clear() {
        if (!mTipsMap.isEmpty()) {
            for (Map.Entry<Integer, View> entry : mTipsMap.entrySet()) {
                dismiss(entry.getValue(), false);
            }
        }
        mTipsMap.clear();
    }

    private void animateDismiss(final View view, final boolean byUser) {
        GdAnimationTool.popout(view, mAnimationDuration, new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (mListener != null) {
                    mListener.onTipDismissed(view, (Integer) view.getTag(), byUser);
                }
            }
        }).start();
    }

    public boolean isVisible(View tipView) {
        return tipView.getVisibility() == View.VISIBLE;
    }

    public interface TipListener {
        void onTipDismissed(View view, int anchorViewId, boolean byUser);
    }

}

