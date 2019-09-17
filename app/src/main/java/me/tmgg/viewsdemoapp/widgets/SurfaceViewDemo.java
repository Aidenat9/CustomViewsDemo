package me.tmgg.viewsdemoapp.widgets;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import me.tmgg.viewsdemoapp.R;
import me.tmgg.viewsdemoapp.utils.Utils;

/**
 * @author sunwei
 * email：tianmu19@gmail.com
 * date：2019/9/17 9:58
 * package：me.tmgg.viewsdemoapp.widgets
 * version：1.0
 * <p>description：              </p>
 */
public class SurfaceViewDemo extends SurfaceView {
    private Paint paint;
    private boolean isStop;
    private int dp26;
    private int dp40;
    private int dp30;

    public SurfaceViewDemo(Context context) {
        super(context);
        init();
    }

    public SurfaceViewDemo(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SurfaceViewDemo(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        paint.setTextSize(Utils.dp2px(25));
        paint.setColor(Color.RED);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);
        SurfaceHolder holder = getHolder();
        holder.addCallback(new SurfaceHolder.Callback2() {
            @Override
            public void surfaceRedrawNeeded(SurfaceHolder holder) {

            }

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                isStop = false;
                dp26 = (int) Utils.dp2px(26);
                dp30 = (int) Utils.dp2px(30);
                dp40 = (int) Utils.dp2px(40);
                Canvas canvas1 = holder.lockCanvas();
                canvas1.drawColor(Color.WHITE, PorterDuff.Mode.CLEAR);
                holder.unlockCanvasAndPost(canvas1);
                if (!isStop) {
                    drawC(holder);
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                isStop = true;
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        if (event.getAction() == MotionEvent.ACTION_DOWN) {
//            drawC(getHolder());
//        }
        return super.onTouchEvent(event);
    }

    private void drawC(SurfaceHolder holder) {
        //挨个画数字
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Canvas canvas1 = holder.lockCanvas();
//                canvas1.drawColor(Color.BLUE);
//                holder.unlockCanvasAndPost(canvas1);
//
//                for (int i = 0; i < 10; i++) {
//                    Rect rect = new Rect(i*dp30,10,(i+1)*dp30,dp40);
//                    Canvas canvas = holder.lockCanvas(rect);
//                    canvas.drawColor(Color.BLUE);
//                    canvas.translate(Utils.dp2px(10),0);
//                    paint.setColor(Color.YELLOW);
//                    canvas.drawRect(rect,paint);
//                    paint.setColor(Color.RED);
//                    paint.getTextBounds(""+i,0,(""+i).length(),rect);
//                    canvas.drawText("" + i, 0, ("" + i).length(), (i) *dp30+ Utils.dp2px(10), (dp40-10)/2-(rect.bottom+rect.top)/2, paint);
//                    holder.unlockCanvasAndPost(canvas);
//                    try {
//                        Thread.sleep(800);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }).start();


        //画左右移动的图片

        new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap src = BitmapFactory.decodeResource(getResources(), R.drawable.test_demo);
                int width = src.getWidth();
                int height = src.getHeight();
                //等比放大图片，画出来
                int targetHeight = getHeight();
                int targetWidth = getHeight() / height * width;
                Bitmap scaledBitmap = Bitmap.createScaledBitmap(src, targetWidth, targetHeight, true);
                while (!isStop) {
                    Canvas canvas = holder.lockCanvas();
                    if (scrollOffset >= 0) {
                        state = ScrollState.LEFT;
                        //开始向左画
                    } else if (scrollOffset <= (width - targetWidth)) {
                        state = ScrollState.RIGHT;
                        //开始向右滑动
                    }
                    if(state==ScrollState.RIGHT){
                        scrollOffset+=STEP;
                    }else if(state==ScrollState.LEFT){
                        scrollOffset-=STEP;
                    }
                    //每次画的起点不同，默认0,0
                    canvas.drawBitmap(scaledBitmap, scrollOffset, 0, null);
                    holder.unlockCanvasAndPost(canvas);
                }
            }
        }).start();
    }

    private float scrollOffset = 0;
    private static final float STEP = 0.9f;
    public  int state = 0;
    private class ScrollState{
        private static final int LEFT = 0;
        private static final int RIGHT = 1;


    }
}
