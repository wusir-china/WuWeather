package com.wusir.wuweather;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.wusir.bean.Weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ItemFragment extends Fragment {
    private static final String ARG_CITY = "arg_city";
    private ArrayList<Weather> list = new ArrayList<>();
    private MyAdapter myAdapter;
    private String citys;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    @SuppressWarnings("unused")
    public static ItemFragment newInstance(String city) {
        ItemFragment fragment = new ItemFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CITY, city);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            citys = getArguments().getString(ARG_CITY);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);
        myAdapter=new MyAdapter();
        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);
        swipeRefreshLayout= (SwipeRefreshLayout) view.findViewById(R.id.refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(myAdapter);
        swipeRefreshLayout.setRefreshing(true);
        loadData();
        return view;
    }

    public void loadData() {
        OkGo.<String>get(Path.weApi(citys)).tag(this).execute(new StringCallback() {
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
                    swipeRefreshLayout.setRefreshing(false);
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
    class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder viewHolder;
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_wether, parent, false);
            viewHolder=new MyViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            MyViewHolder mh = (MyViewHolder) holder;
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
            public  ImageView iv;
            public  TextView tv_time;
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
