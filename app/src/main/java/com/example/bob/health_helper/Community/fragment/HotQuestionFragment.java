package com.example.bob.health_helper.Community.fragment;

import com.example.bob.health_helper.Bean.Question;
import com.example.bob.health_helper.Community.adapter.LoadingMoreAdapter;
import com.example.bob.health_helper.Community.adapter.QuestionListAdapter;
import com.example.bob.health_helper.Community.contract.HotQuestionContract;
import com.example.bob.health_helper.Community.presenter.HotQuestionPresenter;

import java.util.ArrayList;
import java.util.List;

public class HotQuestionFragment extends BaseRefreshableListFragment<HotQuestionContract.Presenter, Question>
        implements HotQuestionContract.View  {
    private List<Question> datas=new ArrayList<>();

    @Override
    protected HotQuestionContract.Presenter bindPresenter() {
        return new HotQuestionPresenter();
    }

    @Override
    protected LoadingMoreAdapter<Question> createAdapter() {
        return new QuestionListAdapter(datas);
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
    public void onLoadHotQuestionSuccess(List<Question> datas) {

    }

    @Override
    public void onLoadHotQuestionFailed() {

    }

    @Override
    public void onLoadMoreHotQuestionSuccess() {

    }

    @Override
    public void onLoadMoreHotQuestionFailed() {

    }
}
