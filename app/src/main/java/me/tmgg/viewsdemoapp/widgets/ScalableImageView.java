package me.tmgg.viewsdemoapp.widgets;


import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.OverScroller;

import me.tmgg.viewsdemoapp.R;
import me.tmgg.viewsdemoapp.utils.Utils;

/**
 * @author sunwei
 * email：tianmu19@gmail.com
 * date：2019/10/25 8:53
 * package：me.tmgg.viewsdemoapp.widgets
 * version：1.0
 * <p>description：              </p>
 */
public class ScalableImageView extends View implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener, ScaleGestureDetector.OnScaleGestureListener {
    private Context mContext;
    private Bitmap bitmap;

    private float WIDTH = Utils.dp2px(200);
    private boolean big;
    private float bigScale;
    private float smallScale;
    private final float OVER_SCALE_FACTOR = 1.5f;
    private GestureDetectorCompat detector;
    //画图的时候的偏移量
    private float originalOffsetX;
    private float originalOffsetY;
    //动画变化量
    private float scaleFraction;
    private ObjectAnimator scaleAnimator;
    private float offsetX;
    private float offsetY;
    private OverScroller scroller;
    private ScaleGestureDetector scaleDetector;

    public ScalableImageView(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    public ScalableImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    public ScalableImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    public ScalableImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mContext = context;
        init();
    }


    private void init() {
        bitmap = Utils.getBitmap(mContext.getResources(), R.drawable.test_demo, (int) WIDTH);
        detector = new GestureDetectorCompat(mContext, this);
        scroller = new OverScroller(mContext);
        scaleDetector = new ScaleGestureDetector(mContext, this);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //图片的偏移量，为了图片居中
        originalOffsetX = ((float) getWidth() - bitmap.getWidth()) / 2;
        originalOffsetY = ((float) getHeight() - bitmap.getHeight()) / 2;
        //计算图片时宽、长图，得到放大到屏幕宽高时的比例
        if ((float) bitmap.getWidth() / bitmap.getHeight() > (float) getWidth() / getHeight()) {
            //宽大
            smallScale = (float) getWidth() / bitmap.getWidth();
            bigScale = (float) getHeight() / bitmap.getHeight() * OVER_SCALE_FACTOR;
        } else {
            bigScale = (float) getWidth() / bitmap.getWidth();
            smallScale = (float) getHeight() / bitmap.getHeight() * OVER_SCALE_FACTOR;
        }
        scaleFraction = smallScale;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float currentScale = getScaleFraction();
        //大图时手指移动，画布移动
        float fraction = (currentScale - smallScale) / (bigScale - smallScale);
        Log.d(TAG, "fraction: " + fraction);
        canvas.translate(offsetX * fraction, offsetY * fraction);
        //缩放画布为了展示大图
        canvas.scale(currentScale, currentScale, getWidth() / 2f, getHeight() / 2f);
        canvas.drawBitmap(bitmap, originalOffsetX, originalOffsetY, null);
    }

    public void setScaleFraction(float scaleFraction) {
        this.scaleFraction = scaleFraction;
        invalidate();
    }

    public float getScaleFraction() {
        return scaleFraction;
    }

    private ObjectAnimator getScaleAnimator() {
        if (null == scaleAnimator) {
            scaleAnimator = ObjectAnimator.ofFloat(this, "scaleFraction", smallScale, bigScale);
        }
        return scaleAnimator;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean b = scaleDetector.onTouchEvent(event);
        if (!scaleDetector.isInProgress()) {
            b = detector.onTouchEvent(event);
        }
        return b;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        if (big) {
            offsetX -= distanceX;
            offsetY -= distanceY;
            //偏移量不能超过边界
            fixOffset();
            invalidate();
        }
        return false;
    }

    private static final String TAG = "scalableImage:  ";

    private void fixOffset() {
        offsetX = Math.min(offsetX, ((float) bitmap.getWidth() * bigScale - getWidth()) / 2);
        offsetX = Math.max(offsetX, -((float) bitmap.getWidth() * bigScale - getWidth()) / 2);
        offsetY = Math.min(offsetY, ((float) bitmap.getHeight() * bigScale - getHeight()) / 2);
        offsetY = Math.max(offsetY, -((float) bitmap.getHeight() * bigScale - getHeight()) / 2);
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    /**
     * 惯性滑动 偏移量变化，重绘
     *
     * @param down
     * @param e2
     * @param velocityX
     * @param velocityY
     * @return
     */
    @Override
    public boolean onFling(MotionEvent down, MotionEvent e2, float velocityX, float velocityY) {
        if (big) {
            //最后2个参数是像ios的超过边界的距离
            scroller.fling((int) offsetX, (int) offsetY, (int) velocityX, (int) velocityY,
                    -(int) ((float) bitmap.getWidth() * bigScale - getWidth()) / 2,
                    (int) ((float) bitmap.getWidth() * bigScale - getWidth()) / 2,
                    -(int) ((float) bitmap.getHeight() * bigScale - getHeight()) / 2,
                    (int) ((float) bitmap.getHeight() * bigScale - getHeight()) / 2);
            postOnAnimation(new Runnable() {
                @Override
                public void run() {
                    //如果返回true，代表动画没结束，才需要继续执行
                    if (scroller.computeScrollOffset()) {
                        offsetX = scroller.getCurrX();
                        offsetY = scroller.getCurrY();
                        invalidate();
                        postOnAnimation(this);
                    }
                }
            });
        }
        return false;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    /**
     * 双击
     *
     * @param e
     * @return
     */
    @Override
    public boolean onDoubleTap(MotionEvent e) {
        //双击
        big = !big;
        if (big) {
            //点击的位置的偏移量，放大后
            offsetX = (e.getX() - getWidth() / 2f) - (e.getX() - getWidth() / 2) * bigScale / smallScale;
            offsetY = (e.getY() - getHeight() / 2f) - (e.getY() - getHeight() / 2) * bigScale / smallScale;
            fixOffset();
            getScaleAnimator().start();
        } else {
            getScaleAnimator().reverse();
        }
        return false;
    }

    /**
     * 双击全部事件
     *
     * @param e
     * @return
     */
    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }

    float initialScale;

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        scaleFraction = initialScale * detector.getScaleFactor();
        invalidate();
        return false;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        initialScale = getScaleFraction();
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {

    }
}
