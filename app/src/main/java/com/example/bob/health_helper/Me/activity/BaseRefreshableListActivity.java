package com.example.bob.health_helper.Me.activity;

import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.bob.health_helper.Base.AppConstant;
import com.example.bob.health_helper.R;

import butterknife.BindView;

public abstract class BaseRefreshableListActivity extends AppCompatActivity {

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.data_list)
    RecyclerView dataList;

    //子类需要实现的方法
    abstract void startRefresh();//刷新
    abstract void startLoadMoreData();//加载更多

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_refreshable_list);//统一布局

        //actionBar统一初始化
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //下拉刷新
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                startRefresh();
            }
        });

        //上拉分页加载
       dataList.addOnScrollListener(new RecyclerView.OnScrollListener() {
           @Override
           public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
               super.onScrollStateChanged(recyclerView, newState);
               if(newState==RecyclerView.SCROLL_STATE_IDLE&&shouldLoadMore())
                   startLoadMoreData();
           }
       });
    }

    //屏幕中最后一个条目是数据列表最后一项且数据量超过默认数目时-加载更多
    private boolean shouldLoadMore(){
        LinearLayoutManager linearLayoutManager=(LinearLayoutManager)dataList.getLayoutManager();
        return (linearLayoutManager.findLastVisibleItemPosition()==dataList.getAdapter().getItemCount()-1
                && dataList.getAdapter().getItemCount()>= AppConstant.DEFAULT_PAGE_SIZE);
    }
}
