package org.ligboy.android.utils.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;

import org.ligboy.android.utils.LogUtil;
import org.ligboy.android.utils.TimeUtils;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = LogUtil.makeLogTag(MainActivity.class);
    private AppCompatTextView mTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mTextView = (AppCompatTextView) findViewById(R.id.textView);

        LogUtil.d(TAG, "onCreate");
        mTextView.setText(TimeUtils.formatDuration(this, 1951674000L));
    }
}
