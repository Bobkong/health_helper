package com.example.bob.health_helper.Community.presenter;

import com.example.bob.health_helper.Base.BaseMvpPresenter;
import com.example.bob.health_helper.Community.contract.QuestionDetailContract;
import com.example.bob.health_helper.NetService.Api.AnswerService;
import com.example.bob.health_helper.NetService.Api.QuestionService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class QuestionDetailPresenter extends BaseMvpPresenter<QuestionDetailContract.View>
            implements QuestionDetailContract.Presenter{
    private int curPage=0;
    private boolean hasMore=false;
    @Override
    public void LoadRecentAnswer(int questionId) {
        curPage=0;
        Disposable disposable=AnswerService.getInstance().getRecentAnswers(questionId,curPage++)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(datas -> {
                    hasMore=!(datas.size()<10);
                    mView.onLoadAnswerSuccess(datas,hasMore);
                        },
                        throwable ->mView.onLoadAnswerFailed(throwable.getMessage()));
        addSubscribe(disposable);
    }

    @Override
    public void LoadMoreRecentAnswer(int questionId) {
       if(hasMore){
           Disposable disposable=AnswerService.getInstance().getRecentAnswers(questionId,curPage++)
                   .observeOn(AndroidSchedulers.mainThread())
                   .subscribe(datas -> {
                       hasMore=!(datas.size()<10);
                       mView.onLoadMoreAnswerSuccess(datas,hasMore);
                           },
                           throwable ->mView.onLoadMoreAnswerFailed(throwable.getMessage()));
           addSubscribe(disposable);
       }
    }

    @Override
    public void LoadHotAnswer(int questionId) {
        curPage=0;
        Disposable disposable=AnswerService.getInstance().getHotAnswers(questionId,curPage++)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(datas -> {
                            hasMore=!(datas.size()<10);
                            mView.onLoadAnswerSuccess(datas,hasMore);
                        },
                        throwable ->mView.onLoadAnswerFailed(throwable.getMessage()));
        addSubscribe(disposable);
    }

    @Override
    public void LoadMoreHotAnswer(int questionId) {
        if(hasMore){
            Disposable disposable=AnswerService.getInstance().getHotAnswers(questionId,curPage++)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(datas -> {
                                hasMore=!(datas.size()<10);
                                mView.onLoadMoreAnswerSuccess(datas,hasMore);
                            },
                            throwable ->mView.onLoadMoreAnswerFailed(throwable.getMessage()));
            addSubscribe(disposable);
        }
    }

    @Override
    public void Favorite(String uid, int questionId) {
        Disposable disposable=QuestionService.getInstance().publishFavorite(uid,questionId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(datas ->mView.onFavoriteSuccess(datas),
                        throwable ->mView.onFavoriteFailed(throwable.getMessage()));
        addSubscribe(disposable);
    }

    @Override
    public void CancelFavorite(String uid, int questionId) {
        Disposable disposable=QuestionService.getInstance().publishUnfavorite(uid,questionId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(datas -> mView.onCancelFavoriteSuccess(datas),
                        throwable->mView.onCancelFavoriteFailed(throwable.getMessage()));
        addSubscribe(disposable);
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
