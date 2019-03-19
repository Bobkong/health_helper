package com.example.bob.health_helper.Community.presenter;

import com.example.bob.health_helper.Base.BaseMvpPresenter;
import com.example.bob.health_helper.Community.contract.QuestionDetailContract;
import com.example.bob.health_helper.NetService.Api.AnswerService;
import com.example.bob.health_helper.NetService.Api.QuestionService;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class QuestionDetailPresenter extends BaseMvpPresenter<QuestionDetailContract.View>
            implements QuestionDetailContract.Presenter{
    private int curPage=0;
    @Override
    public void LoadRecentAnswer(int questionId) {
        curPage=0;
        AnswerService.getInstance().getRecentAnswers(questionId,curPage++)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(datas -> mView.onLoadAnswerSuccess(datas,!(datas.size()==0||datas==null)),
                        throwable ->mView.onLoadAnswerFailed());
    }

    @Override
    public void LoadMoreRecentAnswer(int questionId) {
        AnswerService.getInstance().getRecentAnswers(questionId,curPage++)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(datas -> mView.onLoadMoreAnswerSuccess(datas,!(datas.size()==0||datas==null)),
                        throwable ->mView.onLoadMoreAnswerFailed());
    }

    @Override
    public void LoadHotAnswer(int questionId) {
        curPage=0;
        AnswerService.getInstance().getHotAnswers(questionId,curPage++)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(datas -> mView.onLoadAnswerSuccess(datas,!(datas.size()==0||datas==null)),
                        throwable ->mView.onLoadAnswerFailed());
    }

    @Override
    public void LoadMoreHotAnswer(int questionId) {
        AnswerService.getInstance().getHotAnswers(questionId,curPage++)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(datas -> mView.onLoadMoreAnswerSuccess(datas,!(datas.size()==0||datas==null)),
                        throwable ->mView.onLoadMoreAnswerFailed());
    }

    @Override
    public void Favorite(String uid, int questionId) {
        QuestionService.getInstance().publishFavorite(uid,questionId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(datas ->mView.onFavoriteSuccess(datas),
                        throwable ->mView.onFavoriteFailed());
    }

    @Override
    public void CancelFavorite(String uid, int questionId) {
        QuestionService.getInstance().publishUnfavorite(uid,questionId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(datas -> mView.onCancelFavoriteSuccess(datas),
                        throwable->mView.onCancelFavoriteFailed());
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
