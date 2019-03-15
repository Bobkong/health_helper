package com.example.bob.health_helper.Community.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bob.health_helper.Base.AppConstant;
import com.example.bob.health_helper.Base.BaseMvpContract;
import com.example.bob.health_helper.Base.BaseMvpFragment;
import com.example.bob.health_helper.Community.adapter.LoadingMoreAdapter;
import com.example.bob.health_helper.R;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public abstract class BaseRefreshableListFragment<T extends BaseMvpContract.BasePresenter,T1> extends BaseMvpFragment<T> {
    @BindView(R.id.refresh_layout)
    public SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.data_list)
    public RecyclerView dataList;

    protected LoadingMoreAdapter<T1> adapter;

    //子类需要实现的方法
    protected abstract LoadingMoreAdapter<T1> createAdapter();
    protected abstract void startRefresh();
    protected abstract void startLoadMore();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.fragment_refreshablelist,null);
        ButterKnife.bind(this,root);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter=createAdapter();

        dataList.setLayoutManager(new LinearLayoutManager(getActivity()));
        dataList.setAdapter(adapter);
        dataList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                /*new state:SCROLL_STATE_IDLE 滑动静止状态 */
                //分页加载
                if(newState==RecyclerView.SCROLL_STATE_IDLE&&shouldLoadMore())
                    startLoadMore();
            }
        });

        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);//刷新圈圈颜色
        swipeRefreshLayout.setProgressViewOffset(true,200,300);//刷新圈圈出来的位置
        swipeRefreshLayout.setRefreshing(true);//刚进入时刷新
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                startRefresh();//刷新逻辑的实现
            }
        });

        startRefresh();
    }

    //屏幕中最后一个条目是数据列表最后一项时-加载更多
    private boolean shouldLoadMore(){
        LinearLayoutManager linearLayoutManager=(LinearLayoutManager)dataList.getLayoutManager();
        return (linearLayoutManager.findLastVisibleItemPosition()==dataList.getAdapter().getItemCount()-1
                && dataList.getAdapter().getItemCount()>= AppConstant.DEFAULT_PAGE_SIZE);
    }
}
