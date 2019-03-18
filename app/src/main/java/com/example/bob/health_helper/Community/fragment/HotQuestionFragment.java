package com.example.bob.health_helper.Community.fragment;

import com.example.bob.health_helper.Bean.Question;
import com.example.bob.health_helper.Community.adapter.LoadingMoreAdapter;
import com.example.bob.health_helper.Community.adapter.QuestionListAdapter;
import com.example.bob.health_helper.Community.contract.HotQuestionContract;
import com.example.bob.health_helper.Community.presenter.HotQuestionPresenter;
import com.example.bob.health_helper.R;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

public class HotQuestionFragment extends BaseRefreshableListFragment<HotQuestionContract.Presenter, Question>
        implements HotQuestionContract.View  {
    private List<Question> hotQuestionList=new ArrayList<>();

    @Override
    protected HotQuestionContract.Presenter bindPresenter() {
        return new HotQuestionPresenter();
    }

    @Override
    protected LoadingMoreAdapter<Question> createAdapter() {
        return new QuestionListAdapter(hotQuestionList);
    }

    @Override
    protected void startRefresh() {
        mPresenter.loadHotQuestion();
    }

    @Override
    protected void startLoadMore() {
        mPresenter.loadMoreHotQuestion();
    }

    @Override
    public void onLoadHotQuestionSuccess(List<Question> datas,boolean hasMore) {
        swipeRefreshLayout.setRefreshing(false);
        this.hotQuestionList=datas;
        adapter.updateDatas(hotQuestionList,hasMore);
    }

    @Override
    public void onLoadHotQuestionFailed() {
        swipeRefreshLayout.setRefreshing(false);
        showTips(getString(R.string.network_error));
    }

    @Override
    public void onLoadMoreHotQuestionSuccess(List<Question> datas,boolean hasMore) {
        this.hotQuestionList.addAll(datas);
        adapter.updateDatas(hotQuestionList,hasMore);
    }

    @Override
    public void onLoadMoreHotQuestionFailed() {
        showTips(getString(R.string.network_error));
    }
}
