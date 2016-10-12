package com.jimmy.wbclient.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.jimmy.wbclient.BaseActivity;
import com.jimmy.wbclient.R;
import com.jimmy.wbclient.constants.AccessTokenKeeper;
import com.jimmy.wbclient.constants.WBConstants;
import com.jimmy.wbclient.utils.LogUtil;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;

/**
 * Created by Administrator on 2016/10/8.
 */
public class LoginActivity extends BaseActivity {
    /**
     * 封装了 "access_token"，"expires_in"，"refresh_token"，并提供了他们的管理功能
     */
    private Oauth2AccessToken mAccessToken;

    /**
     * 微博授权类对象
     */
    private AuthInfo mAuthInfo;

    /**
     * SSO SSO授权：仅当手机安装新浪微博。客户端时使用
     * WebWeb 授权：在没有客户端的情况下，可直接使用该实现见
     * SSO+Web 授权： 如果手机端安装了新浪微博客户的话会默认发起
     */
    private SsoHandler mSsoHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuthInfo = new AuthInfo(mBaseApp, WBConstants.APP_KEY, WBConstants.REDIRECT_URL,
                WBConstants.SCOPE);
        mSsoHandler = new SsoHandler(this, mAuthInfo);

    }

    public void onLoginBtn(View view) {
        mSsoHandler.authorize(new AuthListener());//请求授权
    }

    /**
     * 当 SSO 授权 Activity 退出时，该函数被调用。
     *
     * @see {@link #onActivityResult}
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mSsoHandler != null) {
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }

    /**
     * 微博认证授权回调类。
     * 1. SSO 授权时，需要在 {@link #onActivityResult} 中调用 {@link SsoHandler#authorizeCallBack} 后，
     * 该回调才会被执行。
     * 2. 非 SSO 授权时，当授权结束后，该回调就会被执行。
     * 当授权成功后，请保存该 access_token、expires_in、uid 等信息到 SharedPreferences 中。
     */
    class AuthListener implements WeiboAuthListener {
        @Override
        public void onComplete(Bundle bundle) {
            mAccessToken = Oauth2AccessToken.parseAccessToken(bundle);
            if (mAccessToken.isSessionValid()) {
                AccessTokenKeeper.writeAccessToken(mBaseApp, mAccessToken);
                intent2Activity(MainActivity.class);
            } else {
                // 以下几种情况，您会收到 Code：
                // 1. 当您未在平台上注册的应用程序的包名与签名时；
                // 2. 当您注册的应用程序包名与签名不正确时；
                // 3. 当您在平台上注册的包名和签名与您当前测试的应用的包名和签名不匹配时。
                String failCode = bundle.getString("code");
                if (!TextUtils.isEmpty(failCode)) {
                    showToast("授权失败：" + failCode);
                }
            }
        }

        @Override
        public void onWeiboException(WeiboException e) {
            showToast("授权失败" + e.getMessage());
            LogUtil.show("LoginActivity", "授权失败:" + e.toString(), Log.ERROR);
        }

        @Override
        public void onCancel() {
            showToast("取消授权");
        }
    }
}
