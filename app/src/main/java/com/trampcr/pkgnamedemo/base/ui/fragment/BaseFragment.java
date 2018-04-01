package com.trampcr.pkgnamedemo.base.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trampcr.pkgnamedemo.base.ui.view.IViewInit;

/**
 * Created by trampcr on 2018/3/28.
 * base fragment
 */

public abstract class BaseFragment extends Fragment implements View.OnClickListener, IViewInit {
    private static final String TAG = BaseFragment.class.getSimpleName();
    protected View mRootView;
    protected Context mContext;

    protected boolean mIsVisible = false;
    // 视图是否准备好，用于防止视图可见，调用加载时视图未初始化造成空指针
    protected boolean mIsPrepared = false;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        init();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView == null && getContentViewResId() > 0) {
            mRootView = inflater.inflate(getContentViewResId(), container, false);
            if (mRootView.getParent() != null) {
                ((ViewGroup) mRootView.getParent()).removeView(mRootView);
            }

            initView();
            setPrepared(true);
            initListener();
            setViewsValue();
        }
        return mRootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        onClickDispatch(v);
    }

    public abstract void onClickDispatch(View v);

    /**
     * 获取视图
     *
     * @param resId 资源id
     * @param <T>
     * @return 视图
     */
    protected <T extends View> T findViewById(int resId) {
        if (mRootView == null || resId < 1) {
            return null;
        }
        return (T) mRootView.findViewById(resId);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            mIsVisible = true;
            onVisible();
        } else if (mIsVisible && !getUserVisibleHint()) {
            mIsVisible = false;
            onInvisible();
        }
    }

    public void setPrepared(boolean mIsPrepared) {
        this.mIsPrepared = mIsPrepared;
    }

    /**
     * 是否可以使用懒加载，调用懒加载前需要调用
     *
     * @return
     */
    protected boolean isCanLazyLoad() {
        return mIsVisible && mIsPrepared;
    }

    /**
     * 显示fragment
     */
    protected void onVisible() {
        if (isCanLazyLoad()) {
            lazyLoad();
        }
    }

    /**
     * 懒加载方法
     */
    protected abstract void lazyLoad();

    /**
     * 隐藏fragment
     */
    protected void onInvisible() {
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mIsVisible) {
            mIsVisible = false;
            onInvisible();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint()) {
            mIsVisible = true;
            onVisible();
        }
    }
}
