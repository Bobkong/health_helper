package com.example.bob.health_helper.Community.presenter;

import com.example.bob.health_helper.Base.BaseMvpPresenter;
import com.example.bob.health_helper.Community.contract.QuestionDetailContract;

public class QuestionDetailPresenter extends BaseMvpPresenter<QuestionDetailContract.View>
            implements QuestionDetailContract.Presenter{
    @Override
    public void LoadRecentAnswer() {

    }

    @Override
    public void LoadMoreRecentAnswer() {

    }

    @Override
    public void LoadHotAnswer() {

    }

    @Override
    public void LoadMoreHotAnswer() {

    }

    @Override
    public void Favorite(String uid, int questionId) {

    }

    @Override
    public void CancelFavorite(String uid, int questionId) {

    }
}
