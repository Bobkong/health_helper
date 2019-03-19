package com.example.bob.health_helper.Community.presenter;

import com.example.bob.health_helper.Base.BaseMvpPresenter;
import com.example.bob.health_helper.Community.contract.CommentContract;
import com.example.bob.health_helper.NetService.Api.CommentService;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class CommentPresenter extends BaseMvpPresenter<CommentContract.View>
            implements CommentContract.Presenter{
    private int curPage=0;
    @Override
    public void loadComments(int answerId) {
        curPage=0;
        CommentService.getInstance().getComments(answerId,curPage++)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(datas -> mView.onLoadCommentsSuccess(datas,!(datas.size()==0||datas==null)),
                        throwable -> mView.onLoadCommentFailed());
    }

    @Override
    public void loadMoreComments(int answerId) {
        CommentService.getInstance().getComments(answerId,curPage++)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(datas -> mView.onLoadMoreCommentsSuccess(datas,!(datas.size()==0||datas==null)),
                        throwable -> mView.onLoadMoreCommentFailed());
    }

    @Override
    public void sendComment(int answerId,String comment,String uid) {
        CommentService.getInstance().publishComment(comment,uid,answerId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(datas->mView.onSendCommentSuccess(),
                        throwable -> mView.onSendCommentFailed());
    }
}
