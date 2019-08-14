package me.tmgg.viewsdemoapp.picpreview;

import android.app.SharedElementCallback;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.List;
import java.util.Map;

import me.tmgg.viewsdemoapp.PicCommonActivity;
import me.tmgg.viewsdemoapp.R;

/**
 * 图片浏览页面
 */
public class ImagePreviewActivity extends AppCompatActivity {
    public static final String EXTRA_START_POSITION = "start_position";
    private HackyViewPager viewPager ;
    private ContentLoadingProgressBar contentLoadingProgressBar ;
    private int mStartPosition;
    private boolean mIsReturning;
    private int mCurrentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_preview);
        initView();
        initShareElement();
        mStartPosition = getIntent().getIntExtra(EXTRA_START_POSITION, 0);
        mCurrentPosition = mStartPosition;
    }

    private void initShareElement() {
        postponeEnterTransition();
        setEnterSharedElementCallback(mCallback);
    }

    @Override
    public void finishAfterTransition() {
        mIsReturning = true;
        Intent data = new Intent();
        data.putExtra(PicCommonActivity.EXTRA_START_POSITION, mStartPosition);
        data.putExtra(PicCommonActivity.EXTRA_CURRENT_POSITION, mCurrentPosition);
        setResult(RESULT_OK, data);
        super.finishAfterTransition();
    }

    private final SharedElementCallback mCallback = new SharedElementCallback() {

        @Override
        public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
            //退出的时候执行这里
//            if (mIsReturning) {
//                ImageView sharedElement = mCurrentDetailsFragment.getAlbumImage();
//                if (sharedElement == null) {
//                    names.clear();
//                    sharedElements.clear();
//                } else if (mStartPosition != mCurrentPosition) {
//                    names.clear();
//                    names.add(sharedElement.getTransitionName());
//                    sharedElements.clear();
//                    sharedElements.put(sharedElement.getTransitionName(), sharedElement);
//                }
//            }
        }
    };

    private void initView() {
        viewPager = findViewById(R.id.view_pager);
        contentLoadingProgressBar = findViewById(R.id.loading_bar);
//        viewPager.setAdapter(new MyFragmentPagerAdapter());


    }

    private class MyFragmentPagerAdapter extends FragmentStatePagerAdapter{

        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return null;
        }

        @Override
        public int getCount() {
            return 0;
        }
    }
}
