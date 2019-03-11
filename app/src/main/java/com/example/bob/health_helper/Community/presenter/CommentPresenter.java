package com.example.bob.health_helper.Community.presenter;

import com.example.bob.health_helper.Base.BaseMvpPresenter;
import com.example.bob.health_helper.Community.contract.CommentContract;

public class CommentPresenter extends BaseMvpPresenter<CommentContract.View>
            implements CommentContract.Presenter{
    @Override
    public void loadComments(String answerId) {

    }

    @Override
    public void loadMoreComments(String answerId) {

    }

    @Override
    public void sendComment(String answer, String replayTo, String comment) {

    }
}
