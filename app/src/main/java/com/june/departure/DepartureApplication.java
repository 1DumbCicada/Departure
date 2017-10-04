package com.june.departure;

import android.app.Activity;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

/**
 * Created by Maibenben on 2017/10/4.
 */

public class DepartureApplication extends Application {

    private int mActivityLifeCount = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        DepartureCache.getInstance().setContext(getApplicationContext());
        listenForActivityLifeCycle();
        listenForScreenTurningOff();
    }

    /**
     * 监听Activity生命周期回调
     *
     */
    private void listenForActivityLifeCycle() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle bundle) {

            }

            @Override
            public void onActivityStarted(Activity activity) {
                if (mActivityLifeCount == 0) {
                    //切到前台
                }
                mActivityLifeCount++;
            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {
                mActivityLifeCount--;
                if (mActivityLifeCount == 0) {
                    //切到后台
                }
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }

    /**
     * 息屏
     */
    private void listenForScreenTurningOff() {
        IntentFilter screenStateFilter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                //切换到后台
            }
        }, screenStateFilter);
    }


    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if (level == TRIM_MEMORY_UI_HIDDEN) {
            //切换到后台
        }

    }

}
