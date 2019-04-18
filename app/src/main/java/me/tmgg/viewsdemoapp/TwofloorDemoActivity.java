package me.tmgg.viewsdemoapp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.gyf.barlibrary.ImmersionBar;
import com.scwang.smartrefresh.layout.api.OnTwoLevelListener;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.TwoLevelHeader;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;

/**
 * @author sunwei
 * email：tianmu19@gmail.com
 * date：2019/4/13 14:43
 * version：1.0
 * <p>description：淘寶二樓dmeo   </p>
 */

public class TwofloorDemoActivity extends AppCompatActivity {

    private RelativeLayout mRlTitle;
    private ImageView mIvCarHome;
    private FrameLayout flBlank;
    private static final String TAG = "twofloorDemoActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twofloor_demo);
        initState();
        mIvCarHome = findViewById(R.id.iv_car);
        initView();
    }

    private void initView() {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int screenWidth = displayMetrics.widthPixels;
        float dp24 = DpUtils.dp2px(getResources(), 24.0f);
        float dp66 = DpUtils.dp2px(getResources(), 66.0f);
        float middleCarWidth = (screenWidth - dp24 * 2 - dp66 ) / 2;
        Log.e(TAG, "initView: middleCarWidth  " + middleCarWidth);
        mRlTitle = findViewById(R.id.rl_home_title);
        flBlank = findViewById(R.id.fl_blank);
        final View floor = findViewById(R.id.rl_secondfloor);
        final TwoLevelHeader header = findViewById(R.id.header);
        ImageView ivSecondContent = findViewById(R.id.secondfloor_content);
        final RefreshLayout refreshLayout = findViewById(R.id.refreshLayout);
        refreshLayout.setOnMultiPurposeListener(new SimpleMultiPurposeListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishLoadMore(1000);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                Toast.makeText(getBaseContext(), "触发刷新事件", Toast.LENGTH_SHORT).show();
                if(null!=mIvCarHome)
                mIvCarHome.animate().translationX(0).start();
                refreshLayout.finishRefresh(1000);
            }

            @Override
            public void onHeaderMoving(RefreshHeader header, boolean isDragging, float percent, int offset, int headerHeight, int maxDragHeight) {
                floor.setTranslationY(Math.min(offset - floor.getHeight(), refreshLayout.getLayout().getHeight() - floor.getHeight()));
                //3.1 -4.6
                if (percent > 3.1F &&percent<=4.5&& isDragging) {
                    float offsetX = middleCarWidth * ((percent - 3.1F) / 1.4F);
                    Log.e(TAG, "onAnimationUpdate: offsetX  " + offsetX);
                    if(null!=mIvCarHome)mIvCarHome.setAlpha(1.0f);
                    if(null!=mIvCarHome)mIvCarHome.setTranslationX(offsetX);
                } else if (percent <= 3.1F && isDragging) {
                    if(null!=mIvCarHome)mIvCarHome.setAlpha(1.0f);
                    if(null!=mIvCarHome)mIvCarHome.setTranslationX(0);
                }
            }
        });
        header.setEnablePullToCloseTwoLevel(true);
        header.setOnTwoLevelListener(new OnTwoLevelListener() {
            @Override
            public boolean onTwoLevel(@NonNull RefreshLayout refreshLayout) {
                Toast.makeText(getBaseContext(), "触发二楼事件", Toast.LENGTH_SHORT).show();
                if(null!=ivSecondContent)ivSecondContent.animate().alpha(1).setDuration(1000).start();
                float offsetX = middleCarWidth * 2+dp66/2;
                if(null!=mIvCarHome)mIvCarHome.animate().translationX(offsetX).alpha(0.2f).setDuration(500).start();
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(getBaseContext(),MainActivity.class));
                        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                        refreshLayout.getLayout().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if(null!=mIvCarHome)mIvCarHome.setAlpha(0f);
                                if(null!=mIvCarHome)mIvCarHome.setTranslationX(0f);
                                if(null!=ivSecondContent)ivSecondContent.animate().alpha(0).setDuration(1600);
                                header.finishTwoLevel();
                            }
                        },490);
                    }
                }, 1500);
                return true;//true 将会展开二楼状态 false 关闭刷新
            }
        });


    }


    /**
     * 沉浸式状态栏
     */
    public void initState() {
        ImmersionBar mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.transparentStatusBar().statusBarDarkFont(true);
        /*透明状态栏，不写默认透明色*/
        mImmersionBar.init();
    }

    /**
     * 获取设备厂商，如Xiaomi @return 设备厂商
     */
    public static String getManufacturer() {
        return Build.MANUFACTURER;
    }
}
