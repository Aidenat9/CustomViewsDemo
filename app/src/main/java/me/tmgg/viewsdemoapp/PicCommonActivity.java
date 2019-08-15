package me.tmgg.viewsdemoapp;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.SharedElementCallback;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewTreeObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import me.tmgg.viewsdemoapp.picpreview.ImageConstants;
import me.tmgg.viewsdemoapp.picpreview.ImagePreviewActivity;
import me.tmgg.viewsdemoapp.picpreview.RecyclerCardAdapter;

public class PicCommonActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<String> lists = new ArrayList<>();


    public static final String EXTRA_START_POSITION = "start_position";
    public static final String EXTRA_CURRENT_POSITION = "current_position";

    private Activity mActivity;
    private Context context;
    private Bundle mReenterState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic_common);
        context = this;
        mActivity = this;
        initShareElement();
        recyclerView = findViewById(R.id.rv);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(new RecyclerCardAdapter(this, (view, position) -> {
            Intent intent = new Intent(context, ImagePreviewActivity.class);
            intent.putExtra(ImagePreviewActivity.EXTRA_START_POSITION, position);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ActivityOptions compat = ActivityOptions.makeSceneTransitionAnimation(mActivity, view, view.getTransitionName());
                ActivityCompat.startActivity(this, intent, compat.toBundle());
            } else {
                startActivity(intent);
            }
        }));
    }

    private void initShareElement() {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            setExitSharedElementCallback(new SharedElementCallback() {

                @Override
                public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                    if (mReenterState != null) {
                        //从别的界面返回当前界面
                        int startingPosition = mReenterState.getInt(EXTRA_START_POSITION);
                        int currentPosition = mReenterState.getInt(EXTRA_CURRENT_POSITION);
                        if (startingPosition != currentPosition) {
                            String newTransitionName = ImageConstants.IMAGE_SOURCE[currentPosition];
                            View newSharedElement = recyclerView.findViewWithTag(newTransitionName);
                            if (newSharedElement != null) {
                                names.clear();
                                names.add(newTransitionName);
                                sharedElements.clear();
                                sharedElements.put(newTransitionName, newSharedElement);
                            }
                        }
                        mReenterState = null;
//                        supportStartPostponedEnterTransition();
                    } else {
                        //从当前界面进入到别的界面
                        View navigationBar = findViewById(android.R.id.navigationBarBackground);
                        View statusBar = findViewById(android.R.id.statusBarBackground);
                        if (navigationBar != null) {
                            names.add(navigationBar.getTransitionName());
                            sharedElements.put(navigationBar.getTransitionName(), navigationBar);
                        }
                        if (statusBar != null) {
                            names.add(statusBar.getTransitionName());
                            sharedElements.put(statusBar.getTransitionName(), statusBar);
                        }
                    }
                }
            });
        }
    }

    @Override
    public void onActivityReenter(int requestCode, Intent data) {
        super.onActivityReenter(requestCode, data);
        mReenterState = new Bundle(data.getExtras());
        int startingPosition = mReenterState.getInt(EXTRA_START_POSITION);
        int currentPosition = mReenterState.getInt(EXTRA_CURRENT_POSITION);
        if (startingPosition != currentPosition) {
            recyclerView.scrollToPosition(currentPosition);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            supportPostponeEnterTransition();
        }
        recyclerView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                recyclerView.getViewTreeObserver().removeOnPreDrawListener(this);
                recyclerView.requestLayout();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    supportStartPostponedEnterTransition();
                }
                return true;
            }
        });
    }

}
