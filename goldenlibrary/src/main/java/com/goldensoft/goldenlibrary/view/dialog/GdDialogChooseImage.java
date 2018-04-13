package com.goldensoft.goldenlibrary.view.dialog;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.goldensoft.goldenlibrary.R;
import com.goldensoft.goldenlibrary.utils.GdPhotoTool;


/**
 * @author vondear
 * @date 2017/3/20
 * 封装了从相册/相机 获取 图片 的Dialog.
 */
public class GdDialogChooseImage extends GdDialog {

    private LayoutType mLayoutType = LayoutType.TITLE;
    private TextView mTvCamera;
    private TextView mTvFile;
    private TextView mTvCancel;

    public GdDialogChooseImage(Activity context) {
        super(context);
        initView(context);
    }

    public GdDialogChooseImage(Fragment fragment) {
        super(fragment.getContext());
        initView(fragment);
    }

    public GdDialogChooseImage(Activity context, int themeResId) {
        super(context, themeResId);
        initView(context);
    }

    public GdDialogChooseImage(Fragment fragment, int themeResId) {
        super(fragment.getContext(), themeResId);
        initView(fragment);
    }

    public GdDialogChooseImage(Activity context, float alpha, int gravity) {
        super(context, alpha, gravity);
        initView(context);
    }

    public GdDialogChooseImage(Fragment fragment, float alpha, int gravity) {
        super(fragment.getContext(), alpha, gravity);
        initView(fragment);
    }

    public GdDialogChooseImage(Fragment fragment, LayoutType layoutType) {
        super(fragment.getContext());
        mLayoutType = layoutType;
        initView(fragment);
    }


    public GdDialogChooseImage(Activity context, LayoutType layoutType) {
        super(context);
        mLayoutType = layoutType;
        initView(context);
    }

    public GdDialogChooseImage(Activity context, int themeResId, LayoutType layoutType) {
        super(context, themeResId);
        mLayoutType = layoutType;
        initView(context);
    }

    public GdDialogChooseImage(Fragment fragment, int themeResId, LayoutType layoutType) {
        super(fragment.getContext(), themeResId);
        mLayoutType = layoutType;
        initView(fragment);
    }

    public GdDialogChooseImage(Activity context, float alpha, int gravity, LayoutType layoutType) {
        super(context, alpha, gravity);
        mLayoutType = layoutType;
        initView(context);
    }

    public GdDialogChooseImage(Fragment fragment, float alpha, int gravity, LayoutType layoutType) {
        super(fragment.getContext(), alpha, gravity);
        mLayoutType = layoutType;
        initView(fragment);
    }

    public TextView getFromCameraView() {
        return mTvCamera;
    }

    public TextView getFromFileView() {
        return mTvFile;
    }

    public TextView getCancelView() {
        return mTvCancel;
    }

    public LayoutType getLayoutType() {
        return mLayoutType;
    }

    private void initView(final Activity activity) {
        View dialogView = null;
        switch (mLayoutType) {
            case TITLE:
                dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_picker_picture, null);
                break;
            case NO_TITLE:
                dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_camero_show, null);
                break;
            default:
                break;
        }


        mTvCamera = dialogView.findViewById(R.id.tv_camera);
        mTvFile = dialogView.findViewById(R.id.tv_file);
        mTvCancel = dialogView.findViewById(R.id.tv_cancel);
        mTvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                cancel();
            }
        });
        mTvCamera.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                GdPhotoTool.openCameraImage(activity);
                cancel();
            }
        });
        mTvFile.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                GdPhotoTool.openLocalImage(activity);
                cancel();
            }
        });
        setContentView(dialogView);
        mLayoutParams.gravity = Gravity.BOTTOM;
    }

    private void initView(final Fragment fragment) {
        View dialogView = null;
        switch (mLayoutType) {
            case TITLE:
                dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_picker_picture, null);
                break;
            case NO_TITLE:
                dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_camero_show, null);
                break;
            default:
                break;
        }

        mTvCamera =  dialogView.findViewById(R.id.tv_camera);
        mTvFile =  dialogView.findViewById(R.id.tv_file);
        mTvCancel =  dialogView.findViewById(R.id.tv_cancel);
        mTvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                cancel();
            }
        });
        mTvCamera.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                //请求Camera权限
                GdPhotoTool.openCameraImage(fragment);
                cancel();
            }
        });
        mTvFile.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                GdPhotoTool.openLocalImage(fragment);
                cancel();
            }
        });

        setContentView(dialogView);
        mLayoutParams.gravity = Gravity.BOTTOM;
    }

    public enum LayoutType {
        TITLE, NO_TITLE
    }
}

