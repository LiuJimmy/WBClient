package com.jimmy.wbclient;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.jimmy.wbclient.activity.MainActivity;
import com.jimmy.wbclient.api.MyStatusesAPI;
import com.jimmy.wbclient.utils.DialogUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by Administrator on 2016/10/9.
 */
public class BaseFragment extends Fragment {
    protected MainActivity mActivity;
    protected MyStatusesAPI mMyStaAPI;
    protected Dialog mProDia;
    protected ImageLoader mImageLoader;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivity = (MainActivity) getActivity();
        mMyStaAPI = new MyStatusesAPI(mActivity);
        mProDia = DialogUtil.createLoadingDialog(mActivity);
        mImageLoader = ImageLoader.getInstance();
    }

    protected void intent2Activity(Class<? extends Activity> tarActivity) {
        startActivity(new Intent(mActivity, tarActivity));
    }
}
