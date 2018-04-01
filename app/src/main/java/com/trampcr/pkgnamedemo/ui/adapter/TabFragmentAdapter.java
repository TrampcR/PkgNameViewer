package com.trampcr.pkgnamedemo.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;

import com.trampcr.pkgnamedemo.ui.fragment.AllAppFragment;
import com.trampcr.pkgnamedemo.ui.fragment.SystemAppFragment;
import com.trampcr.pkgnamedemo.ui.fragment.UserAppFragment;
import com.trampcr.pkgnamedemo.widget.tab.IOnTabListener;

/**
 * Created by trampcr on 2018/3/28.
 * main tab fragment adapter
 */

public class TabFragmentAdapter extends FragmentPagerAdapter implements IOnTabListener{

    public static int FRAGMENT_COUNT = 3;

    public static final int ITEM_POSITION_HOME = 0;
    public static final int ITEM_POSITION_NEWS = 1;
    public static final int ITEM_POSITION_ME = 2;

    private AllAppFragment mAllAppFragment;
    private UserAppFragment mUserAppFragment;
    private SystemAppFragment mSystemAppFragment;

    public TabFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case ITEM_POSITION_HOME:
                // 第一个fragment不由懒加载控制，直接调用setViewsValue
                if (mAllAppFragment == null) {
                    mAllAppFragment = new AllAppFragment();
                }
                fragment = mAllAppFragment;
                break;
            case ITEM_POSITION_NEWS:
                if (mUserAppFragment == null) {
                    mUserAppFragment = new UserAppFragment();
                }
                fragment = mUserAppFragment;
                break;
            case ITEM_POSITION_ME:
                if (mSystemAppFragment == null) {
                    mSystemAppFragment = new SystemAppFragment();
                }
                fragment = mSystemAppFragment;
                break;
            default:
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return FRAGMENT_COUNT;
    }

    @Override
    public void onClick(View view, int curPos, int prePos) {

    }

    @Override
    public void onDoubleClick(View view, int pos) {

    }
}
