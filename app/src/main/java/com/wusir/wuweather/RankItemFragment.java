package com.wusir.wuweather;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.db.DownloadManager;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.wusir.adapter.QuickAdapter;
import com.wusir.bean.FirstEvent;
import com.wusir.bean.StandItem;
import com.wusir.bean.Weather;
import com.wusir.download.DownLoadManger;
import com.wusir.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RankItemFragment extends Fragment {
    private static final String ARG_PARAM = "param";
    private String mParam;
    private SmartRefreshLayout refreshLayout;
    private RecyclerView mRecyclerView;
    private QuickAdapter adapter;
    private List<StandItem> rankList=new ArrayList<>();
    private int currentPage=1;
    private static final int NOTIFICATION_FLAG = 1;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public static RankItemFragment newInstance(String param) {
        RankItemFragment fragment = new RankItemFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM, param);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam = getArguments().getString(ARG_PARAM);
        }
        EventBus.getDefault().register(this);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(FirstEvent event) {
        Snackbar snackbar=Snackbar.make(mRecyclerView,event.getMsg(),Snackbar.LENGTH_SHORT);
        View view=snackbar.getView();
        if(view!=null){
            view.setBackgroundColor(Color.parseColor("#FF4081"));
        }
        snackbar.show();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_rank_item, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        refreshLayout= (SmartRefreshLayout) view.findViewById(R.id.refreshLayout);

        mRecyclerView= (RecyclerView) view.findViewById(R.id.recyclerview);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        refreshLayout.autoRefresh();
        //获取第一页数据
        //getData();

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                //refreshLayout.autoRefresh();
                //获取第一页数据
                getData();
                refreshLayout.finishRefresh();
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                //refreshlayout.autoLoadmore();
                currentPage++;
                getData();
                refreshlayout.finishLoadmore();
            }
        });
    }
    private void getData(){
        OkGo.<String>get(Path.rankItemApi(mParam,currentPage)).tag(this).execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                try {
                    //rankList.clear();
                    JSONObject jo = new JSONObject(response.body());
                    JSONArray data = jo.getJSONArray("data");
                    if(data.length()==0){
                        refreshLayout.setEnableLoadmore(false);
                        EventBus.getDefault().post(new FirstEvent("没有更多的游戏了！"));
                        return;
                    }else{
                        refreshLayout.setEnableLoadmore(true);
                    }
                    for(int i=0;i<data.length();i++){
                        JSONObject j = data.getJSONObject(i);
                        String gameIcon ="http://d.yxdd.com"+j.optString("gameIcon");
                        String gameDes = j.optString("gameDes");
                        String classname = j.optString("classname");
                        String downurl ="http://d.yxdd.com"+j.optString("downurl");
                        String gameName = j.optString("gameName");
                        int downId = j.optInt("downId");
                        //int seqNum = j.optInt("seqNum");
                        int seqNum=(currentPage-1)*15+i+1;
                        int downLoads = j.optInt("downLoads");
                        int apkSize = j.optInt("apkSize");
                        StandItem si=new StandItem(gameIcon,gameName,classname,
                                gameDes,downurl,downId,seqNum,downLoads,apkSize);
                        rankList.add(si);
                    }
                    adapter=new QuickAdapter(R.layout.list_item_rank,rankList,getActivity());
                    adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                        @Override
                        public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                            StandItem item=rankList.get(position);
                            if(!DownLoadManger.getInstance().isExist(item.getDownurl())){
                                DownLoadManger.getInstance().startDownLoad(item.getDownurl(),item);
                                ToastUtil.showToast(getActivity(),"已加入到下载列表中");
                                sendNotification(position);
                            }else{
                                ToastUtil.showToast(getActivity(),"已在下载列表中");
                            }
                        }
                    });
                    mRecyclerView.setAdapter(adapter);
                    EventBus.getDefault().post(new FirstEvent("加载完成！"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(Response<String> response) {
                super.onError(response);
            }
        });
    }
    private NotificationManager manager;
    private Notification notify;
    private void sendNotification(int position){
        List<Progress> downing= DownloadManager.getInstance().getDownloading();
        Progress progress=downing.get(position);
        StandItem si= (StandItem) progress.extra1;
        PendingIntent pendingIntent=PendingIntent.getActivity(getActivity(),0,new Intent(getActivity(), DownloadCenterActivity.class),0);
        manager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        notify=new Notification.Builder(getActivity())
                .setSmallIcon(R.mipmap.icon)
                .setContentTitle(si.getGameName())
                .setProgress(si.getSize(),(int)progress.currentSize,false)
                .setContentIntent(pendingIntent).build();
        Message msg=new Message();
        msg.what=1;
        if(si.getSize()==(int)progress.currentSize){
            msg.what=2;
        }
        handler.sendMessage(msg);
        manager.notify(NOTIFICATION_FLAG, notify);
    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            //super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    manager.notify(NOTIFICATION_FLAG, notify);
                    break;
                case 2:
                    notify.flags=Notification.FLAG_AUTO_CANCEL;
                    manager.notify(NOTIFICATION_FLAG, notify);
                    break;
            }
        }
    };
}
