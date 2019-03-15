package com.example.bob.health_helper.Community.fragment;

import com.example.bob.health_helper.Data.Bean.Answer;
import com.example.bob.health_helper.Community.adapter.LoadingMoreAdapter;
import com.example.bob.health_helper.Community.adapter.NewAnsweredQuestionListAdapter;
import com.example.bob.health_helper.Community.contract.NewAnsweredQuestionContract;
import com.example.bob.health_helper.Community.presenter.NewAnsweredQuestionPresenter;
import com.example.bob.health_helper.R;

import java.util.ArrayList;
import java.util.List;

public class NewAnsweredQuestionFragment extends BaseRefreshableListFragment<NewAnsweredQuestionContract.Presenter, Answer>
        implements NewAnsweredQuestionContract.View {

    private List<Answer> newAnsweredQuestionList=new ArrayList<>();

    @Override
    protected LoadingMoreAdapter<Answer> createAdapter() {
        return new NewAnsweredQuestionListAdapter(newAnsweredQuestionList);
    }

    @Override
    protected NewAnsweredQuestionContract.Presenter bindPresenter() {
        return new NewAnsweredQuestionPresenter();
    }

    @Override
    protected void startRefresh() {
        mPresenter.loadNewAnsweredQuestion();
    }

    @Override
    protected void startLoadMore() {
        mPresenter.loadMoreNewAnsweredQuestion();
    }

    @Override
    public void onLoadNewAnsweredQuestionSuccess(List<Answer> datas,boolean hasMore) {
        swipeRefreshLayout.setRefreshing(false);
        newAnsweredQuestionList=datas;
        adapter.updateDatas(newAnsweredQuestionList,hasMore);
    }

    @Override
    public void onLoadNewAnsweredQuestionFailed() {
        swipeRefreshLayout.setRefreshing(false);
        showTips(getString(R.string.network_error));
    }

    @Override
    public void onLoadMoreNewAnsweredQuestionSuccess(List<Answer> datas,boolean hasMore) {
        newAnsweredQuestionList.addAll(datas);
        adapter.updateDatas(newAnsweredQuestionList,hasMore);
    }

    @Override
    public void onLoadMoreNewAnsweredQuestionFailed() {
        showTips(getString(R.string.network_error));
    }
}
