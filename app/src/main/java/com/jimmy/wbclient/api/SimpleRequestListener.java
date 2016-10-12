package com.jimmy.wbclient.api;

import android.app.Dialog;
import android.content.Context;

import com.jimmy.wbclient.utils.LogUtil;
import com.jimmy.wbclient.utils.ToastUtil;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;

/**
 * Created by Administrator on 2016/10/9.
 */
public class SimpleRequestListener implements RequestListener {
    private Dialog mDialog;
    private Context mContext;

    public SimpleRequestListener(Context context, Dialog dialog) {
        mContext = context;
        mDialog = dialog;
    }

    @Override
    public void onComplete(String s) {
        onAllDone();
//        LogUtil.show("返回结果：", s);
    }

    @Override
    public void onWeiboException(WeiboException e) {
        onAllDone();
        ToastUtil.show(mContext, "获取数据失败，详看日志");
        LogUtil.show("错误结果：", e.toString());
    }

    public void onAllDone() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }
}
