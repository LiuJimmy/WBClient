package com.jimmy.wbclient.utils;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;

import com.jimmy.wbclient.R;

/**
 * Created by Administrator on 2016/10/9.
 */
public class DialogUtil {
    /**
     * 返回加载进度框
     * @param context
     * @return
     */
    public static Dialog createLoadingDialog(Context context) {
        ProgressDialog dialog = new ProgressDialog(context, R.style.DialogCommon);
        return dialog;
    }

}
