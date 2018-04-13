package com.goldensoft.goldenlibrary.utils;

import java.io.IOException;

/**
 * Created by Administrator on 2018/3/28.
 */

public class GdCryptUtil {
    public static String decrypt(String paras) throws Exception {
        String str = "";
        try {
            str = new String(decrypt(paras.getBytes("UTF-8")), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }
    public static byte[] decrypt(byte[] paramArrayOfByte) throws Exception {
        return GdZipUtil.uncompress(Base64.decode(paramArrayOfByte));
    }
}
