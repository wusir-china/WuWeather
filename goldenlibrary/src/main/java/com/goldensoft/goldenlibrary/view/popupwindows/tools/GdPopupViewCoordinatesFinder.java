package com.goldensoft.goldenlibrary.view.popupwindows.tools;

import android.graphics.Point;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * @author golden
 */
class GdPopupViewCoordinatesFinder {

    /**
     * return the top left coordinates for positioning the tip
     *
     * @param tipView - the newly created tip view
     * @param popupView - tool tip object
     * @return point
     */
    static Point getCoordinates(final TextView tipView, GdPopupView popupView) {
        Point point = new Point();
        final GdCoordinates anchorViewGdCoordinates = new GdCoordinates(popupView.getAnchorView());
        final GdCoordinates rootGdCoordinates = new GdCoordinates(popupView.getRootView());

        tipView.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        switch (popupView.getPosition()) {
            case GdPopupView.POSITION_ABOVE:
                point = getPositionAbove(tipView, popupView,
                        anchorViewGdCoordinates, rootGdCoordinates);
                break;
            case GdPopupView.POSITION_BELOW:
                point = getPositionBelow(tipView, popupView,
                        anchorViewGdCoordinates, rootGdCoordinates);
                break;
            case GdPopupView.POSITION_LEFT_TO:
                point = getPositionLeftTo(tipView, popupView,
                        anchorViewGdCoordinates, rootGdCoordinates);
                break;
            case GdPopupView.POSITION_RIGHT_TO:
                point = getPositionRightTo(tipView, popupView,
                        anchorViewGdCoordinates, rootGdCoordinates);
                break;
            default:
                break;
        }

        // add user defined offset values
        point.x += GdPopupViewTool.isRtl() ? -popupView.getOffsetX() : popupView.getOffsetX();
        point.y += popupView.getOffsetY();

        // coordinates retrieved are relative to 0,0 of the root layout
        // added view to root is subject to root padding
        // we need to subtract the top and left padding of root from coordinates. to adjust
        // top left tip coordinates
        point.x -= popupView.getRootView().getPaddingLeft();
        point.y -= popupView.getRootView().getPaddingTop();

        return point;

    }

    private static Point getPositionRightTo(TextView tipView, GdPopupView GdPopupView, GdCoordinates anchorViewGdCoordinates, GdCoordinates rootLocation) {
        Point point = new Point();
        point.x = anchorViewGdCoordinates.right;
        AdjustRightToOutOfBounds(tipView, GdPopupView.getRootView(), point, anchorViewGdCoordinates, rootLocation);
        point.y = anchorViewGdCoordinates.top + getYCenteringOffset(tipView, GdPopupView);
        return point;
    }

    private static Point getPositionLeftTo(TextView tipView, GdPopupView GdPopupView, GdCoordinates anchorViewGdCoordinates, GdCoordinates rootLocation) {
        Point point = new Point();
        point.x = anchorViewGdCoordinates.left - tipView.getMeasuredWidth();
        AdjustLeftToOutOfBounds(tipView, GdPopupView.getRootView(), point, anchorViewGdCoordinates, rootLocation);
        point.y = anchorViewGdCoordinates.top + getYCenteringOffset(tipView, GdPopupView);
        return point;
    }

    private static Point getPositionBelow(TextView tipView, GdPopupView GdPopupView, GdCoordinates anchorViewGdCoordinates, GdCoordinates rootLocation) {
        Point point = new Point();
        point.x = anchorViewGdCoordinates.left + getXOffset(tipView, GdPopupView);
        if (GdPopupView.alignedCenter()) {
            AdjustHorizontalCenteredOutOfBounds(tipView, GdPopupView.getRootView(), point, rootLocation);
        } else if (GdPopupView.alignedLeft()){
            AdjustHorizontalLeftAlignmentOutOfBounds(tipView, GdPopupView.getRootView(), point, anchorViewGdCoordinates, rootLocation);
        } else if (GdPopupView.alignedRight()){
            AdjustHorizotalRightAlignmentOutOfBounds(tipView, GdPopupView.getRootView(), point, anchorViewGdCoordinates, rootLocation);
        }
        point.y = anchorViewGdCoordinates.bottom;
        return point;
    }

    private static Point getPositionAbove(TextView tipView, GdPopupView GdPopupView,
                                          GdCoordinates anchorViewGdCoordinates, GdCoordinates rootLocation) {
        Point point = new Point();
        point.x = anchorViewGdCoordinates.left + getXOffset(tipView, GdPopupView);
        if (GdPopupView.alignedCenter()) {
            AdjustHorizontalCenteredOutOfBounds(tipView, GdPopupView.getRootView(), point, rootLocation);
        } else if (GdPopupView.alignedLeft()){
            AdjustHorizontalLeftAlignmentOutOfBounds(tipView, GdPopupView.getRootView(), point, anchorViewGdCoordinates, rootLocation);
        } else if (GdPopupView.alignedRight()){
            AdjustHorizotalRightAlignmentOutOfBounds(tipView, GdPopupView.getRootView(), point, anchorViewGdCoordinates, rootLocation);
        }
        point.y = anchorViewGdCoordinates.top - tipView.getMeasuredHeight();
        return point;
    }

    private static void AdjustRightToOutOfBounds(TextView tipView, ViewGroup root, Point point, GdCoordinates anchorViewGdCoordinates, GdCoordinates rootLocation) {
        ViewGroup.LayoutParams params = tipView.getLayoutParams();
        int availableSpace = rootLocation.right - root.getPaddingRight() - anchorViewGdCoordinates.right;
        if (point.x + tipView.getMeasuredWidth() > rootLocation.right - root.getPaddingRight()){
            params.width = availableSpace;
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            tipView.setLayoutParams(params);
            measureViewWithFixedWidth(tipView, params.width);
        }
    }

    private static void AdjustLeftToOutOfBounds(TextView tipView, ViewGroup root, Point point, GdCoordinates anchorViewGdCoordinates, GdCoordinates rootLocation) {
        ViewGroup.LayoutParams params = tipView.getLayoutParams();
        int rootLeft = rootLocation.left + root.getPaddingLeft();
        if (point.x < rootLeft){
            int availableSpace = anchorViewGdCoordinates.left - rootLeft;
            point.x = rootLeft;
            params.width = availableSpace;
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            tipView.setLayoutParams(params);
            measureViewWithFixedWidth(tipView, params.width);
        }
    }

    private static void AdjustHorizotalRightAlignmentOutOfBounds(TextView tipView, ViewGroup root,
                                                                 Point point, GdCoordinates anchorViewGdCoordinates,
                                                                 GdCoordinates rootLocation) {
        ViewGroup.LayoutParams params = tipView.getLayoutParams();
        int rootLeft = rootLocation.left + root.getPaddingLeft();
        if (point.x < rootLeft){
            int availableSpace = anchorViewGdCoordinates.right - rootLeft;
            point.x = rootLeft;
            params.width = availableSpace;
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            tipView.setLayoutParams(params);
            measureViewWithFixedWidth(tipView, params.width);
        }
    }

    private static void AdjustHorizontalLeftAlignmentOutOfBounds(TextView tipView, ViewGroup root,
                                                                 Point point, GdCoordinates anchorViewGdCoordinates,
                                                                 GdCoordinates rootLocation) {
        ViewGroup.LayoutParams params = tipView.getLayoutParams();
        int rootRight = rootLocation.right - root.getPaddingRight();
        if (point.x + tipView.getMeasuredWidth() > rootRight){
            params.width = rootRight - anchorViewGdCoordinates.left;
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            tipView.setLayoutParams(params);
            measureViewWithFixedWidth(tipView, params.width);
        }
    }

    private static void AdjustHorizontalCenteredOutOfBounds(TextView tipView, ViewGroup root,
                                                            Point point, GdCoordinates rootLocation) {
        ViewGroup.LayoutParams params = tipView.getLayoutParams();
        int rootWidth = root.getWidth() - root.getPaddingLeft() - root.getPaddingRight();
        if (tipView.getMeasuredWidth() > rootWidth) {
            point.x = rootLocation.left + root.getPaddingLeft();
            params.width = rootWidth;
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            tipView.setLayoutParams(params);
            measureViewWithFixedWidth(tipView, rootWidth);
        }
    }


    private static void measureViewWithFixedWidth(TextView tipView, int width) {
        tipView.measure(View.MeasureSpec.makeMeasureSpec(width,
                View.MeasureSpec.EXACTLY), ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    /**
     * calculate the amount of movement need to be taken inorder to align tip
     * on X axis according to "align" parameter
     * @return int
     */
    private static int getXOffset(View tipView, GdPopupView gdPopupView) {
        int offset;

        switch (gdPopupView.getAlign()) {
            case GdPopupView.ALIGN_CENTER:
                offset = ((gdPopupView.getAnchorView().getWidth() - tipView.getMeasuredWidth()) / 2);
                break;
            case GdPopupView.ALIGN_LEFT:
                offset = 0;
                break;
            case GdPopupView.ALIGN_RIGHT:
                offset = gdPopupView.getAnchorView().getWidth() - tipView.getMeasuredWidth();
                break;
            default:
                offset = 0;
                break;
        }

        return offset;
    }

    /**
     * calculate the amount of movement need to be taken inorder to center tip
     * on Y axis
     * @return int
     */
    private static int getYCenteringOffset(View tipView, GdPopupView GdPopupView) {
        return (GdPopupView.getAnchorView().getHeight() - tipView.getMeasuredHeight()) / 2;
    }

}
