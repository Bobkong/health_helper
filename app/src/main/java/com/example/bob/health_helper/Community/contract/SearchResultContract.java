package com.example.bob.health_helper.Community.contract;

import com.example.bob.health_helper.Base.BaseMvpContract;
import com.example.bob.health_helper.Bean.Question;

import java.util.List;

public interface SearchResultContract extends BaseMvpContract {
    interface View extends BaseView {
        void onLoadSearchResultSuccess(List<Question> datas, boolean hasMore);
        void onLoadSearchResultFailed();
        void onLoadMoreSearchResultSuccess(List<Question> datas, boolean hasMore);
        void onLoadMoreSearchResultFailed();
    }

    interface Presenter extends BasePresenter<View> {
        void loadSearchResult(String to_search);
        void loadMoreSearchResult(String to_search);
    }
}
