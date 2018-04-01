package com.trampcr.pkgnamedemo.widget.tab;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;

/**
 * Created by trampcr on 2018/3/28.
 */

public class TabItemView extends RadioButton implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private IOnTabItemListener mOnTabListener = null;
    private GestureDetector mGestureDetector = null;
    private OnClickListener mOnClickListener = null;
    private View mView = this;

    public TabItemView(Context context) {
        this(context, null);
    }

    public TabItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mGestureDetector = new GestureDetector(getContext(), new ItemGestureListener());
        setOnCheckedChangeListener(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    public void setOnTabListener(IOnTabItemListener onTabListener) {
        this.mOnTabListener = onTabListener;
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        if (l == this) {
            super.setOnClickListener(l);
        } else {
            mOnClickListener = l;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (mOnTabListener != null && isChecked) {
            mOnTabListener.onViewClick(mView);
        }
    }

    private class ItemGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            if (mOnTabListener != null) {
                mOnTabListener.onSingleClick(mView);
            }
            return super.onSingleTapUp(e);
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            if (mOnTabListener != null) {
                mOnTabListener.onViewDoubleClick(mView);
            }
            return super.onDoubleTap(e);
        }
    }

    @Override
    public void onClick(View v) {

    }
}
