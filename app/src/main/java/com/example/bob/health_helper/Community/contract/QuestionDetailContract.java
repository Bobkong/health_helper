package com.example.bob.health_helper.Community.contract;

import com.example.bob.health_helper.Base.BaseMvpContract;
import com.example.bob.health_helper.Bean.Answer;

import java.util.List;

public interface QuestionDetailContract extends BaseMvpContract {
    interface View extends BaseView{
        //最新回答
        void onLoadAnswerSuccess(List<Answer> answers, boolean hasMore);
        void onLoadAnswerFailed();
        void onLoadMoreAnswerSuccess(List<Answer> answers,boolean hasMore);
        void onLoadMoreAnswerFailed();

        //收藏/取消收藏问题
        void onFavoriteSuccess();
        void onFavoriteFailed();
        void onCancelFavoriteSuccess();
        void onCancelFavoriteFailed();

    }
    interface Presenter extends BasePresenter<View>{
        //最新回答
        void LoadRecentAnswer();
        void LoadMoreRecentAnswer();

        //热门回答
        void LoadHotAnswer();
        void LoadMoreHotAnswer();

        //收藏/取消收藏问题
        void Favorite(String uid,int questionId);
        void CancelFavorite(String uid,int questionId);
    }
}
