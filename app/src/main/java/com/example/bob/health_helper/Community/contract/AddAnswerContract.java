package com.example.bob.health_helper.Community.contract;

import com.example.bob.health_helper.Base.BaseMvpContract;

public interface AddAnswerContract extends BaseMvpContract {
    interface View extends BaseView {
        void onPublishAnswerSuccess();
        void onPublishAnswerFailed();
    }
    interface Presenter extends BasePresenter<View>{
        void publishAnswer(String answer,int questionId,String authorId);
    }
}
