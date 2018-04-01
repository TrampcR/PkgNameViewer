package com.trampcr.pkgnamedemo.widget.tab;

import android.view.View;

/**
 * Created by trampcr on 2018/3/28.
 * tab item listener
 */

public interface IOnTabItemListener {
    void onViewClick(View view);

    void onViewDoubleClick(View view);

    void onSingleClick(View view);
}
