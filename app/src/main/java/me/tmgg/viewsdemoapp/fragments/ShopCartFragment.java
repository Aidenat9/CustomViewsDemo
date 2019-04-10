package me.tmgg.viewsdemoapp.fragments;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Fragment;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import me.tmgg.viewsdemoapp.R;
import me.tmgg.viewsdemoapp.widgets.evaluator.ParabolaEvaluator;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShopCartFragment extends android.support.v4.app.Fragment {


    private View view;

    public ShopCartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_shop_cart, container, false);
        return view;
    }

    private static final String TAG = "shopcart";
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();
        animstart();
    }

    private void animstart() {
        ImageView imageView = view.findViewById(R.id.iv_boshi);
        ImageView ivCart = view.findViewById(R.id.iv_cart);
        int[] startA = new int[2];
        int[] endA = new int[2];
  imageView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
      @Override
      public boolean onPreDraw() {
          imageView.getLocationInWindow(startA);
          return true;
      }
  });
        ivCart.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                ivCart.getLocationInWindow(endA);
                Log.e(TAG, "onViewCreated:startA "+startA [0] +"___"+startA[1]);
                Log.e(TAG, "onViewCreated:endA "+endA [0] +"___"+endA[1]);
                Log.e(TAG, "animstart: "+imageView.getLeft()+"___" +ivCart.getTop() );
                return true;
            }
        });



        view.findViewById(R.id.btn_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAnim();
            }

            private void startAnim() {
                final ImageView mImg = new ImageView(getActivity());
                mImg.setImageResource(R.drawable.shape_point);
                mImg.setScaleType(ImageView.ScaleType.MATRIX);
                ViewGroup rootView = (ViewGroup) getActivity().getWindow().getDecorView();
                rootView.addView(mImg);
                PointF pointF_start = new PointF(startA[0], startA[1]);
                PointF pointF_end = new PointF(endA[0],endA[1]+ivCart.getHeight()/2);
                int pointX = (int) ((pointF_start.x + pointF_end.x) / 2 - 100);
                int pointY = (int) (pointF_start.y - 200);
                PointF controllPoint = new PointF(pointX, pointY);
                ValueAnimator animator = ValueAnimator.ofObject(new ParabolaEvaluator(controllPoint),pointF_start,pointF_end);
                animator.setEvaluator(new ParabolaEvaluator(controllPoint));
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        PointF pointF = (PointF) animation.getAnimatedValue();
                        mImg.setX(pointF.x);
                        mImg.setY(pointF.y);
                    }
                });
                animator.setDuration(3000);
                animator.setInterpolator(new LinearInterpolator());
                animator.start();

                /**
                 * 动画结束，移除掉小圆圈
                 */
                animator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        ViewGroup rootView = (ViewGroup) getActivity().getWindow().getDecorView();
                        rootView.removeView(mImg);
                    }
                });
            }
        });
    }
}
