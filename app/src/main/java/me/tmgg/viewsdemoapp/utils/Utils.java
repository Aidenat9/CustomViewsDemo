package me.tmgg.viewsdemoapp.utils;

import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;

/**
 * @author sunwei
 * email：tianmu19@gmail.com
 * date：2019/8/28 14:45
 * package：me.tmgg.viewsdemoapp.utils
 * version：1.0
 * <p>description：              </p>
 */
public class Utils {
    public static float dp2px(float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().getDisplayMetrics());
    }

    public static float getScreenHeight() {
        DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        return displayMetrics.heightPixels;
    }
}
