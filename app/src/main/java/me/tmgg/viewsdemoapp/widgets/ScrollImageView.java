package me.tmgg.viewsdemoapp.widgets;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import me.tmgg.viewsdemoapp.R;

/**
 * @author sunwei
 * email：tianmu19@gmail.com
 * date：2019/10/20 22:05
 * package：me.tmgg.viewsdemoapp.widgets
 * version：1.0
 * <p>description：    上下滚动图片          </p>
 */
public class ScrollImageView extends ImageView {

    private int width;
    private Bitmap scaledBitmap;
    private int targetHeight;

    public ScrollImageView(Context context) {
        super(context);
        init();
    }

    public ScrollImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ScrollImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

    }

    float scrollOffset =0 ;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(0==width||null==scaledBitmap){
            width = getWidth();
            Bitmap src = BitmapFactory.decodeResource(getResources(), R.drawable.long_bg);
            int widthsrc = src.getWidth();
            int heightsrc = src.getHeight();
            targetHeight = width/widthsrc*heightsrc;
            scaledBitmap = Bitmap.createScaledBitmap(src, width, targetHeight, true);
        }
        if(scrollOffset>0||scrollOffset<getHeight()-targetHeight){
            //临界值，不做处理
        }else{
            canvas.drawBitmap(scaledBitmap,0,scrollOffset,null);
        }
    }

    private static final String TAG = "scrollImage";

    public void setScrollOffset(float percent) {
        //根据百分比 算出滑动的开始
        float scrollHeight = percent * targetHeight;
        this.scrollOffset = -scrollHeight;
        Log.d(TAG,"percent: "+percent);
        if(percent<0||percent>1){
            //临界值，不做处理
        }else{
            invalidate();
        }
    }
}
