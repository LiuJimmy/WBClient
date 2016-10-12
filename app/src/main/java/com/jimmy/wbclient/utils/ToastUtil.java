package com.jimmy.wbclient.utils;

import android.content.Context;
import android.widget.Toast;

import com.jimmy.wbclient.constants.ComConstants;

/**
 * Toast工具类
 * Created by Administrator on 2016/10/8.
 */
public class ToastUtil {
    private static Toast sToast;

    /**
     * 显示LENGTH_SHORT-Toast
     */
    public static void show(Context context, String msg) {
        show(context, msg, Toast.LENGTH_SHORT);
    }

    /**
     * 显示Toast
     */
    public static void show(Context context, String msg, int duration) {
        if (!ComConstants.IS_SHOW_TOAST) {
            return;
        }
        if (sToast == null) {
            sToast = Toast.makeText(context, msg, duration);
        } else {
            sToast.setText(msg);
            sToast.setDuration(duration);
        }
        sToast.show();
    }
}
