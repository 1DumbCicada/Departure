package com.june.departure;

import android.os.Bundle;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.june.departure.common.inflaters.DepCompDelegate;
import com.june.departure.common.widgets.DepTextView;
import com.june.thirdlib.ThirdTextView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private DepCompDelegate mDepCompDelegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LayoutInflaterCompat.setFactory(getLayoutInflater(), getDepCompDelegate());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (findViewById(R.id.tvGreet) instanceof DepTextView) {
            Log.i(TAG, "TextView被替换成DepTextView");
        }
        ThirdTextView thirdTV = new ThirdTextView(this);
    }

    private DepCompDelegate getDepCompDelegate() {
        if (mDepCompDelegate == null) {
            mDepCompDelegate = DepCompDelegate.create(this);
        }
        return mDepCompDelegate;
    }
}
