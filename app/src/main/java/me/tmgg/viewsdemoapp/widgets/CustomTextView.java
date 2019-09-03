package me.tmgg.viewsdemoapp.widgets;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import me.tmgg.viewsdemoapp.utils.Utils;

/**
 * @author sunwei
 * email：tianmu19@gmail.com
 * date：2019/8/31 8:42
 * package：me.tmgg.viewsdemoapp.widgets
 * version：1.0
 * <p>description：   文字绘制示例--主要是找到文字的基线
 * 1.文字的矩形区域寻找理想基线位置，当文字变化时候会跳动高度
 * 2.通过FontMetics找到真正的基线(绝对准确)
 * </p>
 */
public class CustomTextView extends View {
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private String TEXT = "Steam";
    private String TEXT2 = "didi .xskymango";
    private Rect rect = new Rect();
    private final static float RADIUS = Utils.dp2px(85);
    {
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setColor(Color.parseColor("#B3B3B3"));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(Utils.dp2px(8));
    }
    public CustomTextView(Context context) {
        super(context);
    }

    public CustomTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth()/2;
        int height = getHeight()/2;
        canvas.drawPoint(width,height,paint);
        //1.画圆
        canvas.drawCircle(width,height,RADIUS,paint);
        //2.画弧度
        paint.setColor(Color.parseColor("#42B866"));
        canvas.drawArc(new RectF(width-RADIUS,height-RADIUS,width+RADIUS,height+RADIUS),-70,180,false,paint);

        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,30, Resources.getSystem().getDisplayMetrics()));
        paint.setTextAlign(Paint.Align.CENTER);
        //第一种画法，如果文字动态变化，会跳动高度
        paint.getTextBounds(TEXT,0,TEXT.length(),rect);
        float offset = (rect.bottom+rect.top)/2;
        //第二种正确方式画
        //3.画中间文字一行 大
//        Paint.FontMetrics fontMetrics = new Paint.FontMetrics();
//        paint.getFontMetrics(fontMetrics);
//        float offset = (fontMetrics.ascent+fontMetrics.descent)/2;
        paint.setColor(Color.parseColor("#634515"));
        canvas.drawText(TEXT,0,TEXT.length(),width,height-offset,paint);
        //4.画中间文字第二行 小
        paint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,16, Resources.getSystem().getDisplayMetrics()));
        canvas.drawText(TEXT2,0,TEXT2.length(),width,height+TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,38, Resources.getSystem().getDisplayMetrics())/2-offset,paint);

    }
}
