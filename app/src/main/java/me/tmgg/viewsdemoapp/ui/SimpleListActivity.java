package me.tmgg.viewsdemoapp.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import me.tmgg.viewsdemoapp.R;
import me.tmgg.viewsdemoapp.utils.Utils;
import me.tmgg.viewsdemoapp.widgets.OffsetLinearLayoutManager;
import me.tmgg.viewsdemoapp.widgets.ScrollImageView;

public class SimpleListActivity extends AppCompatActivity {
    private List<String> datas = new ArrayList<>();
    private int x=0;
    private RecyclerView recyclerView;
    private final int COUNTS = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_list);
        ScrollImageView scrollImageView = findViewById(R.id.scrollImageView);
        recyclerView = findViewById(R.id.recyclerview);
        OffsetLinearLayoutManager offsetLinearLayoutManager = new OffsetLinearLayoutManager(this);
        recyclerView.setLayoutManager(offsetLinearLayoutManager);
        for (int i = 0; i < COUNTS; i++) {
            datas.add("sample item："+i);
        }
        Myadpter myadpter = new Myadpter(R.layout.item_simple_list,datas);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.HORIZONTAL));
        recyclerView.setAdapter(myadpter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            private float percent;

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int scollYDistance = getScollYDistance();
                percent = (float)scollYDistance/((float)COUNTS*getItemHeight()- Utils.getScreenHeight());
                Log.d(TAG,"scollYDistance: "+scollYDistance+"    itemHeight: "+getItemHeight()+"  screenHeight: "+Utils.getScreenHeight());
                Log.d(TAG,"percent: "+percent);
                scrollImageView.setScrollOffset(percent);
                //计算滑动距离占总高度的百分比，然后传给图片，图片按比例画当前的位置
            }

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
    }

    public int getScollYDistance() {
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        int position = layoutManager.findFirstVisibleItemPosition();
        View firstVisiableChildView = layoutManager.findViewByPosition(position);
        int itemHeight = firstVisiableChildView.getHeight();
        return (position) * itemHeight - firstVisiableChildView.getTop();
    }
    public int getItemHeight() {
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        int position = layoutManager.findFirstVisibleItemPosition();
        View firstVisiableChildView = layoutManager.findViewByPosition(position);
        int itemHeight = firstVisiableChildView.getHeight();
        return itemHeight;
    }

    private static final String TAG = "scrollImageView";
    private class Myadpter extends BaseQuickAdapter<String, BaseViewHolder>{
        public Myadpter(int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
        }
    }
}
