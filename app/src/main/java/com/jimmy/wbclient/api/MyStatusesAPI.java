package com.jimmy.wbclient.api;

import android.content.Context;

import com.jimmy.wbclient.constants.AccessTokenKeeper;
import com.jimmy.wbclient.constants.WBConstants;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.legacy.StatusesAPI;

/**
 * Created by Administrator on 2016/10/9.
 */
public class MyStatusesAPI extends StatusesAPI {
    public MyStatusesAPI(Context context, String appKey, Oauth2AccessToken accessToken) {
        super(context, appKey, accessToken);
    }

    public MyStatusesAPI(Context context) {
        super(context, WBConstants.APP_KEY, AccessTokenKeeper.readAccessToken(context));
    }


    /**
     * 获取当前登录用户及其所关注用户的最新微博。
     *
     * @param count    单页返回的记录条数，默认为50
     * @param page     返回结果的页码，默认为1
     * @param listener 异步请求回调接口
     */
    public void homeTimeline(int count, int page, RequestListener listener) {
        super.homeTimeline(0, 0, count, page, false, 0, false, listener);
    }

    /**
     * 获取微博官方表情的详细信息。
     *
     * @param listener 异步请求回调接口
     */
    public void emotions(RequestListener listener) {
        super.emotions("face", "cnname", listener);
    }
}
