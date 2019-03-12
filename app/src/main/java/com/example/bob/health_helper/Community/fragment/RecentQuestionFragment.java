package com.example.bob.health_helper.Community.fragment;
import com.example.bob.health_helper.Bean.Question;
import com.example.bob.health_helper.Community.adapter.LoadingMoreAdapter;
import com.example.bob.health_helper.Community.adapter.QuestionListAdapter;
import com.example.bob.health_helper.Community.contract.RecentQuestionContract;
import com.example.bob.health_helper.Community.presenter.RecentQuestionPresenter;
import java.util.ArrayList;
import java.util.List;

public class RecentQuestionFragment extends BaseRefreshableListFragment<RecentQuestionContract.Presenter,Question>
        implements RecentQuestionContract.View{

    private List<Question> recentQuestionList=new ArrayList<>();

    @Override
    protected RecentQuestionContract.Presenter bindPresenter() {
        return new RecentQuestionPresenter();
    }

    @Override
    protected LoadingMoreAdapter createAdapter() {
        return new QuestionListAdapter(recentQuestionList);
    }

    @Override
    protected void startRefresh() {
        mPresenter.loadRecentQuestion();
    }

    @Override
    protected void startLoadMore() {
        mPresenter.loadMoreRecentQuestion();
    }

    @Override
    public void onLoadRecentQuestionSuccess(List<Question> datas) {

    }

    @Override
    public void onLoadRecentQuestionFailed() {

    }

    @Override
    public void onLoadMoreRecentQuestionSuccess() {

    }

    @Override
    public void onLoadMoreRecentQuestionFailed() {

    }
}