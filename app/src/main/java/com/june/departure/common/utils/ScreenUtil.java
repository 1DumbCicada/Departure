package com.june.departure.common.utils;

import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.june.departure.DepartureCache;

/**
 * Created by Maibenben on 2017/10/4.
 */

public class ScreenUtil {
    /**
     * 获取 屏幕宽
     */
    public static int getScreenWidth() {
        Resources resources = DepartureCache.getInstance().getContext().getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.widthPixels;
    }

    /**
     * 获取屏幕高
     * @return
     */
    public static int getScreenHeight() {
        Resources resources = DepartureCache.getInstance().getContext().getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.heightPixels;
    }

    /**
     * 获取状态栏高度
     * @return
     */
    public static int getStatusBarHeight() {
        int statusBarHeight = -1;
        //获取status_bar_height资源的ID
        int resourceId = DepartureCache.getInstance().getContext().getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight = DepartureCache.getInstance().getContext().getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }

    public static void setStatusBarColor(){

    }
}
