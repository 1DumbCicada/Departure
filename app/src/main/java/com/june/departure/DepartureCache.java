package com.june.departure;

import android.content.Context;

/**
 * Created by Maibenben on 2017/10/4.
 */

public class DepartureCache {

    private static DepartureCache mInstance;

    private static Context mApplicationContext;

    private DepartureCache() {

    }

    public static DepartureCache getInstance() {
        if (null == mInstance) {
            mInstance = new DepartureCache();
        }
        return mInstance;
    }

    public void setContext(Context context) {
        mApplicationContext = context;
    }

    public Context getContext() {
        return mApplicationContext;
    }
}
