package com.example.bob.health_helper.Community.presenter;

import com.example.bob.health_helper.Base.BaseMvpPresenter;
import com.example.bob.health_helper.Community.contract.RecentQuestionContract;
import com.example.bob.health_helper.NetService.Api.QuestionService;
import com.orhanobut.logger.Logger;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class RecentQuestionPresenter extends BaseMvpPresenter<RecentQuestionContract.View>
        implements RecentQuestionContract.Presenter {
    private int curPage=0;
    private boolean hasMore=false;
    @Override
    public void loadRecentQuestion() {
        curPage=0;
        Disposable disposable=QuestionService.getInstance().getRecentQuestions(curPage++)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(datas -> {
                    hasMore=!(datas.size()<10);
                    mView.onLoadRecentQuestionSuccess(datas,hasMore);
                        }, throwable ->mView.onLoadRecentQuestionFailed(throwable.getMessage()));
        addSubscribe(disposable);
    }

    @Override
    public void loadMoreRecentQuestion() {
        if(hasMore){
            Disposable disposable=QuestionService.getInstance().getRecentQuestions(curPage++)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(datas -> {
                        hasMore=!(datas.size()<10);
                        mView.onLoadMoreRecentQuestionSuccess(datas,hasMore);
                                Logger.e(datas.toString());
                            },
                            throwable ->mView.onLoadMoreRecentQuestionFailed(throwable.getMessage()));
            addSubscribe(disposable);
        }
    }
}
