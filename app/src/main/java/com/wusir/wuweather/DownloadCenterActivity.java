package com.wusir.wuweather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lzy.okgo.db.DownloadManager;
import com.lzy.okgo.model.Progress;
import com.wusir.adapter.section.DownloadSectionAdapter;
import com.wusir.adapter.section.Section;

import java.util.ArrayList;
import java.util.List;

public class DownloadCenterActivity extends AppCompatActivity {
    private DownloadSectionAdapter adapter;
    private RecyclerView rv;
    private List<Section.DataBean> sections= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_center);
        rv= (RecyclerView) findViewById(R.id.rv);
//        adapter=new DownloadSectionAdapter(this);
//        initData();
//        LinearLayoutManager manager=new LinearLayoutManager(this);
//        rv.setLayoutManager(manager);
//        rv.setAdapter(adapter);
    }

    private void initData() {
        List<Progress> finished= DownloadManager.getInstance().getFinished();
        List<Progress> downing=DownloadManager.getInstance().getDownloading();
        if(downing.size()>0||finished.size()>0){
            //
            Section.DataBean dataBean1 = new Section.DataBean();
            dataBean1.setDowning_data(downing);
            dataBean1.setCategory_id(1);
            sections.add(dataBean1);
            //adapter.setData(sections);
            //
            Section.DataBean dataBean2 = new Section.DataBean();
            dataBean2.setDowned_data(finished);
            dataBean2.setCategory_id(2);
            sections.add(dataBean2);
            adapter.setData(sections);
        }else{
            adapter.setData(null);
        }
    }
}
