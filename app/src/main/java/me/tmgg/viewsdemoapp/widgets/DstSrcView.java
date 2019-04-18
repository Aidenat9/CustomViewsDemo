package me.tmgg.viewsdemoapp.widgets;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author sunwei
 * email：tianmu19@gmail.com
 * date：2019/4/11 12:00
 * package：me.tmgg.viewsdemoapp.widgets
 * version：1.0
 * <p>description：              </p>
 */
public class DstSrcView extends View {
    int width = 500;
    int height = 500;

    private Paint paint;

    public DstSrcView(Context context) {
        super(context);
        init();
    }

    public DstSrcView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DstSrcView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //初始化画笔
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //禁用硬件加速
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        //使用离屏绘制
        int layerID = canvas.saveLayer(0, 0, getWidth(), getHeight(), paint, Canvas.ALL_SAVE_FLAG);

        canvas.drawBitmap(createDstBigmap(width, height), 0, 0, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
        canvas.drawBitmap(createSrcBigmap(width, height), width / 2, height / 2, paint);
        paint.setXfermode(null);

        canvas.restoreToCount(layerID);

    }

    public Bitmap createDstBigmap(int width, int height) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint scrPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        scrPaint.setColor(0xFFFFCC44);
        canvas.drawCircle(width / 2, height / 2, width / 2, scrPaint);
        return bitmap;
    }

    public Bitmap createSrcBigmap(int width, int height) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint dstPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        dstPaint.setColor(0xFF66AAFF);
        canvas.drawRect(new Rect(0, 0, width, height), dstPaint);
        return bitmap;
    }
}
