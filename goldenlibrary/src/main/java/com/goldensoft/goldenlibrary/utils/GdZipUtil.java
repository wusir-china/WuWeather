package com.goldensoft.goldenlibrary.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * 
 * Filename:    GdZipUtil.java
 * Description: 压缩解押类  
 * Copyright:   Copyright (c) 2012-2013 All Rights Reserved.
 * Company:     zjmi.com Inc.
 * @author:     yuhg 
 * @version:    1.0  
 * Create at:   2013-9-27 下午2:05:27  
 *  
 * Modification History:  
 * Date         Author      Version     Description  
 * ------------------------------------------------------------------  
 * 2013-9-27      yuhg      1.0         1.0 Version  
 *
 */
public class GdZipUtil {

    static String code = "UTF-8";

    public GdZipUtil() {
    }

    public static String compress(String s) throws IOException {
        if (s != null && s.length() != 0) {
            ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
            GZIPOutputStream gzipoutputstream = new GZIPOutputStream(bytearrayoutputstream);
            gzipoutputstream.write(s.getBytes());
            gzipoutputstream.close();
            s = bytearrayoutputstream.toString("ISO-8859-1");
        }
        return s;
    }

    public static byte[] compress(byte[] input) throws IOException {
        if (input != null && input.length != 0) {
            ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
            GZIPOutputStream gzipoutputstream = new GZIPOutputStream(bytearrayoutputstream);
            gzipoutputstream.write(input);
            gzipoutputstream.close();
            input = bytearrayoutputstream.toByteArray();
        }
        return input;
    }

    public static String uncompress(String paramString) throws IOException {
        if ((paramString == null) || (paramString.length() == 0))
            return paramString;
        ByteArrayOutputStream bOutputStream = new ByteArrayOutputStream();
        GZIPInputStream gzipInputStream = new GZIPInputStream(new ByteArrayInputStream(paramString.getBytes("ISO-8859-1")));
        byte[] bytes = new byte[1024];
        int i = 0;
        while ((i = gzipInputStream.read(bytes)) != -1) {
            bOutputStream.write(bytes, 0, i);
        }
        paramString = bOutputStream.toString();
        return paramString;
    }

    public static byte[] uncompress(byte[] input) throws IOException {
        if ((input == null) || (input.length == 0))
            return input;
        ByteArrayOutputStream bOutputStream = new ByteArrayOutputStream();
        GZIPInputStream gzipInputStream = new GZIPInputStream(new ByteArrayInputStream(input));
        byte[] bytes = new byte[1024];
        int i = 0;
        while ((i = gzipInputStream.read(bytes)) != -1) {
            bOutputStream.write(bytes, 0, i);
        }
        return bOutputStream.toByteArray();

    }

    public static void main(String[] args) throws IOException {
        System.out.println(uncompress(compress("i come from china 压缩之后的字符串大小")));
    }
}
