package com.trampcr.pkgnamedemo.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.trampcr.pkgnamedemo.bean.PkgInfoBean;
import com.trampcr.pkgnamedemo.R;

import java.util.List;

/**
 * Created by trampcr on 2018/3/27.
 * my adapter
 */

public class ViewerListAdapter extends BaseAdapter {

    private Context mContext;
    private List<PkgInfoBean> mPkgInfoList;
    public ViewerListAdapter(Context context, List<PkgInfoBean> pkgInfoList) {
        if (null == context || null == pkgInfoList || pkgInfoList.size() == 0) {
            return;
        }

        mContext = context;
        mPkgInfoList = pkgInfoList;
    }

    @Override
    public int getCount() {
        return mPkgInfoList.size();
    }

    @Override
    public Object getItem(int i) {
        return mPkgInfoList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        View v;
        if (null == view) {
            viewHolder = new ViewHolder();
            v = LayoutInflater.from(mContext).inflate(R.layout.list_item_layout, null);

            viewHolder.listAppIcon = v.findViewById(R.id.image_icon);
            viewHolder.listType = v.findViewById(R.id.text_list_type);
            viewHolder.listVersion = v.findViewById(R.id.text_list_version);
            viewHolder.listAppName = v.findViewById(R.id.text_list_app_name);
            viewHolder.listPkgName = v.findViewById(R.id.text_list_pkg_name);
            v.setTag(viewHolder);
        } else {
            v = view;
            viewHolder = (ViewHolder) v.getTag();
        }

        PkgInfoBean pkgInfoBean = mPkgInfoList.get(i);
        if (null == pkgInfoBean) {
            return null;
        }

        viewHolder.listType.setText(pkgInfoBean.getType());
        viewHolder.listAppName.setText(pkgInfoBean.getAppName());
        viewHolder.listPkgName.setText(pkgInfoBean.getPkgName());
        viewHolder.listVersion.setText(pkgInfoBean.getVersionName());
        viewHolder.listAppIcon.setImageDrawable(pkgInfoBean.getAppIcon());

        return v;
    }

    private class ViewHolder{
        TextView listType;
        TextView listAppName;
        TextView listPkgName;
        TextView listVersion;
        ImageView listAppIcon;
    }
}
