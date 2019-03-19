package com.example.bob.health_helper.Community.presenter;

import com.example.bob.health_helper.Base.BaseMvpPresenter;
import com.example.bob.health_helper.Community.contract.AddAnswerContract;
import com.example.bob.health_helper.NetService.Api.AnswerService;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class AddAnswerPresenter extends BaseMvpPresenter<AddAnswerContract.View>
        implements AddAnswerContract.Presenter{

    @Override
    public void publishAnswer(String answer, int questionId, String authorId) {
        AnswerService.getInstance().publishAnswer(answer,authorId,questionId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(datas->mView.onPublishAnswerSuccess(),
                        throwable -> mView.onPublishAnswerFailed());
    }
}
