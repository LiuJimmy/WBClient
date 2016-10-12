package com.jimmy.wbclient.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.jimmy.wbclient.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/10/9.
 */
public class StatusGridImgsAdapter extends BaseAdapter {
    private ArrayList<String> mPicUrls;
    private Context mContext;
    private ImageLoader mImageLoader;
    private GridView mGridView;

    public StatusGridImgsAdapter(ArrayList<String> picUrls, Context context, GridView gridView) {
        mPicUrls = picUrls;
        mContext = context;
        mImageLoader = ImageLoader.getInstance();
        mGridView = gridView;
    }

    @Override
    public int getCount() {
        return mPicUrls.size();
    }

    @Override
    public String getItem(int position) {
        return mPicUrls.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_grid_image, null);
            holder = new ViewHolder();
            holder.mImageView = (ImageView) convertView.findViewById(R.id.iv_image);
//            holder.mDeletView = (ImageView) convertView.findViewById(R.id.iv_delete_image);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        int horizontalSpacing = mGridView.getHorizontalSpacing();//GirdView的水平宫格间距
        /**
         * 每张图片的宽度
         */
        int width = (mGridView.getWidth() - horizontalSpacing * 2 - mGridView.getPaddingLeft() -
                mGridView.getPaddingRight()) / 3;
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, width);
        holder.mImageView.setLayoutParams(params);//设置每张图片的显示大小

        String picUrl = getItem(position);
        mImageLoader.displayImage(picUrl, holder.mImageView);

        return convertView;
    }

    class ViewHolder {
        ImageView mImageView;
        ImageView mDeletView;
    }


}
