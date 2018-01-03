package com.wusir.download;

import android.os.Environment;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okserver.OkDownload;
import com.lzy.okserver.task.XExecutor;
import com.wusir.adapter.section.Section;
import com.wusir.bean.StandItem;
import com.wusir.util.ToastUtil;

import java.io.File;

/**
 * Created by Administrator on 2017/8/25 0025.
 */

public class DownLoadManger {

    private static DownLoadManger downLoadManger;
    private static OkDownload okDownload;

    public static DownLoadManger getInstance(){
        if(downLoadManger == null){
            downLoadManger = new DownLoadManger();
            initOkDownLoad();
        }
        return downLoadManger;
    }
    private static void initOkDownLoad(){
        okDownload = OkDownload.getInstance();
        okDownload.setFolder(Environment.getExternalStorageDirectory().getPath()+"/downLoad/wuwether");//下载目录
        okDownload.getThreadPool().setCorePoolSize(3);//同时下载数量
        okDownload.addOnAllTaskEndListener(new XExecutor.OnAllTaskEndListener() {
            @Override
            public void onAllTaskEnd() {

            }
        });
    }
    /**
     * 开始下载
     * @param url
     */
    public void startDownLoad(String url, StandItem item){
        if(okDownload.hasTask(url)){
            return;
        }
        //ToastUtil.showToast("已加至下载列表");
        GetRequest<File> request = OkGo.<File>get(url);
        OkDownload.request(url,request)
                .extra1(item)
                .save()
                .start();
    }

    public boolean isExist(String url){
        return okDownload.hasTask(url);
    }
    public void removeAll(){
        okDownload.removeAll(true);
    }
}
