package me.tmgg.viewsdemoapp.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Build.VERSION;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

/**
 * 作为一个状态栏高度填充使用
 */
public class StatusBarView extends View {
    private int statusBarColor;
    private int statusBarHeight;

    public StatusBarView(Context context) {
        this(context, null);
    }

    public StatusBarView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public StatusBarView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.statusBarColor = ContextCompat.getColor(context, Color.BLACK);
        this.statusBarHeight = getStatusBarHeight(context);
    }

    public void setStatusBarColor(int i) {
        this.statusBarColor = i;
        invalidate();
    }

    public void setStatusBarHeight(int i) {
        this.statusBarHeight = i;
        getLayoutParams().height = i;
        requestLayout();
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        canvas.drawColor(this.statusBarColor);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        setMeasuredDimension(i, this.statusBarHeight);
    }

    private int getStatusBarHeight(Context context) {
        if (VERSION.SDK_INT < 21) {
            return 0;
        }
        int identifier = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (identifier > 0) {
            return context.getResources().getDimensionPixelSize(identifier);
        }
        return 0;
    }

    private int getNavigationBarHeight(Context context) {
        int identifier = context.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        if (identifier > 0) {
            return context.getResources().getDimensionPixelSize(identifier);
        }
        return 0;
    }
}