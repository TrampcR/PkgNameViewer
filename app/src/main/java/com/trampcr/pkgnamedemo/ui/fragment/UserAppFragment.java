package com.trampcr.pkgnamedemo.ui.fragment;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.trampcr.pkgnamedemo.R;
import com.trampcr.pkgnamedemo.base.ui.fragment.BaseFragment;
import com.trampcr.pkgnamedemo.bean.PkgInfoBean;
import com.trampcr.pkgnamedemo.ui.adapter.ViewerListAdapter;
import com.trampcr.pkgnamedemo.util.PackageManagerWrapper;
import com.trampcr.pkgnamedemo.util.PackageUtils;
import com.trampcr.pkgnamedemo.util.VersionUtils;
import com.trampcr.pkgnamedemo.widget.dialog.PkgViewerDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by trampcr on 2018/3/28.
 * user app fragment
 */

public class UserAppFragment extends BaseFragment implements AdapterView.OnItemClickListener{

    private ListView mListView;
    private ViewerListAdapter mViewerListAdapter;
    private List<PkgInfoBean> mUserPkgInfoList;

    @Override
    public int getContentViewResId() {
        return R.layout.fragment_pkgname_viewer;
    }

    @Override
    public void init() {
        initPkgNameList();
    }

    @Override
    public void initView() {
        mListView = findViewById(R.id.list_view);
        mViewerListAdapter = new ViewerListAdapter(mContext, mUserPkgInfoList);
    }

    @Override
    public void initListener() {
        mListView.setOnItemClickListener(this);
    }

    @Override
    public void setViewsValue() {
        mListView.setAdapter(mViewerListAdapter);
    }

    @Override
    public void onClickDispatch(View v) {

    }

    @Override
    protected void lazyLoad() {

    }

    private void initPkgNameList() {
        PackageManagerWrapper.init(getContext());
        List<String> installedUserPkgNameList = PackageManagerWrapper.getInstalledUserPkgNameList();
        if (null == installedUserPkgNameList || installedUserPkgNameList.size() == 0) {
            return;
        }

        mUserPkgInfoList = new ArrayList<>();

        for (String pkgName : installedUserPkgNameList) {
            PkgInfoBean pkgInfoBean = new PkgInfoBean();

            String appName = PackageUtils.getAppName(mContext, pkgName);
            Drawable appIcon = PackageUtils.getAppIcon(mContext, pkgName);
            int versionCode = VersionUtils.getVersionCode(mContext, pkgName);
            String versionName = VersionUtils.getVersionName(mContext, pkgName);

            pkgInfoBean.setPkgName(pkgName);
            pkgInfoBean.setAppName(appName);
            pkgInfoBean.setAppIcon(appIcon);
            pkgInfoBean.setVersionName(versionName);
            pkgInfoBean.setVersionCode(versionCode);
            pkgInfoBean.setType(mContext.getString(R.string.user_app_type));

            mUserPkgInfoList.add(pkgInfoBean);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        PkgInfoBean pkgInfoBean = (PkgInfoBean) mViewerListAdapter.getItem(i);
        if (null == pkgInfoBean) {
            return;
        }

        PkgViewerDialog pkgViewerDialog = new PkgViewerDialog(mContext);
        pkgViewerDialog.showViewerDialog(pkgInfoBean);
    }
}
