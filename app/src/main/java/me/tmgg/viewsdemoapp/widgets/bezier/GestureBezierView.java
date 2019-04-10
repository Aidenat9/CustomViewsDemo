package me.tmgg.viewsdemoapp.widgets.bezier;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * @author sunwei
 * 邮箱：tianmu19@gmail.com
 * 时间：2019/4/5 22:07
 * 包名：me.tmgg.viewsdemoapp.widgets.bezier
 * <p>description:            </p>
 */
public class GestureBezierView extends View {

    private Paint paint;
    private Path path;

    public GestureBezierView(Context context) {
        super(context);
        init();
    }

    public GestureBezierView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);        init();

    }

    public GestureBezierView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);        init();

    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        path = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
        canvas.drawPath(path,paint);
    }

    /**
     * 前一个点，作为控制点
     */
   private float mPreX ,mPreY;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(event.getX(),event.getY());
                mPreX = event.getX();
                mPreY = event.getY();
                return true;
            case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP:
                    float endX = (event.getX()+mPreX)/2;
                    float endY = (event.getY()+mPreY)/2;
                path.quadTo(mPreX,mPreY,endX,endY);
                    mPreX = event.getX();
                    mPreY = event.getY();
                break;
                default:
                    break;
        }


        return super.onTouchEvent(event);
    }
}
