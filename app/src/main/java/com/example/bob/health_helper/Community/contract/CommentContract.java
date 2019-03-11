package com.example.bob.health_helper.Community.contract;

import com.example.bob.health_helper.Base.BaseMvpContract;
import com.example.bob.health_helper.Bean.Comment;

import java.util.List;

public interface CommentContract extends BaseMvpContract {
    interface View extends BaseView{
        void onLoadCommentsSuccess(List<Comment> comments);
        void onLoadCommentFailed();

        void onLoadMoreCommentsSuccess();
        void onLoadMoreCommentFailed();

        void onSendCommentSuccess();
        void onSendCommentFailed();
    }

    interface Presenter extends BasePresenter<View>{
        void loadComments(String answerId);
        void loadMoreComments(String answerId);
        void sendComment(String answer, String replayTo, String comment);
    }
}
