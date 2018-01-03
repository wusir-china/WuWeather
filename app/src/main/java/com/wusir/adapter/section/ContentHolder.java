package com.wusir.adapter.section;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.wusir.wuweather.R;


/**
 * Created by Administrator on 2017/9/7 0007.
 */

public class ContentHolder extends RecyclerView.ViewHolder{

    public ImageView image;
    public TextView title;
    public ProgressBar progress;
    public TextView precent;
    public Button download,deleted;
    public TextView nowSize;

    public ContentHolder(View itemView) {
        super(itemView);
        initView(itemView);
    }

    private void initView(View itemView) {
        image = (ImageView) itemView.findViewById(R.id.image);
        title = (TextView) itemView.findViewById(R.id.title);
        progress= (ProgressBar) itemView.findViewById(R.id.progress);
        precent= (TextView) itemView.findViewById(R.id.precent);
        download = (Button) itemView.findViewById(R.id.download);
        deleted = (Button) itemView.findViewById(R.id.deleted);
        nowSize= (TextView) itemView.findViewById(R.id.nowSize);
    }

}
