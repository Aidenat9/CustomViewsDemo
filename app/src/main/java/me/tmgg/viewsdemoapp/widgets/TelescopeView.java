package me.tmgg.viewsdemoapp.widgets;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import me.tmgg.viewsdemoapp.R;
import me.tmgg.viewsdemoapp.utils.Utils;

/**
 * @author sunwei
 * email：tianmu19@gmail.com
 * date：2019/4/5 23:51
 * package：me.tmgg.viewsdemoapp.widgets
 * version：1.0
 * <p>description： 望远镜view    3倍放大效果         </p>
 */
public class TelescopeView extends View {

    private int mDx;
    private int mDy;
    private Bitmap bitmap_canvas;
    private Matrix matrix = new Matrix();
    // 放大镜的半径
    private final static int RADIUS = (int) Utils.dp2px(75);
    // 放大倍数
    private static final int FACTOR = 3;
    private BitmapShader bitmapShader;
    private ShapeDrawable drawable;

    public TelescopeView(Context context) {
        super(context);
        init();

    }

    public TelescopeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setLayerType(LAYER_TYPE_SOFTWARE, null);
    }

    public TelescopeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (null == bitmap_canvas) {
            Bitmap bitmap_res = BitmapFactory.decodeResource(getResources(), R.drawable.bg_demo);
            //得到和画布一样大小的图形
            bitmap_canvas = Bitmap.createScaledBitmap(bitmap_res, getWidth(), getHeight(), false);
            //3倍的图形
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap_canvas, getWidth() * FACTOR, getHeight() * FACTOR, false);
            bitmapShader = new BitmapShader(scaledBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

            //画drawable
            drawable = new ShapeDrawable(new OvalShape());
            //半径
            drawable.setBounds(0, 0, RADIUS * 2, RADIUS * 2);
            drawable.setDither(true);
            drawable.getPaint().setShader(bitmapShader);
        }
        //画背景
        canvas.drawBitmap(bitmap_canvas, 0, 0, null);
        drawable.draw(canvas);
        //在触摸里 移动shader的位置
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDx = (int) event.getX();
                mDy = (int) event.getY();
                return true;
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mDx = (int) event.getX();
                mDy = (int) event.getY();
                break;
            default:
                break;
        }
        // 这个位置表示的是，画shader的起始位置
        matrix.setTranslate(RADIUS - mDx * FACTOR, RADIUS - mDy * FACTOR);
        drawable.getPaint().getShader().setLocalMatrix(matrix);
        //设置圆在触摸的位置
        drawable.setBounds(mDx - RADIUS, mDy - RADIUS, mDx + RADIUS, mDy + RADIUS);
        postInvalidate();
        return true;
    }
}
