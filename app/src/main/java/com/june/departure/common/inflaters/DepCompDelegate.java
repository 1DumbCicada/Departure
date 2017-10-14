package com.june.departure.common.inflaters;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.view.LayoutInflaterFactory;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.VectorEnabledTintResources;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewParent;

/**
 * Created by Maibenben on 2017/10/11.
 */

public class DepCompDelegate implements LayoutInflaterFactory {
    private final AppCompatActivity mAppCompatActivity;
    private DepViewInflater mDepViewInflater;

    private DepCompDelegate(AppCompatActivity appCompatActivity) {
        mAppCompatActivity = appCompatActivity;
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        View view = createView(parent, name, context, attrs);

        if (view == null) {
            return null;
        }
//        if (view instanceof SkinCompatSupportable) {
//            mSkinHelpers.add(new WeakReference<SkinCompatSupportable>((SkinCompatSupportable) view));
//        }

        return view;
    }

    public View createView(View parent, final String name, @NonNull Context context,
                           @NonNull AttributeSet attrs) {
        final boolean isPre21 = Build.VERSION.SDK_INT < 21;

        if (mDepViewInflater == null) {
            mDepViewInflater = new DepViewInflater();
        }

        // We only want the View to inherit its context if we're running pre-v21
        final boolean inheritContext = isPre21 && shouldInheritContext((ViewParent) parent);

        return mDepViewInflater.createView(parent, name, context, attrs, inheritContext,
                isPre21, /* Only read android:theme pre-L (L+ handles this anyway) */
                true, /* Read read app:theme as a fallback at all times for legacy reasons */
                VectorEnabledTintResources.shouldBeUsed() /* Only tint wrap the context if enabled */
        );
    }

    private boolean shouldInheritContext(ViewParent parent) {
        if (parent == null) {
            // The initial parent is null so just return false
            return false;
        }
        final View windowDecor = mAppCompatActivity.getWindow().getDecorView();
        while (true) {
            if (parent == null) {
                // Bingo. We've hit a view which has a null parent before being terminated from
                // the loop. This is (most probably) because it's the root view in an inflation
                // call, therefore we should inherit. This works as the inflated layout is only
                // added to the hierarchy at the end of the inflate() call.
                return true;
            } else if (parent == windowDecor || !(parent instanceof View)
                    || ViewCompat.isAttachedToWindow((View) parent)) {
                // We have either hit the window's decor view, a parent which isn't a View
                // (i.e. ViewRootImpl), or an attached view, so we know that the original parent
                // is currently added to the view hierarchy. This means that it has not be
                // inflated in the current inflate() call and we should not inherit the context.
                return false;
            }
            parent = parent.getParent();
        }
    }

    public static DepCompDelegate create(AppCompatActivity appCompatActivity) {
        return new DepCompDelegate(appCompatActivity);
    }

//    public void applySkin() {
//        if (mSkinHelpers != null && !mSkinHelpers.isEmpty()) {
//            SkinLog.d("size - " + mSkinHelpers.size());
//            for (WeakReference ref : mSkinHelpers) {
//                if (ref != null && ref.get() != null) {
//                    ((SkinCompatSupportable) ref.get()).applySkin();
//                }
//            }
//        }
//    }
}
