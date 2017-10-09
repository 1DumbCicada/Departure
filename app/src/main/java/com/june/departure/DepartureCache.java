package com.june.departure;

import android.app.Activity;
import android.content.Context;

import java.lang.ref.WeakReference;

/**
 * Created by Maibenben on 2017/10/4.
 */

public class DepartureCache {

    private static DepartureCache mInstance;

    private static Context mContext;

    //处于前台的Activity弱引用
    private WeakReference<Activity> mActivityRefer;


    private DepartureCache() {

    }

    public static DepartureCache getInstance() {
        if (null == mInstance) {
            mInstance = new DepartureCache();
        }
        return mInstance;
    }

    public void setContext(Context context) {
        mContext = context;
    }

    public Context getContext() {
        return mContext;
    }

    public WeakReference<Activity> getActivityRefer() {
        return mActivityRefer;
    }

    public void setActivityRefer(Activity activity) {
        mActivityRefer = new WeakReference<Activity>(activity);
    }

}
