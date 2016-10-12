package com.jimmy.wbclient.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.WindowManager;

import com.jimmy.wbclient.BaseActivity;
import com.jimmy.wbclient.R;
import com.jimmy.wbclient.constants.AccessTokenKeeper;
import com.jimmy.wbclient.utils.HttpUtil;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

public class WelcomActivity extends BaseActivity {
    public static final String KEY_IS_FIRST_START = "is_first_start";

    private static final int WHAT_MAIN = 1;
    private static final int WHAT_LOGIN = 2;
    private static long DELAY_TIME;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == WHAT_MAIN) {
                intent2Activity(MainActivity.class);
            } else {
                intent2Activity(LoginActivity.class);
            }
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager
                .LayoutParams.FLAG_FULLSCREEN);//去掉状态栏和TabBar
        setContentView(R.layout.activity_welcom);

        //头次安装需要下载表情文件
        boolean mIS_FIRST_START = mPref.getBoolean(KEY_IS_FIRST_START, true);
        if (mIS_FIRST_START) {
            HttpUtil.updateEmojiJSON();
            mPref.edit().putBoolean(KEY_IS_FIRST_START, false).apply();
            DELAY_TIME = 3000;
        } else {
            DELAY_TIME = 1000;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Oauth2AccessToken token = AccessTokenKeeper.readAccessToken(this);
        if (token.isSessionValid()) {
            mHandler.sendEmptyMessageDelayed(WHAT_MAIN, DELAY_TIME);
        } else {
            mHandler.sendEmptyMessageDelayed(WHAT_LOGIN, DELAY_TIME);
        }
    }
}
