package com.wusir.download;

import android.content.Context;
import android.os.Environment;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.db.DownloadManager;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okserver.OkDownload;
import com.lzy.okserver.download.DownloadTask;

import java.io.File;

/**
 * Created by zy on 2017/9/15.
 */

public class DownloadUtil {
    public static void downloadApk(Context context, String gameName, String iconUrl, String downurl) {
        String path= Environment.getExternalStorageDirectory().getPath()+"wuweather";
        GetRequest<File> request=OkGo.<File>get(downurl);
        OkDownload.request("",request)
                .folder(path).fileName(gameName+".apk").save().start();


    }
}
