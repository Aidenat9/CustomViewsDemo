package me.tmgg.viewsdemoapp.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import me.tmgg.viewsdemoapp.R;

/**
 * @author sunwei
 * email：tianmu19@gmail.com
 * date：2019/4/29 11:48
 * version：1.0
 * <p>description：BottomBehaviorFragment   </p>
 */

public class BottomBehaviorFragment extends Fragment {


    private LinearLayout llContentBottomSheet;

    public BottomBehaviorFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bottom_behavior, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        llContentBottomSheet = view.findViewById(R.id.ll_content_bottom_sheet);
        initListen();
    }

    private void initListen(){
        /**
         * STATE_COLLAPSED：折叠状态，bottom sheets只在底部显示一部分布局。显示高度可以通过 app:behavior_peekHeight 设置；
         * STATE_DRAGGING：过渡状态，此时用户正在向上或者向下拖动bottom sheet；
         * STATE_SETTLING: 视图从脱离手指自由滑动到最终停下的这一小段时间
         * STATE_EXPANDED: 完全展开的状态
         * STATE_HIDDEN: 隐藏状态。默认是false，可通过app:behavior_hideable属性设置是否能隐藏
         *
         * 这种使用方法是没有蒙层，可以对其他控件进行操作。
         */
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(llContentBottomSheet);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

                switch (newState) {
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        Log.e("Bottom Sheet Behaviour", "STATE_COLLAPSED");
                        break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        Log.e("Bottom Sheet Behaviour", "STATE_DRAGGING");
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                        Log.e("Bottom Sheet Behaviour", "STATE_EXPANDED");
                        break;
                    case BottomSheetBehavior.STATE_HIDDEN:
                        Log.e("Bottom Sheet Behaviour", "STATE_HIDDEN");
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        Log.e("Bottom Sheet Behaviour", "STATE_SETTLING");
                        break;
                        default:
                            break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
    }

}
