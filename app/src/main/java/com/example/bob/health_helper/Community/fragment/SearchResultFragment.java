package com.example.bob.health_helper.Community.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.bob.health_helper.Community.adapter.LoadingMoreAdapter;
import com.example.bob.health_helper.Community.adapter.QuestionListAdapter;
import com.example.bob.health_helper.Community.contract.SearchResultContract;
import com.example.bob.health_helper.Community.presenter.SearchResultPresenter;
import com.example.bob.health_helper.Bean.Question;
import com.example.bob.health_helper.R;

import java.util.ArrayList;
import java.util.List;

public class SearchResultFragment extends BaseRefreshableListFragment<SearchResultContract.Presenter,Question>
        implements SearchResultContract.View{

    private String to_search;
    private List<Question> questionList=new ArrayList<>();

    @Override
    protected LoadingMoreAdapter<Question> createAdapter() {
        return new QuestionListAdapter(questionList);
    }


    @Override
    protected SearchResultContract.Presenter bindPresenter() {
        return new SearchResultPresenter();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        to_search=getArguments().getString("search_question");
    }

    @Override
    protected void startRefresh() {
        mPresenter.loadSearchResult(to_search);
    }

    @Override
    protected void startLoadMore() {
        mPresenter.loadMoreSearchResult(to_search);
    }

    @Override
    public void onLoadSearchResultSuccess(List<Question> datas, boolean hasMore) {
        swipeRefreshLayout.setRefreshing(false);
        this.questionList=datas;
        adapter.updateDatas(questionList,hasMore);
    }

    @Override
    public void onLoadSearchResultFailed(String msg) {
        swipeRefreshLayout.setRefreshing(false);
        showTips(msg);
    }

    @Override
    public void onLoadMoreSearchResultSuccess(List<Question> datas, boolean hasMore) {
        this.questionList.addAll(datas);
        adapter.updateDatas(questionList,hasMore);
    }

    @Override
    public void onLoadMoreSearchResultFailed(String msg) {
        showTips(msg);
    }
}
