package me.tmgg.viewsdemoapp.callback;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import java.util.List;

import me.tmgg.viewsdemoapp.bean.TestBean;

/**
 * @author sunwei
 * email：tianmu19@gmail.com
 * date：2019/6/5 21:48
 * package：me.tmgg.viewsdemoapp.callback
 * version：1.0
 * <p>description：              </p>
 */
public class TestDiffCallback extends DiffUtil.Callback {
    private List<TestBean> oldDatas, newDatas;

    public TestDiffCallback(List<TestBean> oldDatas, List<TestBean> newDatas) {
        this.oldDatas = oldDatas;
        this.newDatas = newDatas;
    }

    @Override
    public int getOldListSize() {
        return null == oldDatas ? 0 : oldDatas.size();
    }

    @Override
    public int getNewListSize() {
        return null == newDatas ? 0 : newDatas.size();
    }

    /**
     * 是否同一种布局
     *
     * @param i
     * @param i1
     * @return
     */
    @Override
    public boolean areItemsTheSame(int i, int i1) {
        return newDatas.get(i1).getClass().equals(oldDatas.get(i).getClass());
    }

    @Override
    public boolean areContentsTheSame(int i, int i1) {
        TestBean newb = newDatas.get(i1);
        TestBean oldb = oldDatas.get(i);
//        if(!newb.getName().equals(oldb.getName())){
//            return false;
//        }
//        if(!newb.getDesc().equals(oldb.getDesc())){
//            return false;
//        }
        return true;
    }

    /**
     * 返回item改变的那些字段，代表新老item改变的那些内容的payload对象
     * @param oldItemPosition
     * @param newItemPosition
     * @return
     */

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        TestBean newb = newDatas.get(newItemPosition);
        TestBean oldb = oldDatas.get(oldItemPosition);
        Bundle payload = new Bundle();
        if (!newb.getName().equals(oldb.getName())) {
            payload.putString("KEY_NAME", newb.getName());
        }
        if (payload.size() == 0) {
            return null;
        }


        return payload;
    }
}
