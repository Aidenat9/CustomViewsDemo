package me.tmgg.viewsdemoapp.fragments;


import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import me.tmgg.viewsdemoapp.R;
import me.tmgg.viewsdemoapp.bean.TabEntity;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListViewFragment extends android.support.v4.app.Fragment {


    private List<TabEntity> datas;

    public ListViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_view, container, false);
    }

    private int index = 0;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button btn = view.findViewById(R.id.btn_scroll);
        ListView listView = view.findViewById(R.id.listview);
        datas = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            TabEntity entity = new TabEntity();
            entity.setTitle("tab标" + i);
            if (0 != i) {
                entity.setChecked(false);
            } else {
                entity.setChecked(true);
            }
            datas.add(entity);
        }
        MyAdapter myAdapter = new MyAdapter();
        listView.setAdapter(myAdapter);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index++;
                if (index > datas.size() - 1) {
                    index--;
                }

                myAdapter.setSelected(index);
                listView.setSelection(index);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                myAdapter.setSelected(position);
            }
        });

    }

    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return datas.size();
        }

        @Override
        public Object getItem(int position) {
            return datas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Holder holder = null;
            if(null==convertView){
                 convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tab_list, parent, false);
                holder = new Holder(convertView);
                convertView.setTag(holder);
            }else{
                holder = (Holder) convertView.getTag();
            }

            TabEntity entity = datas.get(position);
            holder.textView.setText(entity.getTitle());
            if (entity.isChecked()) {
                holder.llItem.setBackgroundColor(Color.GRAY);
                holder.imageView.setVisibility(View.VISIBLE);
            } else {
                holder.imageView.setVisibility(View.INVISIBLE);
                holder.llItem.setBackgroundColor(Color.WHITE);
            }
            return convertView;
        }

        private class Holder {
            public LinearLayout llItem;
            public TextView textView;
            public ImageView imageView;

            public Holder(View view) {
                llItem = view.findViewById(R.id.ll_item);
                textView = view.findViewById(R.id.tv_tab);
                imageView = view.findViewById(R.id.iv_label);
            }
        }

        private int oldIndex = 0;

        public void setSelected(int index) {
            if (index > datas.size() - 1 || index < 0) {
                return;
            }
            datas.get(oldIndex).setChecked(false);
            datas.get(index).setChecked(true);
            oldIndex = index;
            notifyDataSetInvalidated();
        }
    }
}
