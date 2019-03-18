package com.example.bob.health_helper.Community.presenter;

import com.example.bob.health_helper.Base.BaseMvpPresenter;
import com.example.bob.health_helper.Community.contract.RecentQuestionContract;
import com.example.bob.health_helper.NetService.Api.QuestionService;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class RecentQuestionPresenter extends BaseMvpPresenter<RecentQuestionContract.View>
        implements RecentQuestionContract.Presenter {
    private int curPage=0;
    @Override
    public void loadRecentQuestion() {
        curPage=0;
        QuestionService.getInstance().getRecentQuestions(curPage++)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(datas -> mView.onLoadRecentQuestionSuccess(datas,!(datas.size()==0||datas==null)),
                        throwable ->mView.onLoadRecentQuestionFailed());
    }

    @Override
    public void loadMoreRecentQuestion() {
        QuestionService.getInstance().getRecentQuestions(curPage++)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(datas -> mView.onLoadMoreRecentQuestionSuccess(datas,!(datas.size()==0||datas==null)),
                        throwable ->mView.onLoadMoreRecentQuestionFailed());
    }
}
