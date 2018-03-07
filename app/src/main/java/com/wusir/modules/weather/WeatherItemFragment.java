package com.wusir.modules.weather;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
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
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.wusir.adapter.QuickAdapter;
import com.wusir.bean.FirstEvent;
import com.wusir.bean.StandItem;
import com.wusir.bean.Weather;
import com.wusir.util.OnLoadMoreListener;
import com.wusir.util.ToastUtil;
import com.wusir.wuweather.Path;
import com.wusir.wuweather.R;
import com.wusir.wuweather.RetrofitFactory;
import com.wusir.wuweather.WeatherApi;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class WeatherItemFragment extends Fragment implements IWeather.View{
    private static final String ARG_PARAM = "param";
    private String mParam;
    @BindView(R.id.smartLayout)
    SmartRefreshLayout smartLayout;

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    private List<Weather> list = new ArrayList<>();
    private MyAdapter myAdapter;
    protected boolean isViewInitiated;
    protected boolean isVisibleToUser;
    protected boolean isDataInitiated;
    private IWeather.Presenter presenter;
    private  ProgressDialog pd;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public static WeatherItemFragment newInstance(String param) {
        WeatherItemFragment fragment = new WeatherItemFragment();
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
        setPresenter(presenter);
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
        View view=inflater.inflate(R.layout.fragment_rank_item, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        smartLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                int firstVisibleItemPosition = ((LinearLayoutManager) mRecyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                if (firstVisibleItemPosition == 0) {
                    presenter.doLoadData(mParam);
                    return;
                }
                //平滑的定位到指定项
                mRecyclerView.smoothScrollToPosition(0);
            }
        });
        smartLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                presenter.doLoadMoreData(mParam);
            }
        });
        mRecyclerView.setHasFixedSize(true);
        isViewInitiated = true;
        myAdapter=new MyAdapter();
        mRecyclerView.setAdapter(myAdapter);
        prepareFetchData();
    }
    //设置Fragment可见状态
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        prepareFetchData();
    }
    //获得Fragment可见状态
    @Override
    public boolean getUserVisibleHint() {
        return super.getUserVisibleHint();
    }

    public boolean prepareFetchData() {
        if (isVisibleToUser && isViewInitiated && (!isDataInitiated || false)) {
            onShowLoading();
            presenter.doLoadData(mParam);
            isDataInitiated = true;
            return true;
        }
        return false;
    }

    @Override
    public void onShowLoading() {
        pd = ProgressDialog.show(getActivity(), "温馨提示", "加载中...");
    }

    @Override
    public void onHideLoading() {
        //ToastUtil.showToast(getActivity(),"加载完成");
        EventBus.getDefault().post(new FirstEvent("加载完成"));
        if(null!=pd){
            pd.dismiss();
        }
        smartLayout.finishRefresh();
        smartLayout.finishLoadmore();
    }

    @Override
    public void onShowNetError() {
        //ToastUtil.showToast(getActivity(),"网络不给力");
        EventBus.getDefault().post(new FirstEvent("网络不给力"));
        myAdapter.notifyDataSetChanged();
//        getActivity().runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                myAdapter.notifyDataSetChanged();
//            }
//        });
    }

    @Override
    public void setPresenter(IWeather.Presenter presenter) {
        if(null==presenter){
            this.presenter=new WeatherPresenter(this);
        }
    }

    @Override
    public void onSetAdapter(List<Weather> list) {
        this.list=list;
        myAdapter.notifyDataSetChanged();
    }

    @Override
    public void onShowNoMore() {
        //ToastUtil.showToast(getActivity(),"没有更多数据");
        EventBus.getDefault().post(new FirstEvent("没有更多数据"));
        myAdapter.notifyDataSetChanged();
        //smartLayout.finishRefresh();
        //refreshLayout.setRefreshing(false);
//        getActivity().runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                myAdapter.notifyDataSetChanged();
//            }
//        });
    }

    class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
        @Override
        public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyAdapter.MyViewHolder viewHolder;
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_wether, parent, false);
            viewHolder=new MyAdapter.MyViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            MyAdapter.MyViewHolder mh = (MyAdapter.MyViewHolder) holder;
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
