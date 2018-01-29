package com.wusir.modules.moive;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.wusir.util.ToastUtil;
import com.wusir.wuweather.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieItemFragment extends Fragment implements IMovie.View{
    private static final String ARG_START= "START",ARG_COUNT= "COUNT";
    private int start,count;
    @BindView(R.id.smartLayout)
    SmartRefreshLayout smartLayout;

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    private List<Movie> list = new ArrayList<>();
    private MovieAdapter myAdapter;
    protected boolean isViewInitiated;
    protected boolean isVisibleToUser;
    protected boolean isDataInitiated;
    private IMovie.Presenter presenter;
    private  ProgressDialog pd;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public static MovieItemFragment newInstance(int start,int count) {
        MovieItemFragment fragment = new MovieItemFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_START, start);
        args.putInt(ARG_COUNT, count);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            start= getArguments().getInt(ARG_START);
            count= getArguments().getInt(ARG_COUNT);
        }
        setPresenter(presenter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_movie_item, container, false);
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
                    presenter.doLoadData(start,count);
                    return;
                }
                //平滑的定位到指定项
                mRecyclerView.smoothScrollToPosition(0);
            }
        });
        smartLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                presenter.doLoadMoreData(start+10,count);
            }
        });
        mRecyclerView.setHasFixedSize(true);
        isViewInitiated = true;
//        myAdapter=new MovieAdapter(list,getContext());
//        mRecyclerView.setAdapter(myAdapter);
        prepareFetchData();
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        prepareFetchData();
    }

    public boolean prepareFetchData() {
        if (isVisibleToUser && isViewInitiated && (!isDataInitiated || false)) {
            onShowLoading();
            presenter.doLoadData(start,count);
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
        if(null!=pd){
            pd.dismiss();
        }
        smartLayout.finishRefresh();
        smartLayout.finishLoadmore();
    }

    @Override
    public void onShowNetError() {
        ToastUtil.showToast(getActivity(),"网络不给力");
        myAdapter.notifyDataSetChanged();
//        getActivity().runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                myAdapter.notifyDataSetChanged();
//            }
//        });
    }

    @Override
    public void setPresenter(IMovie.Presenter presenter) {
        if(null==presenter){
            this.presenter=new MoviePresenter(this);
        }
    }

    @Override
    public void onSetAdapter(List<Movie> list) {
        this.list=list;
        myAdapter=new MovieAdapter(list,getContext());
        mRecyclerView.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();
//        getActivity().runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                myAdapter.notifyDataSetChanged();
//            }
//        });
    }

    @Override
    public void onShowNoMore() {
        ToastUtil.showToast(getActivity(),"没有更多数据");
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
}
