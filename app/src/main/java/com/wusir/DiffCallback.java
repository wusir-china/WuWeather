package com.wusir;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;

import com.wusir.bean.HeWeathers;

import java.util.List;

/**
 * Created by zy on 2017/11/27.
 */

public class DiffCallback extends DiffUtil.Callback{
    private List oldList, newList;
    private int type;
    public DiffCallback(List oldList, List newList, int type) {
        this.oldList = oldList;
        this.newList = newList;
        this.type = type;
    }
    public static void notifyDataSetChanged(List oldList, List newList, int type, RecyclerView.Adapter adapter) {
        DiffCallback diffCallback=new DiffCallback(oldList,newList,type);
        DiffUtil.DiffResult diffResult=DiffUtil.calculateDiff(diffCallback,true);
        //根据情况调用了adapter的四大定向刷新方法
        diffResult.dispatchUpdatesTo(adapter);
    }
    @Override
    public int getOldListSize() {
        return oldList != null ? oldList.size() : 0;
    }

    @Override
    public int getNewListSize() {
        return newList != null ? newList.size() : 0;
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return ((HeWeathers.HeWeather5Bean.DailyForecastBean) oldList.get(oldItemPosition)).getDate().equals(
                ((HeWeathers.HeWeather5Bean.DailyForecastBean) newList.get(newItemPosition)).getDate());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return ((HeWeathers.HeWeather5Bean.DailyForecastBean) oldList.get(oldItemPosition)).getDate().equals(
                ((HeWeathers.HeWeather5Bean.DailyForecastBean) newList.get(newItemPosition)).getDate());
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
