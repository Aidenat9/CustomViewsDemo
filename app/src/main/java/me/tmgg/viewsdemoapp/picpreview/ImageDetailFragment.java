package me.tmgg.viewsdemoapp.picpreview;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import me.tmgg.viewsdemoapp.R;

/**
 * @author sunwei
 * email：tianmu19@gmail.com
 * date：2019/8/15 10:22
 * version：1.0
 * <p>description：真正展示图片的fragment容器   </p>
 */

public class ImageDetailFragment extends Fragment {

    private Context mContext;
    private AppCompatActivity mActivity;
    private ImageView scaleImageView;

    public ImageDetailFragment() {
        // Required empty public constructor
    }

    public static final String ARG_CURRENT_POSITION = "current_position";
    public static final String ARG_START_POSITION = "start_position";
    private int mCurrentPosition;
    private int mStartPosition;


    public static ImageDetailFragment newInstance(int currentPosition, int startPosition) {
        Bundle args = new Bundle();
        args.putInt(ARG_CURRENT_POSITION, currentPosition);
        args.putInt(ARG_START_POSITION, startPosition);
        ImageDetailFragment fragment = new ImageDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = (AppCompatActivity) activity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCurrentPosition = getArguments().getInt(ARG_CURRENT_POSITION);
            mStartPosition = getArguments().getInt(ARG_START_POSITION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_image_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ElasticDragDismissFrameLayout dragDismissFrameLayout = view.findViewById(R.id.dragDismissFrameLayout);
        dragDismissFrameLayout.addListener(new ElasticDragDismissFrameLayout.ElasticDragDismissCallback() {
            @Override
            public void onDragDismissed() {
                super.onDragDismissed();
                if (null != mActivity && mActivity instanceof ImagePreviewActivity) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        mActivity.finishAfterTransition();
                    } else {
                        mActivity.finish();
                    }
                }
            }
        });
        dragDismissFrameLayout.setDragElasticity(2.0f);
        dragDismissFrameLayout.halfDistanceRequired();
        scaleImageView = view.findViewById(R.id.iv_picture);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            scaleImageView.setTransitionName(ImageConstants.IMAGE_SOURCE[mCurrentPosition]);
        }
        Glide.with(mContext).asBitmap().load(ImageConstants.IMAGE_SOURCE[mCurrentPosition]).into(new CustomTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                scaleImageView.setImageBitmap((resource));
                startPostponedEnterTransition2();
            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {
                startPostponedEnterTransition2();
            }
        });

        /**
         * 动画开始前隐藏图片
         */
//        if (mCurrentPosition == mStartPosition) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                mActivity.getWindow().getSharedElementEnterTransition().addListener(new android.transition.Transition.TransitionListener() {
//                    @Override
//                    public void onTransitionStart(android.transition.Transition transition) {
//                        scaleImageView.setVisibility(View.INVISIBLE);
//                    }
//
//                    @Override
//                    public void onTransitionEnd(android.transition.Transition transition) {
//                        scaleImageView.setVisibility(View.VISIBLE);
//                    }
//
//                    @Override
//                    public void onTransitionCancel(android.transition.Transition transition) {
//
//                    }
//
//                    @Override
//                    public void onTransitionPause(android.transition.Transition transition) {
//
//                    }
//
//                    @Override
//                    public void onTransitionResume(android.transition.Transition transition) {
//
//                    }
//                });
//            }
//        }
    }

    /**
     * 获取当前的图片元素
     * @return 共享元素
     */
    @Nullable
    public View getSharedImageView() {
        if (isViewInBounds(mActivity.getWindow().getDecorView(), scaleImageView)) {
            return scaleImageView;
        }
        return null;
    }

    /**
     * 若view在container布局内，返回true
     */
    private static boolean isViewInBounds(@NonNull View container, @NonNull View view) {
        Rect containerBounds = new Rect();
        container.getHitRect(containerBounds);
        return view.getLocalVisibleRect(containerBounds);
    }

    /**
     * 如果当前位置和开始位置相同，就执行共享元素动画，否则不执行
     */
    private void startPostponedEnterTransition2() {
        if (mCurrentPosition == mStartPosition) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mActivity.startPostponedEnterTransition();
            }
            scaleImageView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    scaleImageView.getViewTreeObserver().removeOnPreDrawListener(this);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        mActivity.startPostponedEnterTransition();
                    }
                    return true;
                }
            });
        }
    }
}
