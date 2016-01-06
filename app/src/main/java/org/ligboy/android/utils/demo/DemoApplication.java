package org.ligboy.android.utils.demo;

import android.app.Application;
import android.content.res.Resources;

import org.ligboy.android.utils.LogUtil;

/**
 * @author Ligboy.Liu ligboy@gmail.com.
 */
public class DemoApplication extends Application {
    private static final String TAG = LogUtil.makeLogTag("Demo");
    @Override
    public void onCreate() {
        super.onCreate();
//        LogUtil.initialize(BuildConfig.DEBUG, "utils_");
        LogUtil.initialize(this, "utils_");
        Resources.Theme theme = getTheme();
        LogUtil.d(TAG, theme);
//        ResourcesUtil.getAttributeColor(this, R.attr.colorPrimary, R.color.colorPrimary, null);
    }
}
