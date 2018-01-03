package com.wusir.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wusir.bean.StandItem;
import com.wusir.wuweather.R;

import java.util.List;

/**
 * Created by zy on 2017/9/11.
 */

public class QuickAdapter extends BaseQuickAdapter<StandItem, BaseViewHolder>{
    private Context mContext;
    public QuickAdapter(int layoutResId, List<StandItem> data,Context mContext) {
        super(layoutResId, data);
        this.mContext=mContext;
    }

    @Override
    protected void convert(BaseViewHolder helper, StandItem item) {
        // 加载网络图片
        Glide.with(mContext).load(item.getIcon()).placeholder(R.mipmap.loading).
                error(R.mipmap.lose).into((ImageView) helper.getView(R.id.gameImg));
        helper.setText(R.id.tv_rankNum,item.getPlace()+"");
        helper.setText(R.id.gameName,item.getGameName());
        helper.setText(R.id.gameSize,item.getSize()+"");
        helper.setText(R.id.downNum,item.getDownNum()+"");
        helper.setText(R.id.gameType,item.getType());
        helper.setText(R.id.gameMsg,item.getGameDes());
        helper.addOnClickListener(R.id.downLoad);
    }
}
