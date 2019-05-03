package com.ice.weather.utils;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.ice.weather.R;

/**
 * Created by Administrator on 2017/8/22.
 */

public class ThemeUtil {
    public ThemeUtil() {
    }

    public static void setTheme(@NonNull Activity activity) {
        boolean isLight = PrefsUtil.read(activity, "1", true);
        activity.setTheme(isLight ? R.style.AppTheme_Day : R.style.AppTheme_Night);
    }

    public static void reCreate(@NonNull final Activity activity) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                activity.recreate();
            }
        });

    }
}
