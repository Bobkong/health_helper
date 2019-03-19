package com.example.bob.health_helper.Community.presenter;


import com.example.bob.health_helper.Base.BaseMvpPresenter;
import com.example.bob.health_helper.Community.contract.SearchResultContract;
import com.example.bob.health_helper.NetService.Api.QuestionService;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class SearchResultPresenter extends BaseMvpPresenter<SearchResultContract.View>
        implements SearchResultContract.Presenter{
    private int curPage=0;
    @Override
    public void loadSearchResult(String to_search) {
        curPage=0;
        QuestionService.getInstance().searchQuestion(to_search,curPage++)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(datas->mView.onLoadSearchResultSuccess(datas,!(datas.size()==0||datas==null)),
                        throwable -> mView.onLoadSearchResultFailed());
    }

    @Override
    public void loadMoreSearchResult(String to_search) {
        QuestionService.getInstance().searchQuestion(to_search,curPage++)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(datas->mView.onLoadMoreSearchResultSuccess(datas,!(datas.size()==0||datas==null)),
                        throwable -> mView.onLoadMoreSearchResultFailed());
    }
}
