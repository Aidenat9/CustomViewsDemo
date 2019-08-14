package me.tmgg.viewsdemoapp.picpreview;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.provider.Settings.SettingNotFoundException;
import android.provider.Settings.System;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresPermission;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

import me.tmgg.viewsdemoapp.App;

public class ScreenUtil {
    private static Context getAppContext() {
        return App.getInstance().getApplicationContext();
    }

    public static Bitmap getBitmapFromView(View view) {
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache(true);
        Bitmap drawingCache = view.getDrawingCache();
        if (drawingCache == null) {
            return null;
        }
        Bitmap createBitmap = Bitmap.createBitmap(drawingCache);
        view.setDrawingCacheEnabled(false);
        return createBitmap;
    }

    public static int dip2px(float f) {
        return (int) ((getAppContext().getResources().getDisplayMetrics().density * f) + 0.5f);
    }

    public static int spToPx(float f) {
        return (int) TypedValue.applyDimension(2, f, getAppContext().getResources().getDisplayMetrics());
    }

    public static int getScreenWidth() {
        return getAppContext().getResources().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return getAppContext().getResources().getDisplayMetrics().heightPixels;
    }

    public static float getScreenRatio() {
        DisplayMetrics displayMetrics = getAppContext().getResources().getDisplayMetrics();
        return ((float) displayMetrics.heightPixels) / ((float) displayMetrics.widthPixels);
    }

    public static int getStatusBarHeight() {
        int identifier = getAppContext().getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (identifier > 0) {
            return getAppContext().getResources().getDimensionPixelSize(identifier);
        }
        return 0;
    }

    public static int getNavigationBarHeight() {
        Context appContext = getAppContext();
        boolean hasPermanentMenuKey = ViewConfiguration.get(appContext).hasPermanentMenuKey();
        int identifier = appContext.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        if (identifier <= 0 || hasPermanentMenuKey) {
            return 0;
        }
        return appContext.getResources().getDimensionPixelSize(identifier);
    }

    public static float getScreenDensity() {
        return getAppContext().getResources().getDisplayMetrics().density;
    }

    public static int getScreenDensityDpi() {
        return getAppContext().getResources().getDisplayMetrics().densityDpi;
    }

    public static void setFullScreen(@NonNull Activity activity) {
        activity.getWindow().addFlags(1536);
    }

    public static void setNonFullScreen(@NonNull Activity activity) {
        activity.getWindow().clearFlags(1536);
    }

    public static void toggleFullScreen(@NonNull Activity activity) {
        Window window = activity.getWindow();
        if ((window.getAttributes().flags & 1024) == 1024) {
            window.clearFlags(1536);
        } else {
            window.addFlags(1536);
        }
    }

    public static boolean isFullScreen(@NonNull Activity activity) {
        return (activity.getWindow().getAttributes().flags & 1024) == 1024;
    }

    public static void setLandscape(@NonNull Activity activity) {
        activity.setRequestedOrientation(0);
    }

    public static void setPortrait(@NonNull Activity activity) {
        activity.setRequestedOrientation(1);
    }

    public static boolean isLandscape() {
        return getAppContext().getResources().getConfiguration().orientation == 2;
    }

    public static boolean isPortrait() {
        return getAppContext().getResources().getConfiguration().orientation == 1;
    }

    public static int getScreenRotation(@NonNull Activity activity) {
        switch (activity.getWindowManager().getDefaultDisplay().getRotation()) {
            case 1:
                return 90;
            case 2:
                return 180;
            case 3:
                return SubsamplingScaleImageView.ORIENTATION_270;
            default:
                return 0;
        }
    }

    public static Bitmap screenShot(@NonNull Activity activity) {
        return screenShot(activity, false);
    }

    public static Bitmap screenShot(@NonNull Activity activity, boolean z) {
        Bitmap createBitmap;
        View decorView = activity.getWindow().getDecorView();
        decorView.setDrawingCacheEnabled(true);
        decorView.setWillNotCacheDrawing(false);
        Bitmap drawingCache = decorView.getDrawingCache();
        if (drawingCache == null) {
            return null;
        }
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        if (z) {
            Resources resources = activity.getResources();
            int dimensionPixelSize = resources.getDimensionPixelSize(resources.getIdentifier("status_bar_height", "dimen", "android"));
            createBitmap = Bitmap.createBitmap(drawingCache, 0, dimensionPixelSize, displayMetrics.widthPixels, displayMetrics.heightPixels - dimensionPixelSize);
        } else {
            createBitmap = Bitmap.createBitmap(drawingCache, 0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        }
        decorView.destroyDrawingCache();
        return createBitmap;
    }

    public static boolean isScreenLock() {
        KeyguardManager keyguardManager = (KeyguardManager) getAppContext().getSystemService("keyguard");
        return keyguardManager != null && keyguardManager.inKeyguardRestrictedInputMode();
    }

    @RequiresPermission("android.permission.WRITE_SETTINGS")
    public static void setSleepDuration(int i) {
        System.putInt(getAppContext().getContentResolver(), "screen_off_timeout", i);
    }

    public static int getSleepDuration() {
        try {
            return System.getInt(getAppContext().getContentResolver(), "screen_off_timeout");
        } catch (SettingNotFoundException e) {
            e.printStackTrace();
            return -123;
        }
    }

    public static boolean isTablet() {
        return (getAppContext().getResources().getConfiguration().screenLayout & 15) >= 3;
    }

    public static void adaptScreen4VerticalSlide(Activity activity, int i) {
        adaptScreen(activity, i, true);
    }

    public static void adaptScreen4HorizontalSlide(Activity activity, int i) {
        adaptScreen(activity, i, false);
    }

    private static void adaptScreen(Activity activity, int i, boolean z) {
        DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        DisplayMetrics displayMetrics2 = getAppContext().getResources().getDisplayMetrics();
        DisplayMetrics displayMetrics3 = activity.getResources().getDisplayMetrics();
        if (z) {
            displayMetrics3.density = ((float) displayMetrics3.widthPixels) / ((float) i);
        } else {
            displayMetrics3.density = ((float) displayMetrics3.heightPixels) / ((float) i);
        }
        displayMetrics3.scaledDensity = (displayMetrics.scaledDensity / displayMetrics.density) * displayMetrics3.density;
        displayMetrics3.densityDpi = (int) (160.0f * displayMetrics3.density);
        displayMetrics2.density = displayMetrics3.density;
        displayMetrics2.scaledDensity = displayMetrics3.scaledDensity;
        displayMetrics2.densityDpi = displayMetrics3.densityDpi;
    }

    public static void cancelAdaptScreen(Activity activity) {
        DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        DisplayMetrics displayMetrics2 = getAppContext().getResources().getDisplayMetrics();
        DisplayMetrics displayMetrics3 = activity.getResources().getDisplayMetrics();
        displayMetrics3.density = displayMetrics.density;
        displayMetrics3.scaledDensity = displayMetrics.scaledDensity;
        displayMetrics3.densityDpi = displayMetrics.densityDpi;
        displayMetrics2.density = displayMetrics.density;
        displayMetrics2.scaledDensity = displayMetrics.scaledDensity;
        displayMetrics2.densityDpi = displayMetrics.densityDpi;
    }

    public static boolean isAdaptScreen() {
        return Resources.getSystem().getDisplayMetrics().density != getAppContext().getResources().getDisplayMetrics().density;
    }
}