package me.tmgg.viewsdemoapp;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.LinearInterpolator;

import me.tmgg.viewsdemoapp.bean.Point;
import me.tmgg.viewsdemoapp.widgets.evaluator.ScaleAlhpaExecutor;

public class ValueAnimActivity extends AppCompatActivity {

    private View view1;
    private View view2;
    private View view3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_value_anim);
        processScanBg();
    }

    private int index = 0;

    private void processScanBg() {


        view1 = findViewById(R.id.view01);
        view2 = findViewById(R.id.view02);
        view3 = findViewById(R.id.view03);
        ScaleAlhpaExecutor scaleAlhpaExecutor = new ScaleAlhpaExecutor();
        ValueAnimator valueAnimator1 = new ValueAnimator();
        valueAnimator1.setDuration(5000);
        valueAnimator1.setObjectValues(new Point(0.7f,0.9f),new Point(0f,3.0f));
        valueAnimator1.setRepeatMode(ValueAnimator.RESTART);
        valueAnimator1.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator1.setInterpolator(new LinearInterpolator());
        valueAnimator1.setEvaluator(scaleAlhpaExecutor);

        ValueAnimator valueAnimator2 = valueAnimator1.clone();
        valueAnimator2.setStartDelay(800);
        ValueAnimator valueAnimator3 = valueAnimator1.clone();
        valueAnimator3.setStartDelay(1600);
        valueAnimator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Point point = (Point) animation.getAnimatedValue();
                index = 0;
                processScaleAlpha(index,point);
            }
        });
        valueAnimator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Point point = (Point) animation.getAnimatedValue();
                index = 1;
                processScaleAlpha(index,point);
            }
        });
        valueAnimator3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Point point = (Point) animation.getAnimatedValue();
                index = 2;
                processScaleAlpha(index,point);
            }
        });
        valueAnimator1.start();
        valueAnimator2.start();
        valueAnimator3.start();



    }

    private void processScaleAlpha(int index, Point point) {
        float scale = point.scale;
        float alpha = point.alpha;
        switch (index) {
            case 0:
                view1.setAlpha(alpha);
                view1.setScaleX(scale);
                view1.setScaleY(scale);
                break;
            case 1:
                view2.setAlpha(alpha);
                view2.setScaleX(scale);
                view2.setScaleY(scale);
                break;
            case 2:
                view3.setAlpha(alpha);
                view3.setScaleX(scale);
                view3.setScaleY(scale);
                break;
        }
    }
}
