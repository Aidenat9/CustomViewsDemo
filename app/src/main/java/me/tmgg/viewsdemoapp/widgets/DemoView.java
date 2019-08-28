package me.tmgg.viewsdemoapp.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author sunwei
 *         邮箱：tianmu19@gmail.com
 *         时间：2018/12/12 14:14
 *         包名：me.tmgg.viewsdemoapp.widgets
 *         <p>description:            </p>
 */

public class DemoView extends View {

    private Paint mPaint;

    public DemoView(Context context) {
        super(context);
        initPaint();
    }

    public DemoView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(15);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        // 省略了创建画笔的代码

// 在坐标原点绘制一个黑色圆形
//        mPaint.setColor(Color.BLACK);
//        canvas.translate(200,200);
//        canvas.drawCircle(0,0,100,mPaint);
//
//// 在坐标原点绘制一个蓝色圆形
//        mPaint.setColor(Color.BLUE);
//        canvas.translate(200,200);
//        canvas.drawCircle(0,0,100,mPaint);

        //scale
        // 将坐标系原点移动到画布正中心
//        int mWidth = getWidth();
//        int mHeight = getHeight();
//        canvas.translate(mWidth / 2, mHeight / 2);
//
        RectF rect = new RectF(0,-400,400,0);   // 矩形区域
//
        mPaint.setColor(Color.BLUE);           // 绘制黑色矩形
        canvas.drawRect(rect,mPaint);
//
//        canvas.scale(-0.5f,-0.5f,200,0);          // (先画布缩放再反转  <-- 缩放中心向右偏移了200个单位
//
//        mPaint.setColor(Color.BLUE);            // 绘制蓝色矩形
//        canvas.drawRect(rect,mPaint);
        //for scale
//        RectF rectF = new RectF(-400, -400, 400, 400);
//        for (int i = 0; i < 10; i++) {
//            canvas.scale(0.9F,0.9F);
//            canvas.drawRect(rectF,mPaint);
//        }

        //rotate
        // 将坐标系原点移动到画布正中心

//        RectF rect = new RectF(0,-400,400,0);   // 矩形区域
//
//        canvas.drawRect(rect,mPaint);
//
//        for (int i = 0; i < 30; i++) {
//
//            canvas.rotate(-15);                     // 旋转180度 <-- 默认旋转中心为原点
//
//            mPaint.setColor(Color.BLUE);            // 绘制蓝色矩形
//            canvas.drawRect(rect,mPaint);
//        }
        //2circles
        // 将坐标系原点移动到画布正中心
//        canvas.drawCircle(0,0,400,mPaint);          // 绘制两个圆形
//        canvas.drawCircle(0,0,350,mPaint);
//
//        for (int i=0; i<=360; i+=10){               // 绘制圆形之间的连接线
//            canvas.drawLine(0,350,0,420,mPaint);
//            canvas.rotate(10);
//        }

    }

    /**
     * 画子view
     * @param canvas
     */
    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
    }

    /**
     * api23引入，画滑动条及边缘渐变
     * @param canvas
     */
    @Override
    public void onDrawForeground(Canvas canvas) {
        super.onDrawForeground(canvas);
    }
}
