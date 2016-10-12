package com.jimmy.wbclient.utils;

import android.util.Log;

import com.jimmy.wbclient.constants.ComConstants;

/**
 * 日志工具类
 * Created by Administrator on 2016/10/8.
 */
public class LogUtil {

    /**
     * 显示默认Log
     *
     * @param TAG
     * @param msg
     */
    public static void show(String TAG, String msg) {
        show(TAG, msg, Log.INFO);
    }

    /**
     * 显示Log日志
     *
     * @param TAG
     * @param msg
     * @param level
     */
    public static void show(String TAG, String msg, int level) {
        if (!ComConstants.IS_SHOW_LOG) {
            return;
        }
        switch (level) {
            case Log.VERBOSE:
                Log.v(TAG, msg);
                break;
            case Log.INFO:
                Log.i(TAG, msg);
                break;
            case Log.DEBUG:
                Log.d(TAG, msg);
                break;
            case Log.WARN:
                Log.w(TAG, msg);
                break;
            case Log.ERROR:
                Log.e(TAG, msg);
                break;
            default:
                Log.i(TAG, msg);
                break;
        }
    }
}
