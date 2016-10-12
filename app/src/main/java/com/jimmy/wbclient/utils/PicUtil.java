package com.jimmy.wbclient.utils;

import java.util.ArrayList;

/**
 * 处理配图地址的工具类
 * Created by Administrator on 2016/10/9.
 */
public class PicUtil {

    /**
     * 获取中等图片地址集合
     *
     * @param thumUrls 缩略图地址
     * @return 中等图片地址集合
     */
    public static ArrayList<String> getBmidUtil(ArrayList<String> thumUrls) {
        if (thumUrls == null) {
            return null;
        }
        ArrayList<String> mbmidUrls = new ArrayList<>();//中等图
        for (int i = 0; i < thumUrls.size(); i++) {
            mbmidUrls.add(thumUrls.get(i).replace("thumbnail", "bmiddle"));
        }
        return mbmidUrls;
    }

    /**
     * 获取原始图片地址集合
     *
     * @param thumUrls 缩略图/或中等图地址
     * @return 原始图片地址集合
     */
    public static ArrayList<String> getOrigUtil(ArrayList<String> thumUrls) {
        if (thumUrls == null) {
            return null;
        }
        ArrayList<String> mOrigUrls = new ArrayList<>();//原始图
        for (int i = 0; i < thumUrls.size(); i++) {
            mOrigUrls.add(thumUrls.get(i).replace("thumbnail", "large"));
        }
        return mOrigUrls;
    }
}
