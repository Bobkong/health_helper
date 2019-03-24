package com.example.bob.health_helper.Community.presenter;

import com.example.bob.health_helper.Base.BaseMvpPresenter;
import com.example.bob.health_helper.Community.contract.NewAnsweredQuestionContract;
import com.example.bob.health_helper.NetService.Api.AnswerService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class NewAnsweredQuestionPresenter extends BaseMvpPresenter<NewAnsweredQuestionContract.View>
        implements NewAnsweredQuestionContract.Presenter{
    private int curPage=0;
    private boolean hasMore=false;
    @Override
    public void loadNewAnsweredQuestion() {
        curPage=0;
        Disposable disposable=AnswerService.getInstance().getNewestAnswers(curPage++)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(datas -> {
                    hasMore=!(datas.size()<10);
                    mView.onLoadNewAnsweredQuestionSuccess(datas,hasMore);
                        },
                        throwable ->mView.onLoadNewAnsweredQuestionFailed(throwable.getMessage()));
        addSubscribe(disposable);
    }

    @Override
    public void loadMoreNewAnsweredQuestion() {
        if(hasMore){
            Disposable disposable=AnswerService.getInstance().getNewestAnswers(curPage++)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(datas -> {
                hasMore=!(datas.size()<10);
                mView.onLoadMoreNewAnsweredQuestionSuccess(datas,hasMore);
                    },
                    throwable ->mView.onLoadMoreNewAnsweredQuestionFailed(throwable.getMessage()));
            addSubscribe(disposable);
        }
    }

    @Override
    public void Like(String uid, int answerId) {
        Disposable disposable=AnswerService.getInstance().publishLike(uid,answerId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(datas->mView.onLikeSuccess(datas),
                        throwable -> mView.onLikeFailed(throwable.getMessage()));
        addSubscribe(disposable);
    }

    @Override
    public void CancelLike(String uid, int answerId) {
        Disposable disposable=AnswerService.getInstance().publishUnlike(uid,answerId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(datas->mView.onCancelLikeSuccess(datas),
                        throwable -> mView.onCancelLikeFailed(throwable.getMessage()));
        addSubscribe(disposable);
    }
}
