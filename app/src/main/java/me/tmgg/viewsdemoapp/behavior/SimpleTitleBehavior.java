package me.tmgg.viewsdemoapp.behavior;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * @author sunwei
 * email：tianmu19@gmail.com
 * date：2019/6/6 11:25
 * package：me.tmgg.viewsdemoapp.behavior
 * version：1.0
 * <p>description：              </p>
 */
public class SimpleTitleBehavior extends CoordinatorLayout.Behavior<View> {
    private static final String TAG = "behavior";
    /**
     * 当列表和title刚刚挨着时，手指滑动的距离
     */
    private float deltaY;

    public SimpleTitleBehavior() {
    }

    public SimpleTitleBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        return dependency instanceof RecyclerView;
    }

    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        if (0 == deltaY) {
            deltaY = dependency.getY() - child.getHeight();
        }
        /**
         * 滑动距离
         */
        float scrollY = dependency.getY() - child.getHeight();
        if (scrollY < 0) {
            scrollY = 0;
        }
        /**
         * 向上滑动为负值,滑动的比例
         */
        float tranY = -(scrollY / deltaY) * child.getHeight();
        Log.e(TAG, "onNestedPreScroll: "+tranY );
        child.setTranslationY(tranY);
        float alpha = 1-(scrollY / deltaY);
        child.setAlpha(alpha);
        return true;
    }
}
