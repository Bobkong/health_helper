package com.example.bob.health_helper.Community.fragment;

import com.example.bob.health_helper.Bean.Answer;
import com.example.bob.health_helper.Bean.Question;
import com.example.bob.health_helper.Community.adapter.LoadingMoreAdapter;
import com.example.bob.health_helper.Community.adapter.NewAnsweredQuestionListAdapter;
import com.example.bob.health_helper.Community.contract.NewAnsweredQuestionContract;
import com.example.bob.health_helper.Community.presenter.NewAnsweredQuestionPresenter;

import java.util.ArrayList;
import java.util.List;

public class NewAnsweredQuestionFragment extends BaseRefreshableListFragment<NewAnsweredQuestionContract.Presenter, Answer>
        implements NewAnsweredQuestionContract.View {

    private List<Answer> datas=new ArrayList<>();

    @Override
    protected LoadingMoreAdapter<Answer> createAdapter() {
        return new NewAnsweredQuestionListAdapter(datas);
    }

    @Override
    protected NewAnsweredQuestionContract.Presenter bindPresenter() {
        return new NewAnsweredQuestionPresenter();
    }

    @Override
    protected void startRefresh() {

    }

    @Override
    protected void startLoadMore() {

    }

    @Override
    public void onLoadNewAnsweredQuestionSuccess(List<Question> datas) {

    }

    @Override
    public void onLoadNewAnsweredQuestionFailed() {

    }

    @Override
    public void onLoadMoreNewAnsweredQuestionSuccess() {

    }

    @Override
    public void onLoadMoreNewAnsweredQuestionFailed() {

    }
}
