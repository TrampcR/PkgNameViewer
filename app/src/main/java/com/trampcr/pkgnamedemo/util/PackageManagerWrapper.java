package com.trampcr.pkgnamedemo.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by trampcr on 2018/3/27.
 * package manager wrapper
 */

public class PackageManagerWrapper {

    private static Context mContext;

    public static synchronized void init(Context context) {
        mContext = context;
    }

    public static List<String> getInstalledSystemPkgNameList() {
        List<PackageInfo> pkgInfoList = getInstalledSystemPkgInfoList();
        if (null == pkgInfoList || pkgInfoList.size() == 0) {
            return null;
        }

        List<String> result = new ArrayList<>();

        for (PackageInfo packageInfo : pkgInfoList) {
            result.add(packageInfo.packageName);
        }

        return result;
    }

    public static List<String> getInstalledUserPkgNameList() {
        List<PackageInfo> pkgInfoList = getInstalledUserPkgInfoList();
        if (null == pkgInfoList || pkgInfoList.size() == 0) {
            return null;
        }

        List<String> result = new ArrayList<>();

        for (PackageInfo packageInfo : pkgInfoList) {
            result.add(packageInfo.packageName);
        }

        return result;
    }

    public static List<PackageInfo> getInstalledUserPkgInfoList() {
        List<PackageInfo> pkgInfoList = getInstalledPkgInfoList();
        if (null == pkgInfoList) {
            return null;
        }

        List<PackageInfo> result = new ArrayList<>();

        for (PackageInfo packageInfo : pkgInfoList) {
            if (null == packageInfo) {
                break;
            }
            if (!isSystemApp(packageInfo)) {
                result.add(packageInfo);
            }
        }

        return result;
    }

    public static List<PackageInfo> getInstalledSystemPkgInfoList() {
        List<PackageInfo> pkgInfoList = getInstalledPkgInfoList();
        if (null == pkgInfoList) {
            return null;
        }

        List<PackageInfo> result = new ArrayList<>();

        for (PackageInfo packageInfo : pkgInfoList) {
            if (null == packageInfo) {
                break;
            }
            if (isSystemApp(packageInfo)) {
                result.add(packageInfo);
            }
        }

        return result;
    }

    public static List<String> getInstalledPkgNameList() {
        List<PackageInfo> pkgInfoList = getInstalledPkgInfoList();
        if (null == pkgInfoList || pkgInfoList.size() == 0) {
            return null;
        }

        List<String> result = new ArrayList<>();

        for (PackageInfo packageInfo : pkgInfoList) {
            result.add(packageInfo.packageName);
        }

        return result;
    }

    public static List<PackageInfo> getInstalledPkgInfoList() {
        PackageManager packageManager = mContext.getPackageManager();

        return packageManager.getInstalledPackages(0);
    }

    public static boolean isSystemApp(PackageInfo packageInfo) {
        return packageInfo != null && packageInfo.applicationInfo != null
                && ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0
                || (packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0);
    }
}
