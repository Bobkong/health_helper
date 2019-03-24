package com.example.bob.health_helper.Community.contract;

import com.example.bob.health_helper.Base.BaseMvpContract;
import com.example.bob.health_helper.Bean.Question;

import java.util.List;

public interface HotQuestionContract extends BaseMvpContract {
    interface View extends BaseView{
        void onLoadHotQuestionSuccess(List<Question> datas,boolean hasMore);
        void onLoadHotQuestionFailed(String msg);
        void onLoadMoreHotQuestionSuccess(List<Question> datas,boolean hasMore);
        void onLoadMoreHotQuestionFailed(String msg);
    }

    interface Presenter extends BasePresenter<View>{
        void loadHotQuestion();
        void loadMoreHotQuestion();
    }
}
