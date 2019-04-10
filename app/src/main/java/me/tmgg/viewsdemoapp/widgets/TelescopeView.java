package me.tmgg.viewsdemoapp.widgets;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import me.tmgg.viewsdemoapp.R;

/**
 * @author sunwei
 * email：tianmu19@gmail.com
 * date：2019/4/5 23:51
 * package：me.tmgg.viewsdemoapp.widgets
 * version：1.0
 * <p>description： 望远镜view             </p>
 */
public class TelescopeView extends View {

    private Paint mPaint;
    private Bitmap mBitmap;
    private int mDx;
    private int mDy;
    private BitmapShader mBitmapShader;

    public TelescopeView(Context context) {
        super(context);
        init();

    }

    public TelescopeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bg_demo);
        mBitmapShader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
    }

    public TelescopeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Matrix matrix = new Matrix();
        float scale = (float) getWidth() / mBitmap.getWidth();
        matrix.setScale(scale, scale);
        mBitmapShader.setLocalMatrix(matrix);
        mPaint.setShader(mBitmapShader);
        canvas.drawCircle(mDx, mDy, 150, mPaint);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDx = (int) event.getX();
                mDy = (int) event.getY();
//                postInvalidate();
                return true;
            case MotionEvent.ACTION_MOVE:
//                mDx = (int) event.getX();
//                mDy = (int) event.getY();
//                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mDx = (int) event.getX();
                mDy = (int) event.getY();
//                mDx = -1;
//                mDy = -1;
                postInvalidate();

                break;
            default:
                break;
        }
        postInvalidate();
        return true;
    }
}
