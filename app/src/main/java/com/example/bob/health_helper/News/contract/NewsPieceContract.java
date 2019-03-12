package com.example.bob.health_helper.News.contract;

import com.example.bob.health_helper.Base.BaseMvpContract;
import com.example.bob.health_helper.Bean.News;

import java.util.List;

public interface NewsPieceContract extends BaseMvpContract {
    interface View extends BaseView {
        void onLoadNewsSuccess(List<News> articleList);
        void onLoadNewsFailed();
        void onLoadMoreNewsSuccess();
        void onLoadMoreNewsFailed();
    }

    interface Presenter extends BasePresenter<View> {
        void loadNewsByTag(int tag);
        void loadMoreNewsByTag(int tag);
    }

}