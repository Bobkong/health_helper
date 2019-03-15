package com.example.bob.health_helper.Community.contract;

import com.example.bob.health_helper.Base.BaseMvpContract;
import com.example.bob.health_helper.Data.Bean.Comment;

import java.util.List;

public interface CommentContract extends BaseMvpContract {
    interface View extends BaseView{
        void onLoadCommentsSuccess(List<Comment> comments,boolean hasMore);
        void onLoadCommentFailed();

        void onLoadMoreCommentsSuccess(List<Comment> comments,boolean hasMore);
        void onLoadMoreCommentFailed();

        void onSendCommentSuccess();
        void onSendCommentFailed();
    }

    interface Presenter extends BasePresenter<View>{
        void loadComments(int answerId);
        void loadMoreComments(int answerId);
        void sendComment(int answerId,String comment,String uid);
    }
}
