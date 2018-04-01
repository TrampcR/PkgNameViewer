package com.trampcr.pkgnamedemo.base.ui.activity;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.trampcr.pkgnamedemo.base.ui.view.IViewInit;

/**
 * Created by trampcr on 2018/3/28.
 * base activity
 */

public abstract class BaseActivity extends FragmentActivity implements View.OnClickListener, IViewInit {
    protected Handler mHandler = new Handler();
    private static final String TAG = BaseActivity.class.getSimpleName();

    private int activityCloseEnterAnimation = 0;
    private int activityCloseExitAnimation = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getContentViewResId() > 0) {
            setContentView(getContentViewResId());
        }

        getActivityAnimation();
        init();
        initView();
        initListener();
        setViewsValue();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(activityCloseEnterAnimation, activityCloseExitAnimation);
    }

    @Override
    public final void onClick(final View v) {
        onClickDispatch(v);
    }

    private void getActivityAnimation() {
        TypedArray activityStyle = getTheme().obtainStyledAttributes(new int[]{android.R.attr.windowAnimationStyle});
        if (activityStyle != null) {
            int windowAnimationStyleResId = activityStyle.getResourceId(0, 0);
            activityStyle.recycle();
            activityStyle = getTheme().obtainStyledAttributes(windowAnimationStyleResId,
                    new int[]{android.R.attr.activityCloseEnterAnimation, android.R.attr.activityCloseExitAnimation});
            activityCloseEnterAnimation = activityStyle.getResourceId(0, 0);
            activityCloseExitAnimation = activityStyle.getResourceId(1, 0);
            activityStyle.recycle();
        }
    }

    public abstract void onClickDispatch(View v);
}
