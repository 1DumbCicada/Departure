package com.june.departure.common.widgets;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import com.june.departure.common.strategies.Statistician;

/**
 * Created by Maibenben on 2017/10/9.
 */

public class DepTextView extends AppCompatTextView implements Statistician {

    private static final String TAG = DepTextView.class.getSimpleName();

    private String mStatistic;

    public DepTextView(Context context) {
        this(context, null);
    }

    public DepTextView(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.textViewStyle);
    }

    public DepTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public String getStatistic() {
        return mStatistic;
    }

    public void setStatistic(String statistic) {
        this.mStatistic = statistic;
    }

    @Override
    public void statistic() {
        Log.i(TAG, mStatistic);
    }
}
