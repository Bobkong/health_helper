package com.example.bob.health_helper.Community.contract;

import com.example.bob.health_helper.Base.BaseMvpContract;
import com.example.bob.health_helper.Bean.Answer;

import java.util.List;

public interface NewAnsweredQuestionContract extends BaseMvpContract {
    interface View extends BaseView{
        void onLoadNewAnsweredQuestionSuccess(List<Answer> datas, boolean hasMore);
        void onLoadNewAnsweredQuestionFailed(String msg);
        void onLoadMoreNewAnsweredQuestionSuccess(List<Answer> datas,boolean hasMore);
        void onLoadMoreNewAnsweredQuestionFailed(String msg);

        void onLikeSuccess(String result);
        void onLikeFailed(String msg);
        void onCancelLikeSuccess(String result);
        void onCancelLikeFailed(String msg);

    }

    interface Presenter extends BasePresenter<View>{
        void loadNewAnsweredQuestion();
        void loadMoreNewAnsweredQuestion();

        //点赞/取消点赞回答
        void Like(String uid,int answerId);
        void CancelLike(String uid,int answerId);
    }
}
