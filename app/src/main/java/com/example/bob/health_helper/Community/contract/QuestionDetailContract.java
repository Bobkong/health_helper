package com.example.bob.health_helper.Community.contract;

import com.example.bob.health_helper.Base.BaseMvpContract;
import com.example.bob.health_helper.Bean.Answer;

import java.util.List;

public interface QuestionDetailContract extends BaseMvpContract {
    interface View extends BaseView{
        //最新回答
        void onLoadRecentAnswerSuccess(List<Answer> answers);
        void onLoadRecentAnswerFailed();
        void onLoadMoreRecentAnswerSuccess(List<Answer> answers);
        void onLoadMoreRecentAnswerFailed();

        //热门回答
        void onLoadHotAnswerSuccess(List<Answer> answers);
        void onLoadHotAnswerFailed();
        void onLoadMoreHotAnswerSuccess(List<Answer> answers);
        void onLoadMoreHotAnswerFailed();
    }
    interface Presenter extends BasePresenter<View>{
        //最新回答
        void LoadRecentAnswer();
        void LoadMoreRecentAnswer();

        //热门回答
        void LoadHotAnswer();
        void LoadMoreHotAnswer();

    }
}
