package com.goldensoft.goldenlibrary.view.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.goldensoft.goldenlibrary.R;
import com.goldensoft.goldenlibrary.utils.GdRegTool;
import com.goldensoft.goldenlibrary.utils.GdTextTool;
import com.goldensoft.goldenlibrary.view.dialog.GdDialog;


/**
 * @author vondear
 * @date 2016/7/19
 * 确认 弹出框
 */
public class GdDialogSure extends GdDialog {

    private ImageView mIvLogo;
    private TextView mTvTitle;
    private TextView mTvContent;
    private TextView mTvSure;

    public GdDialogSure(Context context, int themeResId) {
        super(context, themeResId);
        initView();
    }

    public GdDialogSure(Context context, boolean cancelable, DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initView();
    }

    public GdDialogSure(Context context) {
        super(context);
        initView();
    }

    public GdDialogSure(Context context, float alpha, int gravity) {
        super(context, alpha, gravity);
        initView();
    }

    public ImageView getLogoView() {
        return mIvLogo;
    }

    public TextView getTitleView() {
        return mTvTitle;
    }

    public TextView getSureView() {
        return mTvSure;
    }

    public void setSureListener(View.OnClickListener listener) {
        mTvSure.setOnClickListener(listener);
    }

    public TextView getContentView() {
        return mTvContent;
    }

    public void setLogo(int resId) {
        mIvLogo.setImageResource(resId);
    }

    public void setTitle(String title) {
        mTvTitle.setText(title);
    }

    public void setSure(String content) {
        mTvSure.setText(content);
    }

    public void setContent(String str) {
        if (GdRegTool.isURL(str)) {
            // 响应点击事件的话必须设置以下属性
            mTvContent.setMovementMethod(LinkMovementMethod.getInstance());
            mTvContent.setText(GdTextTool.getBuilder("").setBold().append(str).setUrl(str).create());//当内容为网址的时候，内容变为可点击
        } else {
            mTvContent.setText(str);
        }

    }

    private void initView() {
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_sure, null);
        mTvSure = (TextView) dialogView.findViewById(R.id.tv_sure);
        mTvTitle = (TextView) dialogView.findViewById(R.id.tv_title);
        mTvTitle.setTextIsSelectable(true);
        mTvContent = (TextView) dialogView.findViewById(R.id.tv_content);
        mTvContent.setMovementMethod(ScrollingMovementMethod.getInstance());
        mTvContent.setTextIsSelectable(true);
        mIvLogo = (ImageView) dialogView.findViewById(R.id.iv_logo);
        setContentView(dialogView);
    }

}

