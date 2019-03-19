package com.example.bob.health_helper.Community.presenter;

import com.example.bob.health_helper.Base.BaseMvpPresenter;
import com.example.bob.health_helper.Community.contract.NewAnsweredQuestionContract;
import com.example.bob.health_helper.NetService.Api.AnswerService;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class NewAnsweredQuestionPresenter extends BaseMvpPresenter<NewAnsweredQuestionContract.View>
        implements NewAnsweredQuestionContract.Presenter{
    private int curPage=0;
    @Override
    public void loadNewAnsweredQuestion() {
        curPage=0;
        AnswerService.getInstance().getNewestAnswers(curPage++)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(datas -> mView.onLoadNewAnsweredQuestionSuccess(datas,!(datas.size()==0||datas==null)),
                        throwable ->mView.onLoadNewAnsweredQuestionFailed());
    }

    @Override
    public void loadMoreNewAnsweredQuestion() {
        AnswerService.getInstance().getNewestAnswers(curPage++)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(datas -> mView.onLoadMoreNewAnsweredQuestionSuccess(datas,!(datas.size()==0||datas==null)),
                        throwable ->mView.onLoadMoreNewAnsweredQuestionFailed());
    }

    @Override
    public void Like(String uid, int answerId) {
        AnswerService.getInstance().publishLike(uid,answerId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(datas->mView.onLikeSuccess(datas),
                        throwable -> mView.onLikeFailed());
    }

    @Override
    public void CancelLike(String uid, int answerId) {
        AnswerService.getInstance().publishUnlike(uid,answerId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(datas->mView.onCancelLikeSuccess(datas),
                        throwable -> mView.onCancelLikeFailed());
    }
}
