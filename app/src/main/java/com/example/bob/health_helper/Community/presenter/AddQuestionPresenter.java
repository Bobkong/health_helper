package com.example.bob.health_helper.Community.presenter;

import com.example.bob.health_helper.Base.BaseMvpPresenter;
import com.example.bob.health_helper.Community.contract.AddQuestionContract;
import com.example.bob.health_helper.NetService.Api.QuestionService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class AddQuestionPresenter extends BaseMvpPresenter<AddQuestionContract.View>
        implements AddQuestionContract.Presenter {

    @Override
    public void publishQuestion(String title, String descriiption,String uid) {
        Disposable disposable=QuestionService.getInstance().publishQuestion(title,descriiption,uid)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(datas->mView.onPublishQuestionSuccess(),
                        throwable -> mView.onPublishQuestionFailed());
        addSubscribe(disposable);
    }
}
