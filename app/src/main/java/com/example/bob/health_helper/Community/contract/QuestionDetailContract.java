package com.example.bob.health_helper.Community.contract;

import com.example.bob.health_helper.Base.BaseMvpContract;
import com.example.bob.health_helper.Bean.Answer;

import java.util.List;

public interface QuestionDetailContract extends BaseMvpContract {
    interface View extends BaseView{
        //最新回答
        void onLoadAnswerSuccess(List<Answer> answers, boolean hasMore);
        void onLoadAnswerFailed(String msg);
        void onLoadMoreAnswerSuccess(List<Answer> answers,boolean hasMore);
        void onLoadMoreAnswerFailed(String msg);

        //收藏/取消收藏问题
        void onFavoriteSuccess(String result);
        void onFavoriteFailed(String msg);
        void onCancelFavoriteSuccess(String result);
        void onCancelFavoriteFailed(String msg);

        void onLikeSuccess(String result);
        void onLikeFailed(String msg);
        void onCancelLikeSuccess(String result);
        void onCancelLikeFailed(String msg);

    }
    interface Presenter extends BasePresenter<View>{
        //最新回答
        void LoadRecentAnswer(int questionId);
        void LoadMoreRecentAnswer(int questionId);

        //热门回答
        void LoadHotAnswer(int questionId);
        void LoadMoreHotAnswer(int questionId);

        //收藏/取消收藏问题
        void Favorite(String uid,int questionId);
        void CancelFavorite(String uid,int questionId);

        //点赞/取消点赞回答
        void Like(String uid,int answerId);
        void CancelLike(String uid,int answerId);
    }
}
