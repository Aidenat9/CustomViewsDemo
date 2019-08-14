package me.tmgg.viewsdemoapp.picpreview;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.graphics.Color;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import java.util.ArrayList;
import java.util.List;

public class ElasticDragDismissFrameLayout extends FrameLayout {
    private static float DECELERATION_RATE = ((float) (Math.log(0.78d) / Math.log(0.9d)));
    public static final float DRAG_ELASTICITY_LARGE = 0.9f;
    public static final float DRAG_ELASTICITY_NORMAL = 0.5f;
    public static final float DRAG_ELASTICITY_XLARGE = 1.25f;
    public static final float DRAG_ELASTICITY_XXLARGE = 2.0f;
    private static final float GRAVITY = 2000.0f;
    private static final float INFLEXION = 0.35f;
    private static Interpolator fastOutSlowInInterpolator;
    private float alplaDistance;
    /* access modifiers changed from: private */
    public int b;
    private int backgroundColor;
    private int bgAlpha;
    private List<ElasticDragDismissCallback> callbacks;
    private float dragDismissDistance;
    private float dragDismissFraction;
    private float dragDismissScale;
    private float dragElasticity;
    private RectF draggingBackground;
    private boolean draggingDown;
    private boolean draggingUp;
    private boolean enabled;
    /* access modifiers changed from: private */
    public int g;
    private float mFlingFriction;
    private float mPhysicalCoeff;
    private int mTouchSlop;
    /* access modifiers changed from: private */
    public int r;
    private boolean shouldScale;
    private float totalDrag;

    public static abstract class ElasticDragDismissCallback {
        public void onDrag(float f, float f2, float f3, float f4) {
        }

        public void onDragDismissed() {
        }
    }

    public ElasticDragDismissFrameLayout(Context context) {
        this(context, null, 0);
    }

    public ElasticDragDismissFrameLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ElasticDragDismissFrameLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.dragDismissDistance = Float.MAX_VALUE;
        this.alplaDistance = Float.MAX_VALUE;
        this.dragDismissFraction = -1.0f;
        this.dragDismissScale = 0.75f;
        this.shouldScale = false;
        this.dragElasticity = 0.5f;
        this.draggingDown = false;
        this.draggingUp = false;
        this.enabled = true;
        this.bgAlpha = 255;
        this.backgroundColor = Color.parseColor("#363636");
        this.r = Color.red(this.backgroundColor);
        this.g = Color.green(this.backgroundColor);
        this.b = Color.blue(this.backgroundColor);
        this.mFlingFriction = ViewConfiguration.getScrollFriction();
        init();
    }

    public int dip2px(float f) {
        return (int) ((getContext().getResources().getDisplayMetrics().density * f) + 0.5f);
    }

    private void init() {
        this.dragDismissDistance = (float) dip2px(240.0f);
        this.alplaDistance = (float) dip2px(500.0f);
        this.shouldScale = this.dragDismissScale != 1.0f;
        this.mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        setBackgroundColor(this.backgroundColor);
        this.mPhysicalCoeff = getContext().getResources().getDisplayMetrics().density * 160.0f * 386.0878f * 0.84f;
    }

    public boolean onStartNestedScroll(View view, View view2, int i) {
        if (!this.enabled || (i & 2) == 0) {
            return false;
        }
        return true;
    }

    public void onNestedPreScroll(View view, int i, int i2, int[] iArr) {
        if (!this.enabled) {
            return;
        }
        if ((this.draggingDown && i2 > 0) || (this.draggingUp && i2 < 0)) {
            dragScale(i2);
            iArr[1] = i2;
        }
    }

    public void onNestedScroll(View view, int i, int i2, int i3, int i4) {
        if (this.enabled) {
            dragScale(i4);
        }
    }

    public void onStopNestedScroll(View view) {
        if (!this.enabled) {
            return;
        }
        if (Math.abs(this.totalDrag) >= this.dragDismissDistance) {
            dispatchDismissCallback();
            return;
        }
        if (fastOutSlowInInterpolator == null) {
            fastOutSlowInInterpolator = new AccelerateDecelerateInterpolator();
        }
        getChildAt(0).animate().translationY(0.0f).scaleX(1.0f).scaleY(1.0f).setDuration(200).setInterpolator(fastOutSlowInInterpolator).start();
        ValueAnimator ofInt = ValueAnimator.ofInt(new int[]{this.bgAlpha, 255});
        ofInt.setDuration(200);
        ofInt.addUpdateListener(new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                ElasticDragDismissFrameLayout.this.setBackgroundColor(Color.argb(((Integer) valueAnimator.getAnimatedValue()).intValue(), ElasticDragDismissFrameLayout.this.r, ElasticDragDismissFrameLayout.this.g, ElasticDragDismissFrameLayout.this.b));
            }
        });
        ofInt.start();
        this.totalDrag = 0.0f;
        this.draggingUp = false;
        this.draggingDown = false;
        dispatchDragCallback(0.0f, 0.0f, 0.0f, 0.0f);
    }

    public void addListener(ElasticDragDismissCallback elasticDragDismissCallback) {
        if (this.callbacks == null) {
            this.callbacks = new ArrayList();
        }
        this.callbacks.add(elasticDragDismissCallback);
    }

    public void setEnabled(boolean z) {
        this.enabled = z;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void removeListener(ElasticDragDismissCallback elasticDragDismissCallback) {
        if (this.callbacks != null && this.callbacks.size() > 0) {
            this.callbacks.remove(elasticDragDismissCallback);
        }
    }

    private void dragScale(int i) {
        float f;
        if (i != 0) {
            this.totalDrag += (float) i;
            View childAt = getChildAt(0);
            if (i < 0 && !this.draggingUp && !this.draggingDown) {
                this.draggingDown = true;
                if (this.shouldScale) {
                    childAt.setPivotY((float) getHeight());
                }
            } else if (i > 0 && !this.draggingDown && !this.draggingUp) {
                this.draggingUp = true;
                if (this.shouldScale) {
                    childAt.setPivotY(0.0f);
                }
            }
            float log10 = (float) Math.log10((double) ((Math.abs(this.totalDrag) / this.dragDismissDistance) + 1.0f));
            float f2 = this.dragDismissDistance * log10 * this.dragElasticity;
            if (this.draggingUp) {
                f2 *= -1.0f;
            }
            childAt.setTranslationY(f2);
            if (this.draggingBackground == null) {
                this.draggingBackground = new RectF();
                this.draggingBackground.left = 0.0f;
                this.draggingBackground.right = (float) getWidth();
                this.draggingBackground.top = 0.0f;
                this.draggingBackground.bottom = (float) getHeight();
            }
            float abs = Math.abs(this.totalDrag);
            if (abs > this.alplaDistance) {
                abs = this.alplaDistance;
            }
            this.bgAlpha = (int) ((1.0f - (abs / this.alplaDistance)) * 255.0f);
            this.bgAlpha = this.bgAlpha > 255 ? 255 : this.bgAlpha;
            this.bgAlpha = this.bgAlpha < 0 ? 0 : this.bgAlpha;
            setBackgroundColor(Color.argb(this.bgAlpha, this.r, this.g, this.b));
            if (this.shouldScale) {
                float f3 = 1.0f - ((1.0f - this.dragDismissScale) * log10);
                childAt.setScaleX(f3);
                childAt.setScaleY(f3);
            }
            if ((!this.draggingDown || this.totalDrag < 0.0f) && (!this.draggingUp || this.totalDrag > 0.0f)) {
                f = log10;
            } else {
                this.totalDrag = 0.0f;
                this.draggingUp = false;
                this.draggingDown = false;
                childAt.setTranslationY(0.0f);
                childAt.setScaleX(1.0f);
                childAt.setScaleY(1.0f);
                f2 = 0.0f;
                f = 0.0f;
            }
            invalidate();
            dispatchDragCallback(f, f2, Math.min(1.0f, Math.abs(this.totalDrag) / this.dragDismissDistance), this.totalDrag);
        }
    }

    private void dispatchDragCallback(float f, float f2, float f3, float f4) {
        if (this.callbacks != null && !this.callbacks.isEmpty()) {
            for (ElasticDragDismissCallback onDrag : this.callbacks) {
                onDrag.onDrag(f, f2, f3, f4);
            }
        }
    }

    private void dispatchDismissCallback() {
        if (this.callbacks != null && !this.callbacks.isEmpty()) {
            for (ElasticDragDismissCallback onDragDismissed : this.callbacks) {
                onDragDismissed.onDragDismissed();
            }
        }
    }

    public boolean isDragging() {
        return this.draggingDown || this.draggingUp;
    }

    public void setDragElasticity(float f) {
        this.dragElasticity = f;
    }

    public void halfDistanceRequired() {
        this.dragDismissDistance /= 2.0f;
    }
}