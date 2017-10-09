package com.june.departure.common.utils;

import android.content.Intent;
import android.net.Uri;

import com.june.departure.DepartureCache;

import java.io.File;

/**
 * Created by Maibenben on 2017/10/4.
 */

public class InstallApkUtil {
    /**
     * 安装apk文件
     */
    public static void installApk(String filepath) {
        DepartureCache.getInstance().getContext().startActivity(getInstallApkIntent(filepath));
    }

    /**
     * 安装apk文件
     */
    public static Intent getInstallApkIntent(String filepath) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        File file = new File(filepath);
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        return intent;
    }

}
