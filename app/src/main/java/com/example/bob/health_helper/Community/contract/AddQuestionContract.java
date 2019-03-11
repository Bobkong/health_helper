package com.example.bob.health_helper.Community.contract;

import com.example.bob.health_helper.Base.BaseMvpContract;

public interface AddQuestionContract extends BaseMvpContract{
    interface View extends BaseView {
        void onPublishQuestionSuccess();
        void onPublishQuestionFailed();
    }
    interface Presenter extends BasePresenter<View>{
        void publishQuestion(String title,String descriiption);
    }
}
