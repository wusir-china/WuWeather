package com.wusir.adapter.section;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.lzy.okgo.model.Progress;
import com.wusir.bean.StandItem;
import com.wusir.wuweather.R;

import java.util.List;

/**
 * Created by zy on 2017/9/15.
 */

public class DownloadSectionAdapter extends SectionedRecyclerViewAdapter {
    private static final int TYPE_ONE = 1;// 正在下载
    private static final int TYPE_TWO = 2;// 下载完成
    private Context mContext;
    private LayoutInflater mInflater;
    public List<Section.DataBean> allTagList;
    private List<Progress> finished;
    private List<Progress> downing;
    public DownloadSectionAdapter(Context context) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
    }
    public void setData(List<Section.DataBean> allTagList) {
        this.allTagList = allTagList;
        if(allTagList.get(1).getDowned_data()!=null){
            finished=allTagList.get(1).getDowned_data();
        }else if(allTagList.get(0).getDowned_data()!=null){
            downing=allTagList.get(0).getDowned_data();
        }
        notifyDataSetChanged();
    }
    @Override
    protected int getSectionCount() {
        if(allTagList == null || allTagList.size() == 0){
            return 0;
        }else{
            if(downing!=null&&finished==null||downing==null&&finished!=null){
                return 1;
            }else{
                return 2;
            }

        }
    }

    @Override
    protected int getItemCountForSection(int section) {
        if(allTagList != null ){
            switch (allTagList.get(section).getCategory_id()){
                case TYPE_ONE:
                    if(downing!=null){
                        return downing.size();
                    }else{
                        return 0;
                    }
                case  TYPE_TWO:
                    if(finished!=null){
                        return finished.size();
                    }else{
                        return 0;
                    }
                default:
                    return   finished.size()+downing.size();
            }
        }else{
            return 0;
        }
    }

    @Override
    protected boolean hasFooterInSection(int section) {
        return false;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateSectionHeaderViewHolder(ViewGroup parent, int viewType) {
        return new HeaderHolder(mInflater.inflate(R.layout.section_title, parent, false));
    }

    @Override
    protected RecyclerView.ViewHolder onCreateSectionFooterViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_ONE:
                return new ContentHolder(mInflater.inflate(R.layout.list_ondownload_item, parent, false));
            case TYPE_TWO:
                return new ContentHolder(mInflater.inflate(R.layout.list_downloaded_item, parent, false));
        }
        return null;
    }

    @Override
    protected void onBindSectionHeaderViewHolder(RecyclerView.ViewHolder holder, int section) {
        HeaderHolder h=(HeaderHolder)holder;
        if(finished!=null){
            h.tv_head.setText("已完成");
            h.tv_head.setVisibility(View.VISIBLE);
        }else{
            h.tv_head.setVisibility(View.GONE);
        }
        if(downing!=null){
            h.tv_head.setText("下载任务");
            h.tv_head.setVisibility(View.VISIBLE);
        }else{
            h.tv_head.setVisibility(View.GONE);
        }

    }

    @Override
    protected void onBindSectionFooterViewHolder(RecyclerView.ViewHolder holder, int section) {

    }
    private Progress progress;
    @Override
    protected void onBindItemViewHolder(RecyclerView.ViewHolder holder, int section, int position) {
        switch (getSectionItemViewType(section, position)) {
            case TYPE_ONE:
                progress=downing.get(position);
                StandItem si= (StandItem) progress.extra1;
                ContentHolder contentHolder=(ContentHolder) holder;
                Glide.with(mContext).load(si.getIcon()).placeholder(R.mipmap.loading).error(R.mipmap.lose).into(contentHolder.image);
                contentHolder.title.setText(si.getGameName());

                contentHolder.nowSize.setText(progress.currentSize+"/"+progress.totalSize);
                contentHolder.precent.setText((Math.round(progress.currentSize/progress.totalSize) * 1.0f / 100) + "%");
                contentHolder.progress.setMax((int)progress.totalSize);
                contentHolder.progress.setProgress((int)progress.currentSize);
                break;
            case TYPE_TWO:
                progress=finished.get(position);
                StandItem si2= (StandItem) progress.extra1;
                ContentHolder contentHolder2=(ContentHolder) holder;
                Glide.with(mContext).load(si2.getIcon()).placeholder(R.mipmap.loading).error(R.mipmap.lose).into(contentHolder2.image);
                contentHolder2.title.setText(si2.getGameName());
                break;

        }
    }

    @Override
    protected int getSectionItemViewType(int section, int position) {
        int tagId=allTagList.get(section).getCategory_id();
        if(tagId==1){
            return TYPE_ONE;
        }else if(tagId==2){
            return TYPE_TWO;
        }
        return super.getSectionItemViewType(section, position);
    }
}
