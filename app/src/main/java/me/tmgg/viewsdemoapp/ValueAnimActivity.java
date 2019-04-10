package me.tmgg.viewsdemoapp;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import me.tmgg.viewsdemoapp.bean.Point;
import me.tmgg.viewsdemoapp.widgets.evaluator.ChangeWidthEvalutor;
import me.tmgg.viewsdemoapp.widgets.evaluator.ScaleAlhpaEvalutor;

public class ValueAnimActivity extends AppCompatActivity {

    private View view1;
    private View view2;
    private View view3;

    private Button mMenuButton;
    private Button mItemButton1;
    private Button mItemButton2;
    private Button mItemButton3;
    private Button mItemButton4;
    private Button mItemButton5;

    private TextView mTv;
    private ImageView mIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_value_anim);
        processScanBg();
        processBottomMenu();
        init();
    }

    private void init() {
        float dp46 = DpUtils.dp2px(getResources(), 46f);
        float dp98 = DpUtils.dp2px(getResources(), 98f);

        View inflate = findViewById(R.id.fl);
        mTv = findViewById(R.id.tv_changebound_desc);
        mTv.setText("+20分");
        mIv = findViewById(R.id.iv_changebound);


        AnimatorSet animationSet = new AnimatorSet();
        animationSet.setInterpolator(new AccelerateDecelerateInterpolator());
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(inflate, "scaleX", 0f, 1.0f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(inflate, "scaleY", 0f, 1.0f);
        scaleX.setDuration(400);
        scaleY.setDuration(400);
        ChangeWidthEvalutor changeWidthEvalutor = new ChangeWidthEvalutor();
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setDuration(800);
        valueAnimator.setFloatValues(dp46,dp98);
        valueAnimator.setEvaluator(changeWidthEvalutor);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = (float) animation.getAnimatedValue();
                Log.e("animatedValue", "onAnimationUpdate: animatedValue"+animatedValue );
                ViewGroup.LayoutParams layoutParams = inflate.getLayoutParams();
                layoutParams.width = (int) animatedValue;
                inflate.setLayoutParams(layoutParams);
            }
        });
        ValueAnimator valueAnimator2 = new ValueAnimator();
        valueAnimator2.setDuration(500);
        valueAnimator2.setFloatValues(dp98,dp46);
        valueAnimator2.setEvaluator(changeWidthEvalutor);
        valueAnimator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = (float) animation.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = inflate.getLayoutParams();
                layoutParams.width = (int) animatedValue;
                inflate.setLayoutParams(layoutParams);
            }
        });
        animationSet.play(scaleX).with(scaleY).before(valueAnimator).before(valueAnimator2);
        animationSet.start();

    }
    //卫星菜单动画
    private boolean isOpenMenu;

    private void processBottomMenu() {
        mMenuButton = findViewById(R.id.menu);
        mItemButton1 = findViewById(R.id.item1);
        mItemButton2 = findViewById(R.id.item2);
        mItemButton3 = findViewById(R.id.item3);
        mItemButton4 = findViewById(R.id.item4);
        mItemButton5 = findViewById(R.id.item5);
        mMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOpenMenu) {
                    doAnimateClose(mItemButton1, 0, 5, RADIUS);
                    doAnimateClose(mItemButton2, 1, 5, RADIUS);
                    doAnimateClose(mItemButton3, 2, 5, RADIUS);
                    doAnimateClose(mItemButton4, 3, 5, RADIUS);
                    doAnimateClose(mItemButton5, 4, 5, RADIUS);
                } else {
                    doAnimateOpen(mItemButton1, 0, 5, RADIUS);
                    doAnimateOpen(mItemButton2, 1, 5, RADIUS);
                    doAnimateOpen(mItemButton3, 2, 5, RADIUS);
                    doAnimateOpen(mItemButton4, 3, 5, RADIUS);
                    doAnimateOpen(mItemButton5, 4, 5, RADIUS);
                }
                isOpenMenu = !isOpenMenu;
            }
        });

    }

    private static final long DURATION = 350L;
    private static final long RADIUS = 550L;

    private void doAnimateOpen(Button view, int index, int total, long radius) {
        if (view.getVisibility() != View.VISIBLE) {
            view.setVisibility(View.VISIBLE);
        }
        double degree = Math.toRadians(90) / (total - 1) * index;
        int translationX = -(int) (radius * Math.sin(degree));
        int translationY = -(int) (radius * Math.cos(degree));
        AnimatorSet set = new AnimatorSet(); //包含平移、缩放和透明度动画
        set.playTogether(ObjectAnimator.ofFloat(view, "translationX", 0, translationX), ObjectAnimator.ofFloat(view, "translationY", 0, translationY), ObjectAnimator.ofFloat(view, "scaleX", 0f, 1f), ObjectAnimator.ofFloat(view, "scaleY", 0f, 1f), ObjectAnimator.ofFloat(view, "alpha", 0f, 1));
        //动画周期为500ms
        set.setDuration(DURATION).start();
    }

    private void doAnimateClose(final View view, int index, int total, long radius) {
        if (view.getVisibility() != View.VISIBLE) {
            view.setVisibility(View.VISIBLE);
        }
        double degree = Math.PI * index / ((total - 1) * 2);
        int translationX = -(int) (radius * Math.sin(degree));
        int translationY = -(int) (radius * Math.cos(degree));
        AnimatorSet set = new AnimatorSet(); //包含平移、缩放和透明度动画
        set.playTogether(ObjectAnimator.ofFloat(view, "translationX", translationX, 0), ObjectAnimator.ofFloat(view, "translationY", translationY, 0), ObjectAnimator.ofFloat(view, "scaleX", 1f, 0.1f), ObjectAnimator.ofFloat(view, "scaleY", 1f, 0.1f), ObjectAnimator.ofFloat(view, "alpha", 1f, 0f));
        set.setDuration(DURATION).start();
    }
        //扫描动画
        private int index = 0;

        private void processScanBg () {
            view1 = findViewById(R.id.view01);
            view2 = findViewById(R.id.view02);
            view3 = findViewById(R.id.view03);

            ScaleAlhpaEvalutor scaleAlhpaExecutor = new ScaleAlhpaEvalutor();
            ValueAnimator valueAnimator1 = new ValueAnimator();
            valueAnimator1.setDuration(5000);
            valueAnimator1.setObjectValues(new Point(0.7f, 0.9f), new Point(0f, 3.0f));
            valueAnimator1.setRepeatMode(ValueAnimator.RESTART);
            valueAnimator1.setRepeatCount(ValueAnimator.INFINITE);
            valueAnimator1.setInterpolator(new LinearInterpolator());
            valueAnimator1.setEvaluator(scaleAlhpaExecutor);

            ValueAnimator valueAnimator2 = valueAnimator1.clone();
            valueAnimator2.setStartDelay(1000);
            ValueAnimator valueAnimator3 = valueAnimator1.clone();
            valueAnimator3.setStartDelay(2000);
            valueAnimator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    Point point = (Point) animation.getAnimatedValue();
                    index = 0;
                    processScaleAlpha(index, point);
                }
            });
            valueAnimator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    Point point = (Point) animation.getAnimatedValue();
                    index = 1;
                    processScaleAlpha(index, point);
                }
            });
            valueAnimator3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    Point point = (Point) animation.getAnimatedValue();
                    index = 2;
                    processScaleAlpha(index, point);
                }
            });
            valueAnimator1.start();
            valueAnimator2.start();
            valueAnimator3.start();


        }

        private void processScaleAlpha ( int index, Point point){
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
