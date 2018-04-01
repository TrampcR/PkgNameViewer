package com.trampcr.pkgnamedemo.widget.tab;

import android.view.View;

/**
 * Created by trampcr on 2018/3/28.
 * tab listener
 */

public interface IOnTabListener {
    void onClick(View view, int curPos, int prePos);

    void onDoubleClick(View view, int pos);
}
