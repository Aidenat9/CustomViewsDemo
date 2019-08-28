package me.tmgg.viewsdemoapp.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import me.tmgg.viewsdemoapp.utils.Utils;

/**
 * @author sunwei
 * email：tianmu19@gmail.com
 * date：2019/8/28 11:49
 * package：me.tmgg.viewsdemoapp.widgets
 * version：1.0
 * <p>description：    简单仪表盘demo          </p>
 */
public class DashboardView extends View {
    private Paint pointerPaint;
    private Paint paint;
    private static final float RADIUS = Utils.dp2px(150);
    private static final int ANGLE = 120;
    private float HEIGHT_KEDU = Utils.dp2px(10);
    private float WIDTH_KEDU = Utils.dp2px(2);
    private static final float LENGTH = Utils.dp2px(100);
    //当前指针在第几刻度
    private int index = 2;
    Path dashPath = new Path();
    private RectF rectF;
    private int halfWidth;
    private int halfHeight;
    private PathDashPathEffect pathDashPathEffect;

    {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(Utils.dp2px(2));
        pointerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        pointerPaint.setColor(Color.RED);
        pointerPaint.setStrokeCap(Paint.Cap.ROUND);
        pointerPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        pointerPaint.setStrokeWidth(Utils.dp2px(2));
        setLayerType(LAYER_TYPE_SOFTWARE, paint);
        dashPath.addRect(new RectF(0, 0, Utils.dp2px(WIDTH_KEDU), HEIGHT_KEDU), Path.Direction.CW);
        //计算弧度的总长
        halfWidth = getWidth() / 2;
        halfHeight = getHeight() / 2;
        rectF = new RectF(halfWidth - RADIUS, halfHeight - RADIUS, halfWidth + RADIUS, halfHeight + RADIUS);
        Path arc = new Path();
        arc.addArc(rectF, 90 + ANGLE / 2, 360 - ANGLE);
        PathMeasure pathMeasure = new PathMeasure(arc, false);
        pathDashPathEffect = new PathDashPathEffect(dashPath, (pathMeasure.getLength() - WIDTH_KEDU) / 20, 0, PathDashPathEffect.Style.ROTATE);
    }

    public DashboardView(Context context) {
        super(context);
    }

    public DashboardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DashboardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.drawPoint(halfWidth, halfHeight, paint);
        //1.画弧度
        halfWidth = getWidth() / 2;
        halfHeight = getHeight() / 2;
        rectF = new RectF(halfWidth - RADIUS, halfHeight - RADIUS, halfWidth + RADIUS, halfHeight + RADIUS);
        canvas.drawArc(rectF, 90 + ANGLE / 2, 360 - ANGLE, false, paint);
        //2.画刻度
        paint.setPathEffect(pathDashPathEffect);
        canvas.drawArc(rectF, 90 + ANGLE / 2, 360 - ANGLE, false, paint);
        paint.setPathEffect(null);
        //3.画指针
        float endX = (float) (Math.cos(Math.toRadians(getAngleFromMark(index))) * LENGTH + halfWidth);
        float endY = (float) (Math.sin(Math.toRadians(getAngleFromMark(index))) * LENGTH + halfHeight);
        canvas.drawLine(halfWidth, halfHeight, endX, endY, pointerPaint);
        canvas.restore();
    }

    private int getAngleFromMark(int index) {
        return (int) (90 + (float) ANGLE / 2 + (360 - (float) ANGLE) / 20 * index);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }
}
