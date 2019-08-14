package me.tmgg.viewsdemoapp;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.SharedElementCallback;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import me.tmgg.viewsdemoapp.picpreview.ActivityBrowse;
import me.tmgg.viewsdemoapp.picpreview.ImageConstants;
import me.tmgg.viewsdemoapp.picpreview.RecyclerCardAdapter;

public class PicCommonActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<String> lists = new ArrayList<>();


    public static final String EXTRA_START_POSITION   = "start_position";
    public static final String EXTRA_CURRENT_POSITION = "current_position";

    private Activity mActivity;
    private Context      context;
    private Bundle       mReenterState;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic_common);
        context = this;
        initShareElement();
        mActivity = this;
        context = this;
        recyclerView = findViewById(R.id.rv);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
//        MyAdapter adapter = new MyAdapter(R.layout.item_pic_grid,lists);
        recyclerView.setAdapter(new RecyclerCardAdapter(this, new RecyclerCardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(context,"onItemClick",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, ActivityBrowse.class);
                intent.putExtra(ActivityBrowse.EXTRA_START_POSITION, position);
                ActivityOptions compat = ActivityOptions.makeSceneTransitionAnimation(mActivity, view, view.getTransitionName());
                startActivity(intent, compat.toBundle());
            }
        }));
    }

    private void initShareElement() {
        setExitSharedElementCallback(mCallback);
    }

    private final SharedElementCallback mCallback = new SharedElementCallback() {

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
    };

    @Override
    public void onActivityReenter(int requestCode, Intent data) {
        super.onActivityReenter(requestCode, data);
        mReenterState = new Bundle(data.getExtras());
        int startingPosition = mReenterState.getInt(EXTRA_START_POSITION);
        int currentPosition = mReenterState.getInt(EXTRA_CURRENT_POSITION);
        if (startingPosition != currentPosition) {
            recyclerView.scrollToPosition(currentPosition);
        }
        postponeEnterTransition();
        recyclerView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                recyclerView.getViewTreeObserver().removeOnPreDrawListener(this);
                recyclerView.requestLayout();
                startPostponedEnterTransition();
                return true;
            }
        });
    }








    private class MyAdapter extends BaseQuickAdapter<String, BaseViewHolder>{
        RequestOptions requestOptions = null;
        public MyAdapter(int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
            requestOptions = new RequestOptions().override(900, 600).diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            Glide.with(context).load(item).apply(requestOptions).thumbnail(0.4f).into((ImageView) helper.getView(R.id.iv_grid));
        }
    }






}
