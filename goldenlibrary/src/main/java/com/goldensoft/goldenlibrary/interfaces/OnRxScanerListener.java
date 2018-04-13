package com.goldensoft.goldenlibrary.interfaces;

import com.google.zxing.Result;

/**
 * Created by golden on 2018/4/3.
 */

public interface OnRxScanerListener {
    void onSuccess(String type, Result result);

    void onFail(String type, String message);
}
