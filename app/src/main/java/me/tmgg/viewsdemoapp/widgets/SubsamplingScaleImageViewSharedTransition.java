package me.tmgg.viewsdemoapp.widgets;

import android.animation.Animator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.graphics.PointF;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.davemorrissey.labs.subscaleview.ImageViewState;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

import me.tmgg.viewsdemoapp.R;

@TargetApi(19)
public class SubsamplingScaleImageViewSharedTransition extends Transition {
    private static final int CENTER_CROP = 1;
    private static final int FIT_CENTER = 0;
    private static final String PROPNAME_CENTER_X = "com.daimajia.gold:transition:center_x";
    private static final String PROPNAME_CENTER_Y = "com.daimajia.gold:transition:center_y";
    private static final String PROPNAME_SIZE = "com.daimajia.gold:transition:size";
    private static final String PROPNAME_STATE = "com.daimajia.gold:transition:state";
    private int direction;
    private int imageViewScaleType;
    private int subsamplingScaleType;

    public SubsamplingScaleImageViewSharedTransition() {
        this.imageViewScaleType = 0;
        this.direction = 0;
        this.subsamplingScaleType = 0;
    }

    @TargetApi(21)
    public SubsamplingScaleImageViewSharedTransition(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.SubsamplingScaleImageViewSharedTransition);
        this.direction = obtainStyledAttributes.getInt(R.styleable.SubsamplingScaleImageViewSharedTransition_transitionDirection, 0);
        this.subsamplingScaleType = obtainStyledAttributes.getInt(R.styleable.SubsamplingScaleImageViewSharedTransition_subsamplingImageViewScaleType, 0);
        this.imageViewScaleType = obtainStyledAttributes.getInt(R.styleable.SubsamplingScaleImageViewSharedTransition_imageViewScaleType, 0);
        obtainStyledAttributes.recycle();
        addTarget(SubsamplingScaleImageView.class);
    }

    public void captureStartValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    public void captureEndValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    private void captureValues(TransitionValues transitionValues) {
        if (transitionValues.view instanceof SubsamplingScaleImageView) {
            transitionValues.values.put(PROPNAME_SIZE, new Point(transitionValues.view.getWidth(), transitionValues.view.getHeight()));
            transitionValues.values.put(PROPNAME_STATE, ((SubsamplingScaleImageView) transitionValues.view).getState());
        }
    }

    public Animator createAnimator(ViewGroup viewGroup, TransitionValues transitionValues, TransitionValues transitionValues2) {
        float scale;
        float minIfTrue;
        PointF pointF;
        boolean z;
        boolean z2;
        if (transitionValues == null || transitionValues2 == null) {
            return null;
        }
        ImageViewState imageViewState = (ImageViewState) transitionValues.values.get(PROPNAME_STATE);
        Point point = (Point) transitionValues.values.get(PROPNAME_SIZE);
        Point point2 = (Point) transitionValues2.values.get(PROPNAME_SIZE);
        if (point == null || point2 == null || imageViewState == null || point.equals(point2)) {
            return null;
        }
        final SubsamplingScaleImageView subsamplingScaleImageView = (SubsamplingScaleImageView) transitionValues.view;
        Point point3 = new Point(subsamplingScaleImageView.getSWidth(), subsamplingScaleImageView.getSHeight());
        if (point3.x == 0 || point3.y == 0) {
            return null;
        }
        boolean z3 = this.direction == 0 ? point.x < point2.x || point.y < point2.y : this.direction == 1;
        PointF pointF2 = new PointF((float) (point3.x / 2), (float) (point3.y / 2));
        if (z3) {
            PointF pointF3 = new PointF((float) (point3.x / 2), (float) (point3.y / 2));
            float f = ((float) point.x) / ((float) point3.x);
            float f2 = ((float) point.y) / ((float) point3.y);
            if (this.imageViewScaleType == 0) {
                z = true;
            } else {
                z = false;
            }
            scale = getMinIfTrue(f, f2, z);
            float f3 = ((float) point3.x) / ((float) point2.x);
            float f4 = ((float) point3.y) / ((float) point2.y);
            if (this.subsamplingScaleType == 0) {
                z2 = true;
            } else {
                z2 = false;
            }
            minIfTrue = getMinIfTrue(f3, f4, z2);
            pointF = pointF3;
        } else {
            PointF center = imageViewState.getCenter();
            scale = imageViewState.getScale();
            minIfTrue = getMinIfTrue(((float) point2.x) / ((float) point3.x), ((float) point2.y) / ((float) point3.y), this.imageViewScaleType == 0);
            pointF = center;
        }
        ValueAnimator ofPropertyValuesHolder = ValueAnimator.ofPropertyValuesHolder(new PropertyValuesHolder[]{PropertyValuesHolder.ofFloat(PROPNAME_SIZE, new float[]{scale, minIfTrue}), PropertyValuesHolder.ofFloat(PROPNAME_CENTER_X, new float[]{pointF.x, pointF2.x}), PropertyValuesHolder.ofFloat(PROPNAME_CENTER_Y, new float[]{pointF.y, pointF2.y})});
        ofPropertyValuesHolder.addUpdateListener(new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                subsamplingScaleImageView.setScaleAndCenter(((Float) valueAnimator.getAnimatedValue(SubsamplingScaleImageViewSharedTransition.PROPNAME_SIZE)).floatValue(), new PointF(((Float) valueAnimator.getAnimatedValue(SubsamplingScaleImageViewSharedTransition.PROPNAME_CENTER_X)).floatValue(), ((Float) valueAnimator.getAnimatedValue(SubsamplingScaleImageViewSharedTransition.PROPNAME_CENTER_Y)).floatValue()));
            }
        });
        return ofPropertyValuesHolder;
    }

    private float getMinIfTrue(float f, float f2, boolean z) {
        return z ? Math.min(f, f2) : Math.max(f, f2);
    }

    public SubsamplingScaleImageViewSharedTransition setSubsamplingScaleType(int i) {
        this.subsamplingScaleType = i;
        return this;
    }

    public SubsamplingScaleImageViewSharedTransition setImageViewScaleType(int i) {
        this.imageViewScaleType = i;
        return this;
    }

    public SubsamplingScaleImageViewSharedTransition setDirection(int i) {
        this.direction = i;
        return this;
    }
}