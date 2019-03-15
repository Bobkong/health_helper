package com.example.bob.health_helper.Community.contract;

import com.example.bob.health_helper.Base.BaseMvpContract;
import com.example.bob.health_helper.Bean.Question;

import java.util.List;

public interface RecentQuestionContract extends BaseMvpContract {
    interface View extends BaseView{
        void onLoadRecentQuestionSuccess(List<Question> datas,boolean hasMore);
        void onLoadRecentQuestionFailed();
        void onLoadMoreRecentQuestionSuccess(List<Question> datas,boolean hasMore);
        void onLoadMoreRecentQuestionFailed();
    }

    interface Presenter extends BasePresenter<View>{
        void loadRecentQuestion();
        void loadMoreRecentQuestion();
    }
}
