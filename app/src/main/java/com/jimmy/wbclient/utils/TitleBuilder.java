package com.jimmy.wbclient.utils;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jimmy.wbclient.R;

/**
 * TitleBuilder工具类
 * Created by Administrator on 2016/10/9.
 */
public class TitleBuilder {
    private View mTitleBar;
    private ImageView mIV_Left, mIV_Right;
    private TextView mTV_Left, mTV_Title, mTV_Right;

    public TitleBuilder(Activity activity) {
        mTitleBar = activity.findViewById(R.id.rl_titlebar);
        findSubView();
    }

    public TitleBuilder(View view) {
        mTitleBar = view.findViewById(R.id.rl_titlebar);
        findSubView();
    }

    private void findSubView() {
        mIV_Left = (ImageView) mTitleBar.findViewById(R.id.iv_titlebar_left);
        mIV_Right = (ImageView) mTitleBar.findViewById(R.id.iv_titlebar_right);
        mTV_Left = (TextView) mTitleBar.findViewById(R.id.tv_titlebar_left);
        mTV_Title = (TextView) mTitleBar.findViewById(R.id.tv_titlebar_middle);
        mTV_Right = (TextView) mTitleBar.findViewById(R.id.tv_titlebar_right);
    }

    public TitleBuilder setBackgroundRes(int resId) {
        mTitleBar.setBackgroundResource(resId);
        return this;
    }

    public TitleBuilder setLeftImage(int resId) {
        mIV_Left.setVisibility(resId > 0 ? View.VISIBLE : View.GONE);
        mIV_Left.setImageResource(resId);
        return this;
    }

    public TitleBuilder setLeftText(String text) {
        mTV_Left.setVisibility(TextUtils.isEmpty(text) ? View.GONE : View.VISIBLE);
        mTV_Left.setText(text);
        return this;
    }

    public TitleBuilder setTitleText(String text) {
        mTV_Title.setVisibility(TextUtils.isEmpty(text) ? View.GONE : View.VISIBLE);
        mTV_Title.setText(text);
        return this;
    }

    public TitleBuilder setRightImage(int resId) {
        mIV_Right.setVisibility(resId > 0 ? View.VISIBLE : View.GONE);
        mIV_Right.setImageResource(resId);
        return this;
    }

    public TitleBuilder setRightText(String text) {
        mTV_Right.setVisibility(TextUtils.isEmpty(text) ? View.GONE : View.VISIBLE);
        mTV_Right.setText(text);
        return this;
    }

    public TitleBuilder setLeftOnClickListener(View.OnClickListener listener) {
        if (mIV_Left.getVisibility() == View.VISIBLE) {
            mIV_Left.setOnClickListener(listener);
        } else if (mTV_Left.getVisibility() == View.VISIBLE) {
            mTV_Left.setOnClickListener(listener);
        }
        return this;
    }

    public TitleBuilder setTitleOnClickListener(View.OnClickListener listener) {
        if (mTV_Title.getVisibility() == View.VISIBLE) {
            mTV_Title.setOnClickListener(listener);
        }
        return this;
    }

    public TitleBuilder setRightOnClickListener(View.OnClickListener listener) {
        if (mIV_Right.getVisibility() == View.VISIBLE) {
            mIV_Right.setOnClickListener(listener);
        } else if (mTV_Right.getVisibility() == View.VISIBLE) {
            mTV_Right.setOnClickListener(listener);
        }
        return this;
    }

    public View build() {
        return mTitleBar;
    }
}
