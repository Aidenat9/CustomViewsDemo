package me.tmgg.viewsdemoapp.fragments;


import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import me.tmgg.viewsdemoapp.R;
import me.tmgg.viewsdemoapp.bean.TestBean;
import me.tmgg.viewsdemoapp.callback.TestDiffCallback;

/**
 * A simple {@link Fragment} subclass.
 */
public class DiffRecyclerViewFragment extends android.support.v4.app.Fragment implements View.OnClickListener {


    private RecyclerView recyclerView;
    private Context mContext;
    private List<TestBean> datas;
    private MyAdapter myAdapter;

    public DiffRecyclerViewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_diff_recycler_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        datas = new ArrayList<>();
        initDatas();
        view.findViewById(R.id.refresh).setOnClickListener(this);
        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        myAdapter = new MyAdapter(android.R.layout.simple_list_item_1, datas);
        recyclerView.setAdapter(myAdapter);
    }

    private void initDatas() {
        datas.add(new TestBean("李四", "lisi", 0));
        datas.add(new TestBean("李四", "lisi2", 1));
        datas.add(new TestBean("李四3", "lisi3", 2));
        datas.add(new TestBean("李四", "lisi4", 3));
    }

    @Override
    public void onClick(View v) {
        List<TestBean> newDatas = new ArrayList<>();
        newDatas.addAll(datas);
        for (TestBean b :
                datas) {
            try {
                newDatas.add(b.clone());
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
        newDatas.add(new TestBean("赵云5", "常山", 5));
        newDatas.get(0).setName("马一一");
        newDatas.get(0).setDesc("模拟的");
        TestBean bean1 = newDatas.get(1);
        newDatas.remove(bean1);
        newDatas.add(bean1);
        //old style
//        datas = newDatas;
//        myAdapter.setDatas(datas);
//        myAdapter.notifyDataSetChanged();
        //new style
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new TestDiffCallback(datas, newDatas), false);
        diffResult.dispatchUpdatesTo(myAdapter);
        datas = newDatas;
        myAdapter.setDatas(datas);
    }


    private static class MyAdapter extends BaseQuickAdapter<TestBean, BaseViewHolder> {

        public MyAdapter(int layoutResId, @Nullable List<TestBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, TestBean item) {
            helper.setText(android.R.id.text1, item.getName() + "   desc：" + item.getDesc() + "  Id：" + item.getId());
        }

        @Override
        public void onBindViewHolder(@NonNull BaseViewHolder holder, int position, @NonNull List<Object> payloads) {
            if (payloads.isEmpty()) {
                onBindViewHolder(holder, position);
            } else {
                Bundle payload = (Bundle) payloads.get(0);
                TestBean testBean = mData.get(position);
                for (String key :
                        payload.keySet()) {
                    switch (key) {
                        case "KEY_NAME":
                            holder.setText(android.R.id.text1, testBean.getName());
                            break;
                        default:
                            break;
                    }
                }


            }
        }

        public void setDatas(List<TestBean> datas) {
            mData = new ArrayList<>(datas);
        }
    }


}
