package com.june.thirdlib;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Maibenben on 2017/10/14.
 */

public class ThirdTextView extends AppCompatTextView {

    public ThirdTextView(Context context) {
        this(context, null);
    }

    public ThirdTextView(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.textViewStyle);
    }

    public ThirdTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
