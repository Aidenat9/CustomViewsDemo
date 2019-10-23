package me.tmgg.viewsdemoapp.ui;

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
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;

import java.util.List;
import java.util.Map;

import me.tmgg.viewsdemoapp.R;
import me.tmgg.viewsdemoapp.picpreview.ImageConstants;
import me.tmgg.viewsdemoapp.picpreview.ImagePreviewActivity;
import me.tmgg.viewsdemoapp.picpreview.RecyclerCardAdapter;

public class PicCommonActivity extends AppCompatActivity {

    private RecyclerView recyclerView;


    public static final String EXTRA_START_POSITION = "start_position";
    public static final String EXTRA_CURRENT_POSITION = "current_position";

    private Activity mActivity;
    private Context context;
    private Bundle mReenterState;
    private GridLayoutManager gridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic_common);
        context = this;
        mActivity = this;
        recyclerView = findViewById(R.id.rv);
        gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(new RecyclerCardAdapter(this, (view, position) -> {
            if(null==view){
                return;
            }
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setExitSharedElementCallback(new SharedElementCallback() {

                @Override
                public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                    if (mReenterState != null) {
                        //从别的界面返回当前界面
                        int startingPosition = mReenterState.getInt(EXTRA_START_POSITION);
                        int currentPosition = mReenterState.getInt(EXTRA_CURRENT_POSITION);
                        Log.e("tag","111从别的界面返回当前界面 current: "+currentPosition+"  starting: "+startingPosition);
                        if (startingPosition != currentPosition) {
                            String newTransitionName = ImageConstants.IMAGE_SOURCE[currentPosition]+currentPosition;
                            View newSharedElement = recyclerView.findViewWithTag(newTransitionName);
                            if (newSharedElement != null) {
                                names.clear();
                                sharedElements.clear();
                                names.add(newTransitionName);
                                sharedElements.put(newTransitionName, newSharedElement);
                                setExitSharedElementCallback(new SharedElementCallback() {
                                });
                            }
                        }
                        mReenterState = null;
                    } else {
                        //从当前界面进入到别的界面
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
            int firstVisibleItemPosition = gridLayoutManager.findFirstVisibleItemPosition();
            int lastVisibleItemPosition = gridLayoutManager.findLastVisibleItemPosition();
            if(currentPosition>lastVisibleItemPosition||currentPosition<firstVisibleItemPosition){
                Log.e("tag","111onActivityReenter----当前item处于隐藏"+currentPosition);
//                overridePendingTransition(R.anim.alpha_exit,0);
                gridLayoutManager.scrollToPosition(currentPosition);
                recyclerView.getViewTreeObserver().addOnDrawListener(new ViewTreeObserver.OnDrawListener() {
                    @Override
                    public void onDraw() {
                        Log.e("tag","列表重新布局后");
                        initShareElement();
                    }
                });
            }else{
                Log.e("tag","111onActivityReenter----当前item显示在window"+currentPosition);
                initShareElement();
            }
        }

    }

}
