package me.tmgg.viewsdemoapp.widgets;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import me.tmgg.viewsdemoapp.DpUtils;
import me.tmgg.viewsdemoapp.R;


/**
 * @author ddc
 * 邮箱: 931952032@qq.com
 * <p>description:
 */
public class CreditMainView extends View {
    private int mwidth;
    private int mheight;

    private final float circle_y_scale = 0.42f;
    private final float circle_x_scale = 0.36f;
    private Paint mPaint;
    private Bitmap bg_bitmap;

    private final float angle_total = 210;//总分数 弧度
    private final float angle_start = 180;//起点
    private final float angle_average = 6f;//平均角度  210/ 35(线条的数量)

    private float my_point = 400;
    private final float totle_point = 500;
    private float scale_point;
    private int dp_1;
    private int dp_1_5;
    private int value;

    public CreditMainView(Context context) {
        super(context);
        init();
    }

    public CreditMainView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mwidth = MeasureSpec.getSize(widthMeasureSpec);
        mheight = MeasureSpec.getSize(heightMeasureSpec);
        if (bg_bitmap != null) {
            bg_bitmap = getNewBitmap(bg_bitmap, mwidth);
            mheight = bg_bitmap.getHeight();
        }
        setMeasuredDimension(mwidth, mheight);
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        dp_1_5 = (int) DpUtils.dp2px(getContext().getResources(), 2f);
        dp_1 = (int) DpUtils.dp2px(getContext().getResources(), 1f);
        bg_bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.bg_credit);

        ValueAnimator animator = ValueAnimator.ofInt(0, 34);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                value = (int) valueAnimator.getAnimatedValue();
//                  postInvalidate();

            }
        });
        animator.setDuration(10000);
        animator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (bg_bitmap != null) {
            canvas.drawBitmap(bg_bitmap, 0, 0, new Paint());
            canvas.restore();
        }
        drawCircles(canvas);
    }


    float mPadding = 90f;

    private void drawCircles(Canvas canvas) {
        float rotateY = (float) (getHeight()*circle_y_scale);
        canvas.translate(getWidth() / 2, mPadding);

        int xr = getWidth() / 2;
//        int xr = 0;
        canvas.rotate(-17 * 6, 0, rotateY);
        mPaint.setStrokeWidth(dp_1_5);
        int lineWidth = 30;
        for (int i = 0; i < 35; i++) {
            if (i == 20) {
                mPaint.setStrokeWidth(dp_1_5);
                mPaint.setColor(Color.RED);
                lineWidth = 35;
            } else {
                lineWidth = 20;
                mPaint.setColor(Color.BLACK);
                mPaint.setStrokeWidth(dp_1);
            }
            //起点 终点 ，画竖直的线，然后旋转得到圆弧（半径是旋转的坐标位置）
            canvas.drawLine(0, mPadding, 0, (mPadding - lineWidth), mPaint);
            canvas.rotate(6, 0, rotateY);//以新坐标系的点来旋转
        }

        Log.e(TAG, "drawCircles: " + value);
    }

    private static final String TAG = "tag";

    public Bitmap getNewBitmap(Bitmap bitmap, float newWidth) {
        // 获得图片的宽高.
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        // 计算缩放比例.
        float scale = newWidth / width;
        // 取得想要缩放的matrix参数.
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
        // 得到新的图片.
        Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        return newBitmap;
    }

}
