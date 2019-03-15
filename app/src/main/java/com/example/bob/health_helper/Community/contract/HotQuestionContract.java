package com.example.bob.health_helper.Community.contract;

import com.example.bob.health_helper.Base.BaseMvpContract;
import com.example.bob.health_helper.Data.Bean.Question;

import java.util.List;

public interface HotQuestionContract extends BaseMvpContract {
    interface View extends BaseView{
        void onLoadHotQuestionSuccess(List<Question> datas,boolean hasMore);
        void onLoadHotQuestionFailed();
        void onLoadMoreHotQuestionSuccess(List<Question> datas,boolean hasMore);
        void onLoadMoreHotQuestionFailed();
    }

    interface Presenter extends BasePresenter<View>{
        void loadHotQuestion();
        void loadMoreHotQuestion();
    }
}
