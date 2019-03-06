package me.tmgg.viewsdemoapp.widgets;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.IntEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.BounceInterpolator;

import me.tmgg.viewsdemoapp.R;

/**
 * @author sunwei
 * 邮箱：tianmu19@gmail.com
 * 时间：2019/3/6 10:27
 * 包名：me.tmgg.viewsdemoapp.widgets
 * <p>description:            </p>
 */
public class ImageViewBound extends AppCompatImageView {

    private static int mTop;
    private static int mLeft;

    public ImageViewBound(Context context) {
        super(context);
        init();
    }

    private int i = 0;
    private static int TOTALSIZE = 3;
    private static final String TAG = "valueanim";

    private void init() {
        post(new Runnable() {
            @Override
            public void run() {
                mTop = getTop();
                mLeft = getLeft();
            }
        });
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setDuration(3000);
        valueAnimator.setIntValues(0, 300,0);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setInterpolator(new BounceInterpolator());
        valueAnimator.setRepeatMode(ValueAnimator.RESTART);
        valueAnimator.setEvaluator(new IntEvaluator());
        valueAnimator.setStartDelay(100);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                Log.e(TAG, "onAnimationUpdate: "+value );
                layout(mLeft, mTop- value, mLeft + getWidth(), mTop - value + getHeight());
            }
        });
        valueAnimator.start();
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationRepeat(Animator animation) {
                super.onAnimationRepeat(animation);
                i++;
                switch (i % TOTALSIZE) {
                    case 0:
                        setImageResource(R.drawable.share_zhuce_weixin);
                        break;
                    case 1:
                        setImageResource(R.drawable.share_zhuce_qq);
                        break;
                    case 2:
                        setImageResource(R.drawable.share_zhucepengyouquan);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                setImageResource(R.drawable.share_zhuce_weixin);
            }
        });
    }

    public ImageViewBound(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }
}
