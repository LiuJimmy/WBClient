package com.jimmy.wbclient;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;
import com.jimmy.wbclient.api.MyStatusesAPI;
import com.jimmy.wbclient.constants.ComConstants;
import com.jimmy.wbclient.utils.LogUtil;
import com.jimmy.wbclient.utils.ToastUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by Administrator on 2016/10/8.
 */
public class BaseActivity extends AppCompatActivity {

    protected String TAG;
    protected BaseApplication mBaseApp;
    protected SharedPreferences mPref;
    protected MyStatusesAPI mAPI;
    protected Intent mIntent;
    protected Gson mGson;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        TAG = getClass().getSimpleName();
        mBaseApp = (BaseApplication) getApplication();
        mPref = getSharedPreferences(ComConstants.SP_NAME, MODE_PRIVATE);

        mAPI = new MyStatusesAPI(this);
        mIntent = new Intent();
        mGson = new Gson();

    }


    protected void intent2Activity(Class<? extends Activity> tarActivity) {
        startActivity(new Intent(this, tarActivity));
    }

    public void showToast(String msg) {
        ToastUtil.show(this, msg);
    }

    public void showLog(String msg) {
        LogUtil.show(TAG, msg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImageLoader.getInstance().clearMemoryCache();
        ImageLoader.getInstance().clearDiskCache();
    }
}
