package com.example.bob.health_helper.Community.contract;

import com.example.bob.health_helper.Base.BaseMvpContract;
import com.example.bob.health_helper.Bean.Answer;

import java.util.List;

public interface NewAnsweredQuestionContract extends BaseMvpContract {
    interface View extends BaseView{
        void onLoadNewAnsweredQuestionSuccess(List<Answer> datas, boolean hasMore);
        void onLoadNewAnsweredQuestionFailed();
        void onLoadMoreNewAnsweredQuestionSuccess(List<Answer> datas,boolean hasMore);
        void onLoadMoreNewAnsweredQuestionFailed();
    }

    interface Presenter extends BasePresenter<View>{
        void loadNewAnsweredQuestion();
        void loadMoreNewAnsweredQuestion();
    }
}
