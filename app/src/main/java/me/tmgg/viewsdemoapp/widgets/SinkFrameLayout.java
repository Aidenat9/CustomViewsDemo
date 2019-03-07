package me.tmgg.viewsdemoapp.widgets;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

/**
 * @author sunwei
 * 邮箱：tianmu19@gmail.com
 * 时间：2019/3/7 13:55
 * 包名：me.tmgg.viewsdemoapp.widgets
 * <p>description:            </p>
 */
public class SinkFrameLayout extends FrameLayout {
    private final static int MARGIN_DP = 3;


    public SinkFrameLayout(@NonNull Context context) {
        super(context);
        init();
    }


    public SinkFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float scaleValueX = ((getWidth() - MARGIN_DP * getResources().getDisplayMetrics().density + 0.5f) / getWidth());
        float scaleValueY = ((getHeight() - MARGIN_DP * getResources().getDisplayMetrics().density + 0.5f) / getHeight());
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(this, "scaleX", getScaleX(), scaleValueX);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(this, "scaleY", getScaleY(), scaleValueY);
        ObjectAnimator scaleX2 = ObjectAnimator.ofFloat(this, "scaleX", getScaleX(), 1.0f);
        ObjectAnimator scaleY2 = ObjectAnimator.ofFloat(this, "scaleY", getScaleY(), 1.0f);
        scaleX.setTarget(this);
        scaleY.setTarget(this);
        scaleX2.setTarget(this);
        scaleY2.setTarget(this);
        scaleX.setDuration(200);
        scaleX2.setDuration(200);
        scaleY.setDuration(200);
        scaleY2.setDuration(200);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                scaleX.start();
                scaleY.start();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    setElevation(9.0f);
                }
                setAlpha(0.92f);
                break;
            case MotionEvent.ACTION_UP:
                scaleX2.start();
                scaleY2.start();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    setElevation(0.2f);
                }
                setAlpha(1.00f);
                break;
            default:
                break;
        }
        return true;
    }
}
