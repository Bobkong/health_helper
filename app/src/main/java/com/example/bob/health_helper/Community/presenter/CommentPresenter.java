package com.example.bob.health_helper.Community.presenter;

import com.example.bob.health_helper.Base.BaseMvpPresenter;
import com.example.bob.health_helper.Community.contract.CommentContract;
import com.example.bob.health_helper.NetService.Api.CommentService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class CommentPresenter extends BaseMvpPresenter<CommentContract.View>
            implements CommentContract.Presenter{
    private int curPage=0;
    private boolean hasMore=false;
    @Override
    public void loadComments(int answerId) {
        curPage=0;
        Disposable disposable=CommentService.getInstance().getComments(answerId,curPage++)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(datas -> {
                    hasMore=!(datas.size()<10);
                    mView.onLoadCommentsSuccess(datas,hasMore);
                        },
                        throwable -> mView.onLoadCommentFailed(throwable.getMessage()));
        addSubscribe(disposable);
    }

    @Override
    public void loadMoreComments(int answerId) {
        if(hasMore){
            Disposable disposable=CommentService.getInstance().getComments(answerId,curPage++)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(datas -> {
                        hasMore=!(datas.size()<10);
                        mView.onLoadMoreCommentsSuccess(datas,hasMore);
                            },
                            throwable -> mView.onLoadMoreCommentFailed(throwable.getMessage()));
            addSubscribe(disposable);
        }
    }

    @Override
    public void sendComment(int answerId,String comment,String uid) {
        Disposable disposable=CommentService.getInstance().publishComment(comment,uid,answerId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(datas->mView.onSendCommentSuccess(),
                        throwable -> mView.onSendCommentFailed());
        addSubscribe(disposable);
    }
}
