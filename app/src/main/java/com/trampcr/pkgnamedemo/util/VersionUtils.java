package com.trampcr.pkgnamedemo.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.text.TextUtils;

/**
 * Created by trampcr on 2018/3/28.
 * version utils
 */

public class VersionUtils {
    public static final String TAG = VersionUtils.class.getSimpleName();

    public static String getVersionName(Context context, String pkgName) {
        if (null == context || TextUtils.isEmpty(pkgName)) {
            return null;
        }

        PackageInfo packageInfo = PackageUtils.getPackageInfo(context, pkgName);
        if (null == packageInfo) {
            return null;
        }

        return packageInfo.versionName;
    }

    public static int getVersionCode(Context context, String pkgName) {
        if (null == context || TextUtils.isEmpty(pkgName)) {
            return 0;
        }

        PackageInfo packageInfo = PackageUtils.getPackageInfo(context, pkgName);
        if (null == packageInfo) {
            return 0;
        }

        return packageInfo.versionCode;
    }
}
