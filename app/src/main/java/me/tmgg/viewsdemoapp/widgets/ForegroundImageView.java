package me.tmgg.viewsdemoapp.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * @author sunwei
 * email：tianmu19@gmail.com
 * date：2019/10/21 14:15
 * version：1.0
 * <p>description：前景色图片，点击展示前景色   </p>
 */

public class ForegroundImageView extends ImageView {
    public ForegroundImageView(Context context) {
        this(context, null);
    }

    public ForegroundImageView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ForegroundImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        setScaleType(ScaleType.CENTER_CROP);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isPressed()) {
            canvas.drawColor(Color.parseColor("#8000FFFF"));
        }
    }

    public void dispatchSetPressed(boolean z) {
        super.dispatchSetPressed(z);
        invalidate();
    }
}