/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.tmgg.viewsdemoapp.fragments;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * A {@link FrameLayout} that allows the user to drag and reposition child views.
 */
public class DragFrameLayout extends FrameLayout {

    /**
     * The list of {@link View}s that will be draggable.
     */
    private List<View> mDragViews;

    /**
     * The {@link DragFrameLayoutController} that will be notify on drag.
     */
    private DragFrameLayoutController mDragFrameLayoutController;

    private ViewDragHelper mDragHelper;

    public DragFrameLayout(Context context) {
        this(context, null, 0, 0);
    }

    public DragFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0, 0);
    }

    public DragFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DragFrameLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mDragViews = new ArrayList<View>();

        /**
         * Create the {@link ViewDragHelper} and set its callback.
         */
        mDragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {

            private int top;
            private int left;

            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return mDragViews.contains(child);
            }

            //当状态改变的时候回调，返回相应的状态（这里有三种状态）
            @Override
            public void onViewDragStateChanged(int state) {
                super.onViewDragStateChanged(state);
            }

            // 当你拖动的View位置发生改变的时候回调
            @Override
            public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
                super.onViewPositionChanged(changedView, left, top, dx, dy);
            }

            //不让移动子布局超过边界
            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                int paddingLeft = getPaddingLeft();
                int rightBound = getWidth()-child.getWidth()-paddingLeft;

                return Math.min(Math.max(paddingLeft,left),rightBound);
            }

            //竖直拖拽的时候回调的方法
            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                int paddingTop = getPaddingTop();
                int bottomBound = getHeight()- child.getHeight()-paddingTop;
                return Math.min(Math.max(paddingTop,top),bottomBound);
            }

            @Override
            public void onViewCaptured(View capturedChild, int activePointerId) {
                super.onViewCaptured(capturedChild, activePointerId);
                left = capturedChild.getLeft();
                top = capturedChild.getTop();
                if (mDragFrameLayoutController != null) {
                    mDragFrameLayoutController.onDragDrop(true);
                }
            }

            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                super.onViewReleased(releasedChild, xvel, yvel);
                if (mDragFrameLayoutController != null) {
                    mDragFrameLayoutController.onDragDrop(false);
                }
                //松开时候 让子view回到原来的状态
//                mDragHelper.settleCapturedViewAt(left, top);
//                invalidate();
            }


            //把view的移动 给边框的拖动
//            @Override
//            public void onEdgeDragStarted(int edgeFlags, int pointerId) {
////                super.onEdgeDragStarted(edgeFlags, pointerId);
//                if(mDragViews.get(0)!=null){
//                    mDragHelper.captureChildView(mDragViews.get(0),pointerId);
//                }
//            }
        });
        //把view的移动 给边框的拖动
//        mDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_ALL);
    }


    @Override
    public void computeScroll() {
        if (mDragHelper != null && mDragHelper.continueSettling(true)) {
            postInvalidate();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final int action = ev.getActionMasked();
        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            mDragHelper.cancel();
            return false;
        }
        return mDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        mDragHelper.processTouchEvent(ev);
        return true;
    }

    /**
     * Adds a new {@link View} to the list of views that are draggable within the container.
     *
     * @param dragView the {@link View} to make draggable
     */
    public void addDragView(View dragView) {
        mDragViews.add(dragView);
    }

    /**
     * Sets the {@link DragFrameLayoutController} that will receive the drag events.
     *
     * @param dragFrameLayoutController a {@link DragFrameLayoutController}
     */
    public void setDragFrameController(DragFrameLayoutController dragFrameLayoutController) {
        mDragFrameLayoutController = dragFrameLayoutController;
    }

    /**
     * A controller that will receive the drag events.
     */
    public interface DragFrameLayoutController {

        public void onDragDrop(boolean captured);
    }
}
