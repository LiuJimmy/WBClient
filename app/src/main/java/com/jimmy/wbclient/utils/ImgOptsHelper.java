package com.jimmy.wbclient.utils;

import android.graphics.Bitmap;

import com.jimmy.wbclient.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

/**
 * Created by Administrator on 2016/10/9.
 */
public class ImgOptsHelper {

    public static DisplayImageOptions getImgOpts() {
        return new DisplayImageOptions.Builder()
                .cacheOnDisk(true)
                .cacheInMemory(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .showImageOnLoading(R.drawable.timeline_image_loading)
                .showImageForEmptyUri(R.drawable.timeline_image_loading)
                .showImageOnFail(R.drawable.timeline_image_failure).build();
    }
}
