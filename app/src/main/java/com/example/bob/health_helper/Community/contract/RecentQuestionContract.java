package com.example.bob.health_helper.Community.contract;

import com.example.bob.health_helper.Base.BaseMvpContract;
import com.example.bob.health_helper.Bean.Question;

import java.util.List;

public interface RecentQuestionContract extends BaseMvpContract {
    interface View extends BaseView{
        void onLoadRecentQuestionSuccess(List<Question> datas);
        void onLoadRecentQuestionFailed();
        void onLoadMoreRecentQuestionSuccess();
        void onLoadMoreRecentQuestionFailed();
    }

    interface Presenter extends BasePresenter<View>{
        void loadRecentQuestion();
        void loadMoreRecentQuestion();
    }
}
