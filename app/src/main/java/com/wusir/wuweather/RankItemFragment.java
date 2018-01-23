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
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.wusir.util.OnLoadMoreListener;
import com.wusir.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RankItemFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private static final String ARG_PARAM = "param";
    private String mParam;
    //private SmartRefreshLayout refreshLayout;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView mRecyclerView;
    private QuickAdapter adapter;
    private List<StandItem> rankList=new ArrayList<>();
    private int currentPage=1;
    protected boolean canLoadMore = false;
    //private String citys;
    private ArrayList<Weather> list = new ArrayList<>();
    private MyAdapter myAdapter;
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
        refreshLayout= (SwipeRefreshLayout) view.findViewById(R.id.refreshLayout);
        mRecyclerView= (RecyclerView) view.findViewById(R.id.recyclerview);
        mRecyclerView.setHasFixedSize(true);
        refreshLayout.setOnRefreshListener(this);
        //getData();
        mRecyclerView.addOnScrollListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (canLoadMore) {
                    canLoadMore = false;
                    ++currentPage;
                    getWeatherData();
                }
            }
        });
        myAdapter=new MyAdapter();
        mRecyclerView.setAdapter(myAdapter);
        isViewInitiated = true;
        prepareFetchData();
    }
    private void getWeatherData(){
        OkGo.<String>get(Path.weApi(mParam)).tag(this).execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                try {
                    list.clear();
                    JSONObject jo = new JSONObject(response.body());
                    JSONArray data = jo.getJSONArray("HeWeather5");
                    for(int i=0;i<data.length();i++){
                        JSONObject item= data.getJSONObject(i);
                        JSONArray daily_forecast = item.getJSONArray("daily_forecast");
                        for(int j=0;j<daily_forecast.length();j++){
                            JSONObject subitem= daily_forecast.getJSONObject(j);
                            JSONObject cond=subitem.getJSONObject("cond");
                            String txt_d = cond.optString("txt_d");
                            String date = subitem.optString("date");
                            JSONObject tmp=subitem.getJSONObject("tmp");
                            String min= tmp.optString("min");
                            String max= tmp.optString("max");
                            String stmp=min+"度/"+max+"度";
                            list.add(new Weather(date,txt_d,stmp));
                        }
                    }
                    myAdapter.notifyDataSetChanged();
                    refreshLayout.setRefreshing(false);
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
    private void getData(){
        OkGo.<String>get(Path.rankItemApi(mParam,currentPage)).tag(this).execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                try {
                    //rankList.clear();
                    JSONObject jo = new JSONObject(response.body().toString());
                    JSONArray data = jo.getJSONArray("data");
                    if(data.length()==0){
                        canLoadMore=false;
                        //refreshLayout.setEnableLoadmore(false);
                        EventBus.getDefault().post(new FirstEvent("没有更多的游戏了！"));
                        return;
                    }else{
                        canLoadMore=true;
                        //refreshLayout.setEnableLoadmore(true);
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
                    mRecyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
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
    protected boolean isViewInitiated;
    protected boolean isVisibleToUser;
    protected boolean isDataInitiated;
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        prepareFetchData();
    }

    public boolean prepareFetchData() {
        if (isVisibleToUser && isViewInitiated && (!isDataInitiated || false)) {
            getWeatherData();
            isDataInitiated = true;
            return true;
        }
        return false;
    }
    @Override
    public void onRefresh() {
        int firstVisibleItemPosition = ((LinearLayoutManager) mRecyclerView.getLayoutManager()).findFirstVisibleItemPosition();
        if (firstVisibleItemPosition == 0) {
            //presenter.doRefresh();
            getWeatherData();
            return;
        }
        //平滑的定位到指定项
        mRecyclerView.smoothScrollToPosition(0);
    }
    class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
        @Override
        public RankItemFragment.MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            RankItemFragment.MyAdapter.MyViewHolder viewHolder;
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_wether, parent, false);
            viewHolder=new RankItemFragment.MyAdapter.MyViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            RankItemFragment.MyAdapter.MyViewHolder mh = (RankItemFragment.MyAdapter.MyViewHolder) holder;
            mh.tv_time.setText(list.get(position).getTime());
            String status=list.get(position).getStatus();
            mh.tv_status.setText(status);
            if(status.equals("多云")){
                //holder.iv.setBackgroundResource(R.mipmap.yin);//做背景时图片容易变形
                mh.iv.setImageResource(R.mipmap.yin);//不变形
            }else if(status.equals("晴")){
                mh.iv.setImageResource(R.mipmap.qing);
            }else {
                mh.iv.setImageResource(R.mipmap.yu);
            }
            mh.tv_tmp.setText(list.get(position).getTmp());
        }

        @Override
        public int getItemCount() {
            return list.size();
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
}
