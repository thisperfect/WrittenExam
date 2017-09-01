package com.dayee.writtenExam.framework.attachment.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.IOUtils;

public class AttachmentUtils {

    public static String getExt(String name) {

        return name == null ? null : name.substring(name.lastIndexOf("."),
                                                    name.length());
    }

    /**
     * 通过图片url返回图片 bytes
     * 
     * @param urlPath
     * @return
     */
    public static byte[] getBytesFromUrl(String urlPath) {

        URL url = null;
        byte[] bytes = null;
        InputStream is = null;
        try {
            url = new URL(urlPath);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();// 利用HttpURLConnection对象,我们可以从网络中获取网页数据.
            conn.setDoInput(true);
            conn.connect();
            is = conn.getInputStream(); // 得到网络返回的输入流
            bytes = IOUtils.toByteArray(is);
        } catch (IOException e) {
            //e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(is);
        }
        return bytes;
    }

    public static String getFileSizeStr(Long size) {

        String sizeStr = "";
        if (null == size) {
            return sizeStr;
        }
        String t_size = "";
        if (size > 1024 * 1024 * 1L) {
            t_size = String.valueOf((double) size / (1024 * 1024 * 1L));
            if (t_size.indexOf(".") > -1) {
                t_size = t_size.substring(0, t_size.indexOf(".") + 2);
            }
            sizeStr = t_size + "M";
        }

        if (size < 1024 * 1024 * 1L && size > 1024 * 1L) {
            t_size = String.valueOf((double) size / (1024 * 1L));
            if (t_size.indexOf(".") > -1) {
                t_size = t_size.substring(0, t_size.indexOf(".") + 2);
            }
            sizeStr = t_size + "KB";
        }

        if (size < 1024 * 1L) {
            sizeStr = (double) size + "B";
        }

        return sizeStr;
    }
}
