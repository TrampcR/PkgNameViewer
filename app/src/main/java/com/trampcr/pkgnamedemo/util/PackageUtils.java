package com.trampcr.pkgnamedemo.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;

/**
 * Created by trampcr on 2018/3/27.
 * package utils
 */

public class PackageUtils {

    private static PackageManager getPackageManager(Context context) {
        if (null == context) {
            return null;
        }

        return context.getPackageManager();
    }

    public static PackageInfo getPackageInfo(Context context, String pkgName) {
        if (null == context || TextUtils.isEmpty(pkgName)) {
            return null;
        }

        PackageInfo packageInfo;

        try {
            PackageManager packageManager = getPackageManager(context);
            if (null == packageManager) {
                return null;
            }

            packageInfo = packageManager.getPackageInfo(pkgName, PackageManager.GET_CONFIGURATIONS);

            return packageInfo;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private static ApplicationInfo getApplicationInfo(Context context, String pkgName) {
        if (null == context || TextUtils.isEmpty(pkgName)) {
            return null;
        }

        try {
            PackageInfo pkgInfo = getPackageInfo(context, pkgName);
            if (pkgInfo != null) {
                return pkgInfo.applicationInfo;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String getAppName(Context context, String pkgName) {
        if (null == context || TextUtils.isEmpty(pkgName)) {
            return null;
        }

        String label;
        ApplicationInfo applicationInfo = getApplicationInfo(context, pkgName);
        if (applicationInfo != null) {
            PackageManager packageManager = getPackageManager(context);
            if (null == packageManager) {
                return null;
            }

            label = packageManager.getApplicationLabel(applicationInfo).toString();
        } else {
            label = pkgName;
        }

        return label;
    }

    public static Drawable getAppIcon(Context context, String pkgName) {
        if (null == context || TextUtils.isEmpty(pkgName)) {
            return null;
        }

        Drawable appIcon = null;
        PackageManager packageManager = getPackageManager(context);
        if (null == packageManager) {
            return null;
        }

        try {
            appIcon = packageManager.getApplicationIcon(pkgName);
        } catch (Exception | Error e) {
            e.printStackTrace();
        }

        return appIcon;
    }
}
