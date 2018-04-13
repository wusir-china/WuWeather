package com.goldensoft.goldenlibrary.view.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.goldensoft.goldenlibrary.R;

/**
 * Created by golden on 2018/4/4.
 */

public class GdAlertDialog {

    private void init(Context context,String... params){
        // 创建构建器
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        // 设置参数
        builder.setTitle(params[0])
                .setIcon(R.drawable.icon)
                .setMessage(params[1])
                .setPositiveButton("美", new DialogInterface.OnClickListener() {// 积极

                    @Override
                    public void onClick(DialogInterface dialog,int which) {
                        // TODO Auto-generated method stub

                    }
                })
                .setNegativeButton("不美", new DialogInterface.OnClickListener() {// 消极

                    @Override
                    public void onClick(DialogInterface dialog,int which) {
                        // TODO Auto-generated method stub

                    }
                })
                .setNeutralButton("不知道", new DialogInterface.OnClickListener() {// 中间级

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub

                    }
                });
        builder.create().show();
    }

    private void setItem(Context context){
        // 创建数据
        final String[] items = new String[] { "北京", "上海", "广州", "深圳" };
        // 创建对话框构建器
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        // 设置参数
        builder.setIcon(R.drawable.icon)
                .setTitle("提示")
                .setItems(items, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //得到items[which]
                    }
                });
        builder.create().show();
    }
    //设置单选
    private void setSingleChoiceItems(Context context){
        // 创建数据
        final String[] items = new String[] { "北京", "上海", "广州", "深圳" };
        // 创建对话框构建器
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        // 设置参数
        builder.setIcon(R.drawable.icon).setTitle("提示")
                .setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        //items[which]
                    }
                });
        builder.create().show();
    }
}
