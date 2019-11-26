package me.tmgg.viewsdemoapp.widgets;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

/**
 * @author sunwei
 * email：tianmu19@gmail.com
 * date：2019/11/26 9:13
 * version：1.0
 * <p>description：对这个可以自定peekHeight和maxHeight的BottomSheetDialog做一个简单的封装，方便使用：   </p>
 * fixbug:在BottomSheetDialog中使用ViewPager会报这个错误。错误的原因没有深究，提供一个简单的解决方案就是不要再onCreateDialog()中创建Dialog，转到onCreateView中创建View，就不会报这个异常。
 *          但是这么干又会带来第一个问题里面提到的，背景变成了白色。如果直接在onCreateView()或者onViewCreated()里面调用
 *          ((View) view.getParent()).setBackgroundColor(getResources().getColor(android.R.color.transparent));
 *          会报空指针异常，这时需要在onActivityCreated（）中调用这个方法，就可以避免这个问题。汗，一趟走过来，问题还真是不少。
 */

public class CustomHeightBottomSheetDialog extends BottomSheetDialog {

    private int mPeekHeight;
    private int mMaxHeight;
    private boolean mCreated;
    private Window mWindow;
    private BottomSheetBehavior mBottomSheetBehavior;

    public CustomHeightBottomSheetDialog(@NonNull Context context,int peekHeight,int maxHeight) {
        super(context);
        init(peekHeight, maxHeight);
    }

    public CustomHeightBottomSheetDialog(@NonNull Context context, int theme,int peekHeight,int maxHeight) {
        super(context, theme);
        init(peekHeight, maxHeight);
    }

    public CustomHeightBottomSheetDialog(@NonNull Context context, boolean cancelable, OnCancelListener cancelListener,int peekHeight,int maxHeight) {
        super(context, cancelable, cancelListener);
        init(peekHeight, maxHeight);
    }

    private void init(int peekHeight, int maxHeight) {
        mWindow = getWindow();
        mPeekHeight = peekHeight;
        mMaxHeight = maxHeight;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCreated = true;

        setPeekHeight();
        setMaxHeight();
        setBottomSheetCallback();
    }

    public void setPeekHeight(int peekHeight) {
        mPeekHeight = peekHeight;

        if (mCreated) {
            setPeekHeight();
        }
    }

    public void setMaxHeight(int height) {
        mMaxHeight = height;

        if (mCreated) {
            setMaxHeight();
        }
    }

    public void setBatterSwipeDismiss(boolean enabled) {
        if (enabled) {

        }
    }

    private void setPeekHeight() {
        if (mPeekHeight <= 0) {
            return;
        }

        if (getBottomSheetBehavior() != null) {
            mBottomSheetBehavior.setPeekHeight(mPeekHeight);
        }
    }

    private void setMaxHeight() {
        if (mMaxHeight <= 0) {
            return;
        }

        mWindow.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, mMaxHeight);
        mWindow.setGravity(Gravity.BOTTOM);
    }

    private BottomSheetBehavior getBottomSheetBehavior() {
        if (mBottomSheetBehavior != null) {
            return mBottomSheetBehavior;
        }

        View view = mWindow.findViewById(android.support.design.R.id.design_bottom_sheet);
        // setContentView() 没有调用
        if (view == null) {
            return null;
        }
        mBottomSheetBehavior = BottomSheetBehavior.from(view);
        return mBottomSheetBehavior;
    }

    private void setBottomSheetCallback() {
        if (getBottomSheetBehavior() != null) {
            mBottomSheetBehavior.setBottomSheetCallback(mBottomSheetCallback);
        }
    }

    private final BottomSheetBehavior.BottomSheetCallback mBottomSheetCallback
            = new BottomSheetBehavior.BottomSheetCallback() {
        @Override
        public void onStateChanged(@NonNull View bottomSheet,
                                   @BottomSheetBehavior.State int newState) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss();
                BottomSheetBehavior.from(bottomSheet).setState(
                        BottomSheetBehavior.STATE_COLLAPSED);
            }
        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
        }
    };
}

