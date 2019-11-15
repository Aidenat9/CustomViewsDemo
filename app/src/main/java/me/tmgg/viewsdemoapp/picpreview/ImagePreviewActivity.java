package me.tmgg.viewsdemoapp.picpreview;

import android.app.SharedElementCallback;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Map;

import me.tmgg.viewsdemoapp.R;
import me.tmgg.viewsdemoapp.ui.PicCommonActivity;

/**
 * @author sunwei
 * email：tianmu19@gmail.com
 * date：2019/8/15 9:50
 * version：1.0
 * <p>description：图片浏览页面   </p>
 */

public class ImagePreviewActivity extends AppCompatActivity {
    public static final String EXTRA_START_POSITION = "start_position";
    private ContentLoadingProgressBar contentLoadingProgressBar;
    private int mStartPosition;
    private boolean mIsReturning;
    private int mCurrentPosition;

    private ImageDetailFragment mCurrentDetailsFragment;
    private int imageSize;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActivityHook.hookOrientation(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_preview);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            supportPostponeEnterTransition();
        }
        initShareElement();
        mStartPosition = getIntent().getIntExtra(EXTRA_START_POSITION, 0);
        mCurrentPosition = mStartPosition;
        initView();
    }

    private void initShareElement() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setEnterSharedElementCallback(new SharedElementCallback() {

                @Override
                public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                    /**
                     * 退出的时候执行这里
                     */
                    if (mIsReturning) {
                        View sharedElement = mCurrentDetailsFragment.getSharedImageView();
                        if (sharedElement == null) {
                            names.clear();
                            sharedElements.clear();
                            Log.e("tag","222sharedElement == null");
                        } else if (mStartPosition != mCurrentPosition) {
                            //把原来的共享元素删除，添加新的共享元素数据
                            Log.e("tag","222mStartPosition != mCurrentPosition>>>");
                            names.clear();
                            sharedElements.clear();
                            names.add(sharedElement.getTransitionName());
                            sharedElements.put(sharedElement.getTransitionName(), sharedElement);
                        }
                    }
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        this.finishAfterTransition();
    }

    /**
     * 返回时执行代码
     */
    @Override
    public void finishAfterTransition() {
        Log.e("tag","222finishAfterTransition");
        mIsReturning = true;
        Intent data = new Intent();
        data.putExtra(PicCommonActivity.EXTRA_START_POSITION, mStartPosition);
        data.putExtra(PicCommonActivity.EXTRA_CURRENT_POSITION, mCurrentPosition);
        setResult(RESULT_OK, data);
        super.finishAfterTransition();
    }

    private void initView() {
        imageSize = ImageConstants.IMAGE_SOURCE.length;
        HackyViewPager viewPager = findViewById(R.id.view_pager);
        contentLoadingProgressBar = findViewById(R.id.loading_bar);
        TextView tvIndicator = findViewById(R.id.tv_pager_indicator);
        TextView tvSaveImage = findViewById(R.id.tv_pager_indicator);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager()));
        viewPager.setCurrentItem(mCurrentPosition);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                mCurrentPosition = i;
                tvIndicator.setText((mCurrentPosition + 1) + "/" + imageSize);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        tvIndicator.setText((mCurrentPosition + 1) + "/" + imageSize);
        tvSaveImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveImage2SDcard();
            }
        });

    }


    private class MyFragmentPagerAdapter extends FragmentStatePagerAdapter {

        private MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return ImageDetailFragment.newInstance(position, mStartPosition);
        }

        @Override
        public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            super.setPrimaryItem(container, position, object);
//            Log.e("tag","setPrimaryItem  "+position);
            mCurrentDetailsFragment = (ImageDetailFragment) object;
        }

        @Override
        public int getCount() {
            return imageSize;
        }
    }

    /**
     * 下载保存图片到sd卡
     */
    private void saveImage2SDcard() {
        Toast.makeText(this,"待实现保存图片",Toast.LENGTH_SHORT).show();
    }
}
