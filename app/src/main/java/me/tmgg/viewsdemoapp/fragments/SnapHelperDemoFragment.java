package me.tmgg.viewsdemoapp.fragments;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import me.tmgg.viewsdemoapp.R;

/**
 * A SnapHelper demo.
 */
public class SnapHelperDemoFragment extends android.support.v4.app.Fragment {


    private FragmentActivity mActivity;
    private Context mContext;
    private List<String> datas ;
    private MyAdapter myAdapter;

    public SnapHelperDemoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (FragmentActivity) context;
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_snap_helper_demo, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        //居中操作
//        LinearSnapHelper linearSnapHelper = new LinearSnapHelper();
//        linearSnapHelper.attachToRecyclerView(recyclerView);
        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(recyclerView);
        datas = new ArrayList<>(15);
        initDatas();
        myAdapter = new MyAdapter(R.layout.item_rv, datas);
        recyclerView.setAdapter(myAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //第一个展示的位置
                int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
                //第一个全部展示出来的item的位置
                int firstCompletelyVisibleItemPosition = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
                Toast.makeText(mContext, "位置："+firstCompletelyVisibleItemPosition+"___ "+firstVisibleItemPosition, Toast.LENGTH_SHORT).show();
            }
        });
        myAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(mActivity, "Item: "+position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initDatas() {
        for (int i = 0; i < 15; i++) {
            datas.add("item "+i);
        }
    }

    private static class MyAdapter extends BaseQuickAdapter<String, BaseViewHolder>{

        public MyAdapter(int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            helper.setText(R.id.tv_item,item);
            int adapterPosition = helper.getAdapterPosition();
            if(adapterPosition%2==0){
                helper.setBackgroundColor(R.id.cl_bg, Color.RED);
            }else{
                helper.setBackgroundColor(R.id.cl_bg, Color.YELLOW);
            }
        }
    }

}
