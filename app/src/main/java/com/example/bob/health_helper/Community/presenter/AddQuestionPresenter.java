package com.example.bob.health_helper.Community.presenter;

import com.example.bob.health_helper.Base.BaseMvpPresenter;
import com.example.bob.health_helper.Community.contract.AddQuestionContract;
import com.example.bob.health_helper.Util.SharedPreferenceUtil;

public class AddQuestionPresenter extends BaseMvpPresenter<AddQuestionContract.View>
        implements AddQuestionContract.Presenter {

    @Override
    public void publishQuestion(String title, String descriiption) {
        SharedPreferenceUtil.getUser().getUid();


    }
}
