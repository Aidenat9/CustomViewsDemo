package me.tmgg.viewsdemoapp.widgets;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * @author sunwei
 * email：tianmu19@gmail.com
 * date：2019/4/10 13:59
 * package：me.tmgg.viewsdemoapp.widgets
 * version：1.0
 * <p>description：  闪动文字            </p>
 */
public class FlashingTextView extends AppCompatTextView {
    private Paint mPaint;
    private LinearGradient mLinearGradient ;
    private float mEndX;

    public FlashingTextView(Context context) {
        super(context);
        init();
    }

    private void init() {
        setLayerType(LAYER_TYPE_SOFTWARE,mPaint);
        mPaint = getPaint();
        mPaint.setAntiAlias(true);
    }

    public FlashingTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FlashingTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        initAnim();
        initGradient();
    }

    @Override
    protected void onDraw(Canvas canvas) {
            Matrix matrix = new Matrix();
            matrix.setTranslate(mEndX,0);
            mLinearGradient.setLocalMatrix(matrix);
            mPaint.setShader(mLinearGradient);
            super.onDraw(canvas);
    }

    private void initGradient() {
        mLinearGradient = new LinearGradient(-getMeasuredWidth(),0,0,0,new int[]{getCurrentTextColor(),0xFFFDD835,getCurrentTextColor()},new float[]{0,0.5F,1.0F}, Shader.TileMode.CLAMP);
    }


    private void initAnim() {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0,  2*getMeasuredWidth());
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setRepeatMode(ValueAnimator.RESTART);
        valueAnimator.setDuration(2000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mEndX = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.start();
    }


}
