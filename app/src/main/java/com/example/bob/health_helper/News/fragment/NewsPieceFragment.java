package com.example.bob.health_helper.News.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.bob.health_helper.Bean.News;
import com.example.bob.health_helper.Community.adapter.LoadingMoreAdapter;
import com.example.bob.health_helper.Community.fragment.BaseRefreshableListFragment;
import com.example.bob.health_helper.News.adapter.NewsListAdapter;
import com.example.bob.health_helper.News.contract.NewsPieceContract;
import com.example.bob.health_helper.News.presenter.NewsPiecePresenter;
import com.example.bob.health_helper.R;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;


public class NewsPieceFragment extends BaseRefreshableListFragment<NewsPieceContract.Presenter, News>
            implements NewsPieceContract.View {

    private int tag;
    private static final String NEWS_TYPE="news_type";
    private List<News> newsList=new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取碎片携带的tag参数
        tag=getArguments().getInt(NEWS_TYPE,-1);
    }

    public static NewsPieceFragment newInstance(int tag){
        //创建碎片时添加参数以便后面加载数据
        NewsPieceFragment newsPieceFragment=new NewsPieceFragment();
        Bundle bundle=new Bundle();
        bundle.putInt(NEWS_TYPE,tag);
        newsPieceFragment.setArguments(bundle);
        return newsPieceFragment;
    }

    @Override
    protected LoadingMoreAdapter<News> createAdapter() {
        return new NewsListAdapter(newsList);
    }

    @Override
    protected NewsPieceContract.Presenter bindPresenter() {
        return new NewsPiecePresenter();
    }

    @Override
    protected void startRefresh() {
        mPresenter.loadNewsByTag(tag);
    }

    @Override
    protected void startLoadMore() {
        mPresenter.loadMoreNewsByTag(tag);
    }

    @Override
    public void onLoadNewsSuccess(List<News> datas,boolean hasMore) {
        swipeRefreshLayout.setRefreshing(false);
        newsList=datas;
        adapter.updateDatas(newsList,hasMore);
    }

    @Override
    public void onLoadNewsFailed(String msg) {
        swipeRefreshLayout.setRefreshing(false);
        showTips(msg);
    }

    @Override
    public void onLoadMoreNewsSuccess(List<News> datas,boolean hasMore) {
        swipeRefreshLayout.setRefreshing(false);
        newsList.addAll(datas);
        adapter.updateDatas(newsList,hasMore);
    }

    @Override
    public void onLoadMoreNewsFailed(String msg) {
        showTips(msg);
    }

}
