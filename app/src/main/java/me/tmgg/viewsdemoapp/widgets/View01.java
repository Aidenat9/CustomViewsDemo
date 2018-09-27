package me.tmgg.viewsdemoapp.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author sunwei
 *         邮箱：tianmu19@gmail.com
 *         时间：2018/9/27 17:15
 *         包名：me.tmgg.viewsdemoapp.widgets
 *         <p>description:            </p>
 */

public class View01 extends View{

    private Paint paint;

    public View01(Context context) {
        super(context);
        initPaint();
    }

    public View01(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public View01(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
        paint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.LTGRAY);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
//        canvas.drawLine(10,10,300,300,paint);
//        canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher),
//                50,50,paint);//paint 的color不起作用
        paint.setColor(Color.parseColor("2E8772"));
        canvas.drawRect(new Rect(30,30,230,180),paint);
        paint.setColor(Color.parseColor("CB8B04"));
        canvas.drawLine(300,30,450,180,paint);
        paint.setColor(Color.RED);
        canvas.drawText("画文字",490,30,paint);



    }


}
