package com.wusir.wuweather;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.wusir.DiffCallback;
import com.wusir.LoadingBean;
import com.wusir.LoadingEndBean;
import com.wusir.OnLoadMoreListener;
import com.wusir.Register;
import com.wusir.RxBus;
import com.wusir.bean.HeWeathers;
import com.wusir.bean.Weather;
import com.wusir.modules.weather.IWeather;
import com.wusir.modules.weather.WeatherPresenter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;
import okhttp3.ResponseBody;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.Call;
import retrofit2.converter.gson.GsonConverterFactory;

public class ItemFragment extends Fragment {//implements IWeather.View,SwipeRefreshLayout.OnRefreshListener

    // TODO: Customize parameter argument names
    private static final String ARG_CITY = "arg_city";
    //private RecyclerView rv;
    private ArrayList<Weather> list = new ArrayList<>();
    private MyAdapter myAdapter;
    private int cis=0;
    private String citys;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    //
    private IWeather.Presenter presenter;
    protected MultiTypeAdapter adapter;
    protected Items oldItems = new Items();
    private  boolean canLoadMore = false;
    protected boolean isViewInitiated;
    protected boolean isVisibleToUser;
    protected boolean isDataInitiated;
    protected Observable<Integer> observable;
    public static final String TAG = "ItemFragment";
    public ItemFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ItemFragment newInstance(int ci) {
        ItemFragment fragment = new ItemFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_CITY, ci);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            cis = getArguments().getInt(ARG_CITY);
        }
        //setPresenter(presenter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        isViewInitiated = true;
//        fetchData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);
        myAdapter=new MyAdapter();
        // Set the adapter
        //if (view instanceof RecyclerView) {
            recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);
            swipeRefreshLayout= (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
            recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
            recyclerView.setAdapter(myAdapter);
            //
//            adapter = new MultiTypeAdapter(oldItems);
//            Register.registerWeatherItem(adapter);
//            recyclerView.setAdapter(adapter);
//            recyclerView.addOnScrollListener(new OnLoadMoreListener() {
//                @Override
//                public void onLoadMore() {
//                    if (canLoadMore) {
//                        canLoadMore = false;
//                        presenter.doLoadMoreData();
//                    }
//                }
//            });
        //}
        loadData();
        return view;
    }

    public void loadData() {
        if(cis==1){
            citys="杭州";
        }else if(cis==2){
            citys="南昌";
        }else if(cis==3){
            citys="九江";
        }else if(cis==4){
            citys="景德镇";
        }
        //2.OkGo方法
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
    //1.
//    @Override
//    public void onShowLoading() {
//        swipeRefreshLayout.post(new Runnable() {
//            @Override
//            public void run() {
//                swipeRefreshLayout.setRefreshing(true);
//            }
//        });
//    }
//    //2.
//    @Override
//    public void onHideLoading() {
//        swipeRefreshLayout.post(new Runnable() {
//            @Override
//            public void run() {
//                swipeRefreshLayout.setRefreshing(false);
//            }
//        });
//    }
//    //3
//    @Override
//    public void onShowNetError() {
//        getActivity().runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                adapter.setItems(new Items());
//                adapter.notifyDataSetChanged();
//                canLoadMore = false;
//            }
//        });
//    }
//    //4
//    @Override
//    public void setPresenter(IWeather.Presenter presenter) {
//        if (null == presenter) {
//            this.presenter = new WeatherPresenter(this);
//        }
//    }
//    //5
//    @Override
//    public void onSetAdapter(List<?> list) {
//        Items newItems = new Items(list);
//        newItems.add(new LoadingBean());
//        DiffCallback.notifyDataSetChanged(oldItems, newItems, 1, adapter);
//        oldItems.clear();
//        oldItems.addAll(newItems);
//        canLoadMore = true;
//    }
//    //6
//    @Override
//    public void onShowNoMore() {
//        getActivity().runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                if(oldItems.size()>0){
//                    Items newItems=new Items(oldItems);
//                    newItems.remove(newItems.size()-1);
//                    newItems.add(new LoadingEndBean());
//                    adapter.setItems(newItems);
//                    adapter.notifyDataSetChanged();
//                }else if(oldItems.size()==0){
//                    oldItems.add(new LoadingEndBean());
//                    adapter.setItems(oldItems);
//                    adapter.notifyDataSetChanged();
//                }
//                canLoadMore=false;
//            }
//        });
//    }
//    //7
//    @Override
//    public void onLoadData() {
//        onShowLoading();
//        presenter.doLoadData(citys);
//    }
//    //8
//    @Override
//    public void onRefresh() {
//        int firstVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
//        if (firstVisibleItemPosition == 0) {
//            presenter.doRefresh(citys);
//            return;
//        }
//        recyclerView.scrollToPosition(5);
//        recyclerView.smoothScrollToPosition(0);
//    }
//
//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        this.isVisibleToUser = isVisibleToUser;
//        fetchData();
//    }
//
//    private void fetchData() {
//        if (isVisibleToUser && isViewInitiated && (!isDataInitiated || false)) {
//            observable = RxBus.getInstance().register(TAG);
//            observable.subscribe(new Consumer<Integer>() {
//                @Override
//                public void accept(@NonNull Integer integer) throws Exception {
//                    adapter.notifyDataSetChanged();
//                }
//            });
//            onLoadData();
//            isDataInitiated = true;
//        }
//    }

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
