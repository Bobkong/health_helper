package com.example.bob.health_helper.Community.presenter;

import com.example.bob.health_helper.Base.BaseMvpPresenter;
import com.example.bob.health_helper.Community.contract.AddAnswerContract;

public class AddAnswerPresenter extends BaseMvpPresenter<AddAnswerContract.View>
        implements AddAnswerContract.Presenter{

    @Override
    public void publishAnswer(String answer, int questionId, String authorId) {
        mView.onPublishAnswerSuccess();
        //to do
    }
}
