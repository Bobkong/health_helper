package com.example.bob.health_helper.Community.contract;

import com.example.bob.health_helper.Base.BaseMvpContract;
import com.example.bob.health_helper.Bean.Question;

import java.util.List;

public interface NewAnsweredQuestionContract extends BaseMvpContract {
    interface View extends BaseView{
        void onLoadNewAnsweredQuestionSuccess(List<Question> datas);
        void onLoadNewAnsweredQuestionFailed();
        void onLoadMoreNewAnsweredQuestionSuccess();
        void onLoadMoreNewAnsweredQuestionFailed();
    }

    interface Presenter extends BasePresenter<View>{
        void loadNewAnsweredQuestion();
        void loadMoreNewAnsweredQuestion();
    }
}
