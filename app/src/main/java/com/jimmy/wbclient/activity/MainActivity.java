package com.jimmy.wbclient.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.jimmy.wbclient.BaseActivity;
import com.jimmy.wbclient.R;
import com.jimmy.wbclient.fragment.FragmentSet;
import com.jimmy.wbclient.fragment.HomeFragment;
import com.jimmy.wbclient.fragment.MsgFragment;
import com.jimmy.wbclient.fragment.SearchFragment;
import com.jimmy.wbclient.fragment.UserFragment;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener,
        View.OnClickListener {

    private FragmentManager mFM;
    private FragmentSet mFrgSet;
    private RadioGroup mRG_Tab;
    private ImageView mIV_TabAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mFM = getSupportFragmentManager();
        mRG_Tab = (RadioGroup) findViewById(R.id.rg_tab);
        mIV_TabAdd = (ImageView) findViewById(R.id.iv_tab_add);

//        mFrgSet = FragmentSet.getFrgSet(this, R.id.fl_content);
//        mFrgSet.show(0);
        mFM.beginTransaction().replace(R.id.fl_content, new HomeFragment()).commit();

        mRG_Tab.setOnCheckedChangeListener(this);
        mIV_TabAdd.setOnClickListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        FragmentTransaction ft = mFM.beginTransaction();
        switch (checkedId) {
            case R.id.rb_tab_home:
//                mFrgSet.replace(0);
                ft.replace(R.id.fl_content, new HomeFragment());
                break;
            case R.id.rb_tab_msg:
//                mFrgSet.replace(1);
                ft.replace(R.id.fl_content, new MsgFragment());
                break;
            case R.id.rb_tab_search:
//                mFrgSet.replace(2);
                ft.replace(R.id.fl_content, new SearchFragment());
                break;
            case R.id.rb_tab_user:
//                mFrgSet.replace(3);
                ft.replace(R.id.fl_content, new UserFragment());
                break;
        }
        ft.commit();
    }

    @Override
    public void onClick(View v) {
    }
}





































