package com.wusir.modules.weather;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wusir.bean.HeWeathers;
import com.wusir.wuweather.R;

import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by zy on 2017/11/23.
 */

public class WeatherViewBinder extends ItemViewBinder<HeWeathers.HeWeather5Bean.DailyForecastBean,WeatherViewBinder.MyViewHolder>{
    @NonNull
    @Override
    protected MyViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.list_item_wether, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder holder, @NonNull HeWeathers.HeWeather5Bean.DailyForecastBean item) {
        MyViewHolder mh = (MyViewHolder) holder;
        mh.tv_time.setText(item.getDate());
        String status=item.getCond().getTxt_d();
        mh.tv_status.setText(status);
        if(status.equals("多云")){
            //holder.iv.setBackgroundResource(R.mipmap.yin);//做背景时图片容易变形
            mh.iv.setImageResource(R.mipmap.yin);//不变形
        }else if(status.equals("晴")){
            mh.iv.setImageResource(R.mipmap.qing);
        }else {
            mh.iv.setImageResource(R.mipmap.yu);
        }
        mh.tv_tmp.setText(item.getTmp().getMin()+"/"+item.getTmp().getMax());
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        public ImageView iv;
        public TextView tv_time;
        public  TextView tv_status;
        public  TextView tv_tmp;
        public MyViewHolder(final View itemView) {
            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.iv);
            tv_time = (TextView) itemView.findViewById(R.id.time);
            tv_status = (TextView) itemView.findViewById(R.id.status);
            tv_tmp = (TextView) itemView.findViewById(R.id.tmp);
        }
    }
}
