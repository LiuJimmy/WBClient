package com.jimmy.wbclient.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.jimmy.wbclient.BaseActivity;
import com.jimmy.wbclient.R;
import com.jimmy.wbclient.fragment.ImgBroFragment;
import com.jimmy.wbclient.utils.PicUtil;

import java.util.ArrayList;

public class ImageBrowserActivity extends BaseActivity {
    public static final String KEY_IMAGE_URIS = "com.jimmy.wbclient.activity.image_uris";
    public static final String KEY_CUR_INDEX = "com.jimmy.wbclient.activity.cur_index";
    private TextView mTVImgIndex;
    private ViewPager mViewPager;
    private ArrayList<String> mOrginalPicUrls;
    private int mCurIndex;

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_browser);

        mTVImgIndex = (TextView) findViewById(R.id.tv_image_top_index);
        mViewPager = (ViewPager) findViewById(R.id.vp_image_brower);

        ArrayList<String> picUrls = getIntent().getStringArrayListExtra(KEY_IMAGE_URIS);
        mOrginalPicUrls = PicUtil.getOrigUtil(picUrls);//解析原始图片地址
        mCurIndex = getIntent().getIntExtra(KEY_CUR_INDEX, 0);

        mTVImgIndex.setVisibility(mOrginalPicUrls.size() > 1 ? View.VISIBLE : View.GONE);
        mTVImgIndex.setText(mCurIndex + 1 + "/" + mOrginalPicUrls.size());

        FragmentManager fm = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public Fragment getItem(int position) {
                return ImgBroFragment.newInstance(mOrginalPicUrls.get(position));
            }

            @Override
            public int getCount() {
                return mOrginalPicUrls.size();
            }
        });

        mViewPager.setCurrentItem(mCurIndex);

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int
                    positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mTVImgIndex.setText(position + 1 + "/" + mOrginalPicUrls.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

    }
}
