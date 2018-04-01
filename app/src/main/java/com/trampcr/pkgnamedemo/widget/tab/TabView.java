package com.trampcr.pkgnamedemo.widget.tab;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.trampcr.pkgnamedemo.R;

/**
 * Created by trampcr on 2018/3/28.
 * tab view
 */

public class TabView extends RadioGroup {
    private TypedArray mIconRsIds = null;
    private String[] mTitleRsIds = null;

    private int mTextSize = 0;
    private int mTextDrawablePadding = 0;
    private LayoutParams mItemPararms = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);

    private IOnTabListener mOnTabListener;

    private int mCurrentSelectPosition = -1;
    private int mPreSelectPosition = -1;

    private boolean mIsInit = true;

    public TabView(Context context) {
        this(context, null);
    }

    public TabView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setOrientation(HORIZONTAL);
        mTextSize = getResources().getDimensionPixelSize(R.dimen.tab_item_text_size_default);
        mTextDrawablePadding = getResources().getDimensionPixelSize(R.dimen.tab_item_text_drawable_padding_default);

    }

    private synchronized void initLayout() {
        if (mTitleRsIds == null && mIconRsIds == null) {
            return;
        }

        int size = 0;
        if (mTitleRsIds == null) {
            size = mIconRsIds.length();
        } else if (mIconRsIds == null) {
            size = mTitleRsIds.length;
        } else {
            size = mTitleRsIds.length > mIconRsIds.length() ? mTitleRsIds.length : mIconRsIds.length();
        }

        // 如果有布局，以布局为准，没有布局则新增view
        if (getChildCount() > 0) {
            int position = 0;
            for (int childIndex = 0; childIndex < getChildCount(); childIndex++) {
                View childView = getChildAt(childIndex);
                if (!(childView instanceof TabItemView)) {
                    continue;
                }
                setTabItemView(position, size, (TabItemView) childView);
                position++;
            }
        } else {
            for (int position = 0; position < size; position++) {
                TabItemView itemView = createTabItemView();
                setTabItemView(position, size, itemView);
                addView(itemView, mItemPararms);
            }
        }

        setSelect(mCurrentSelectPosition >= 0 ? mCurrentSelectPosition : 0);
    }

    private void setTabItemView(int position, int size, TabItemView view) {
        setItemText(position, view);
        setItemIcon(position, view);
        setItemListener(position, view);
        setItemParams(size, view);
    }

    private TabItemView createTabItemView() {
        TabItemView itemView = (TabItemView) View.inflate(getContext(), R.layout.view_tab_item, null);
        return itemView;
    }

    private void setItemParams(int size, RadioButton rb) {
        mItemPararms.width = (int) ((float) getWidth() / size);
        mItemPararms.height = getHeight();
        mItemPararms.weight = 1;
        rb.setLayoutParams(mItemPararms);
    }

    private void setItemText(int position, RadioButton rb) {
        if (mTitleRsIds == null || mTitleRsIds.length <= position) {
            return;
        }
        rb.setText(mTitleRsIds[position]);
        rb.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
        rb.setGravity(Gravity.CENTER);
        rb.setButtonDrawable(null);
    }

    private void setItemIcon(int position, RadioButton rb) {
        if (mIconRsIds == null || mIconRsIds.length() <= position) {
            return;
        }

        int resId = mIconRsIds.getResourceId(position, 0);
        if (resId == 0) {
            return;
        }

        Drawable topDrawable = getResources().getDrawable(resId);
        topDrawable.setBounds(0, 0, topDrawable.getMinimumWidth(), topDrawable.getMinimumHeight());
        rb.setCompoundDrawables(null, topDrawable, null, null);
        rb.setCompoundDrawablePadding(mTextDrawablePadding);
    }

    private void setItemListener(final int position, final TabItemView rb) {
        rb.setOnTabListener(new IOnTabItemListener() {
            @Override
            public void onViewClick(View view) {
                setSelect(position, true);
            }

            @Override
            public void onSingleClick(View view) {
            }

            @Override
            public void onViewDoubleClick(View view) {
                setSelect(position, true);
                if (mOnTabListener != null) {
                    mOnTabListener.onDoubleClick(view, position);
                }
            }
        });
    }

    public void setIconRsIds(TypedArray tconRsIds) {
        this.mIconRsIds = tconRsIds;
        refreshLayout();
    }

    public void setTitleRsIds(String[] titleRsIds) {
        this.mTitleRsIds = titleRsIds;
        refreshLayout();
    }

    public void setTextSize(int textSize) {
        this.mTextSize = textSize;
        refreshLayout();
    }

    public void setOnTabListener(IOnTabListener onTabListener) {
        this.mOnTabListener = onTabListener;
    }

    private void refreshLayout() {
        post(new Runnable() {
            @Override
            public void run() {
                initLayout();
            }
        });
    }

    public void setSelect(int position) {
        setSelect(position, false);
    }

    public void setSelect(int position, boolean isClick) {
        updateSelectedByPosition(position, isClick);
    }

    private void updateSelectedByPosition(int position, boolean isClick) {
        int curPos = 0;
        for (int i = 0; i < getChildCount(); i++) {
            if (!(getChildAt(i) instanceof TabItemView)) {
                return;
            }
            if (curPos == position) {
                ((TabItemView) getChildAt(i)).setChecked(true);
            } else {
                ((TabItemView) getChildAt(i)).setChecked(false);
            }
            curPos++;
        }
        mCurrentSelectPosition = position;
        if (isClick) {
            notifyListener(null, mCurrentSelectPosition);
            mPreSelectPosition = mCurrentSelectPosition;
        }
    }

    private void notifyListener(View view, int position) {
        if (mOnTabListener != null) {
            mOnTabListener.onClick(view, position, mPreSelectPosition);
        }
    }
}
