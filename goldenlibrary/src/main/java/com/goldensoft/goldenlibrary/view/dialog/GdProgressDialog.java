package com.goldensoft.goldenlibrary.view.dialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.Gravity;
import android.widget.ProgressBar;

/**
 * Created by golden on 2018/4/4.
 * ProgressDialog的基本操作
 */

public class GdProgressDialog {
    private static ProgressDialog pd;
    public void init(Context context){
        pd = new ProgressDialog(context);
        pd.setTitle("请稍等");
        //设置对话进度条样式为水平
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        //设置提示信息
        pd.setMessage("正在玩命下载中......");
        pd.setMax(100);
        //设置对话进度条显示在屏幕顶部（方便截图）
        pd.getWindow().setGravity(Gravity.CENTER);
    }
    //显示
    public void show(){
        pd.show();
    }
    //显示当前进度
    public void setCurrentProgress(int currentProgress){
        pd.setProgress(currentProgress);
    }
    //消失
    public void dismiss(){
        pd.dismiss();
    }
}
