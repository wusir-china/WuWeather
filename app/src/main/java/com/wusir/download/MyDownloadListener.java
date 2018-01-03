package com.wusir.download;

import com.lzy.okgo.model.Progress;
import com.lzy.okserver.download.DownloadListener;

import java.io.File;

/**
 * Created by zy on 2017/9/15.
 */

public class MyDownloadListener extends DownloadListener{
    public MyDownloadListener(Object tag) {
        super(tag);
    }

    @Override
    public void onStart(Progress progress) {

    }

    @Override
    public void onProgress(Progress progress) {

    }

    @Override
    public void onError(Progress progress) {

    }

    @Override
    public void onFinish(File file, Progress progress) {

    }

    @Override
    public void onRemove(Progress progress) {

    }
}
