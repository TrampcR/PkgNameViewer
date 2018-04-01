package com.trampcr.pkgnamedemo.ui;

import android.support.v4.view.ViewPager;
import android.view.View;

import com.trampcr.pkgnamedemo.R;
import com.trampcr.pkgnamedemo.base.ui.activity.BaseActivity;
import com.trampcr.pkgnamedemo.ui.adapter.TabFragmentAdapter;
import com.trampcr.pkgnamedemo.util.StatusBarCompat;
import com.trampcr.pkgnamedemo.widget.tab.IOnTabListener;
import com.trampcr.pkgnamedemo.widget.tab.TabView;

/**
 * Created by trampcr on 2018/3/28.
 * pkg name viewer activity
 */

public class PkgNameViewerActivity extends BaseActivity {

    private static final String TAG = PkgNameViewerActivity.class.getSimpleName();

    private TabView mTabView;
    private ViewPager mViewPager;
    private StatusBarCompat mStatusBarCompat;
    private TabFragmentAdapter mViewPagerAdapter;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_pkgname_viewer;
    }

    @Override
    public void init() {

    }

    @Override
    public void initView() {
        mTabView = findViewById(R.id.tab_view);
        mViewPager = findViewById(R.id.view_pager);
        mViewPagerAdapter = new TabFragmentAdapter(getSupportFragmentManager());
//        initStatusBar();
    }

    @Override
    public void initListener() {
        mTabView.setOnTabListener(new IOnTabListener() {
            @Override
            public void onClick(View view, int curPos, int prePos) {
                mViewPager.setCurrentItem(curPos, false);
                mViewPagerAdapter.onClick(view, curPos, prePos);
            }

            @Override
            public void onDoubleClick(View view, int position) {
                mViewPager.setCurrentItem(position, false);
                mViewPagerAdapter.onDoubleClick(view, position);
            }
        });
    }

    @Override
    public void setViewsValue() {
        mTabView.setIconRsIds(getResources().obtainTypedArray(R.array.tab_item_icon_array_viewer));
        mTabView.setTitleRsIds(getResources().getStringArray(R.array.tab_item_text_array_viewer));
        mViewPager.setOffscreenPageLimit(TabFragmentAdapter.FRAGMENT_COUNT);
        mViewPager.setAdapter(mViewPagerAdapter);
    }

    @Override
    public void onClickDispatch(View v) {

    }

    private void initStatusBar() {
        if (null != mStatusBarCompat) {
            return;
        }

        mStatusBarCompat = new StatusBarCompat();
        StatusBarCompat.setStatusBarFullScreen(this);
    }
}
