package com.jimmy.wbclient.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.jimmy.wbclient.BaseFragment;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/10/9.
 */
public class FragmentSet {
    private static FragmentSet sFrgSet;
    private FragmentManager mFM;
    private int mContainerId;
    private ArrayList<BaseFragment> mFragList;

    public static FragmentSet getFrgSet(AppCompatActivity activity, int containerId) {
        if (sFrgSet == null) {
            sFrgSet = new FragmentSet(activity, containerId);
        }
        return sFrgSet;
    }

    private FragmentSet(AppCompatActivity activity, int containerId) {
        mFM = activity.getSupportFragmentManager();
        mContainerId = containerId;

        mFragList = new ArrayList<>();
        mFragList.add(new HomeFragment());
        mFragList.add(new MsgFragment());
        mFragList.add(new SearchFragment());
        mFragList.add(new UserFragment());

        FragmentTransaction ft = mFM.beginTransaction();
        for (Fragment fragment : mFragList) {
            ft.add(mContainerId, fragment);
        }
        ft.commit();
    }

    public Fragment getFragment(int index) {
        return mFragList.get(index);
    }

    public void show(int index) {
        FragmentTransaction ft = mFM.beginTransaction();
        for (Fragment fragment : mFragList) {
            if (fragment != null) {
                ft.hide(fragment);
            }
        }
        ft.show(mFragList.get(index)).commit();
    }

    public void replace(int index) {
        BaseFragment fragment = (BaseFragment) mFM.findFragmentById(mContainerId);
        if (!fragment.equals(mFragList.get(index))) {
            mFM.beginTransaction().replace(mContainerId, mFragList.get(index))
                    .commitAllowingStateLoss();
        }
    }


}
