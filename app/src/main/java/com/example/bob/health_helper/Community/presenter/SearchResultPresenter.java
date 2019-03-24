package com.example.bob.health_helper.Community.presenter;


import com.example.bob.health_helper.Base.BaseMvpPresenter;
import com.example.bob.health_helper.Community.contract.SearchResultContract;
import com.example.bob.health_helper.NetService.Api.QuestionService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class SearchResultPresenter extends BaseMvpPresenter<SearchResultContract.View>
        implements SearchResultContract.Presenter{
    private int curPage=0;
    private boolean hasMore=false;
    @Override
    public void loadSearchResult(String to_search) {
        curPage=0;
        Disposable disposable=QuestionService.getInstance().searchQuestion(to_search,curPage++)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(datas->{
                    hasMore=!(datas.size()<10);
                    mView.onLoadSearchResultSuccess(datas,hasMore);
                        },
                        throwable -> mView.onLoadSearchResultFailed(throwable.getMessage()));
        addSubscribe(disposable);
    }

    @Override
    public void loadMoreSearchResult(String to_search) {
        if(hasMore){
            Disposable disposable=QuestionService.getInstance().searchQuestion(to_search,curPage++)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(datas->{
                        hasMore=!(datas.size()<10);
                        mView.onLoadMoreSearchResultSuccess(datas,hasMore);
                            },
                            throwable -> mView.onLoadMoreSearchResultFailed(throwable.getMessage()));
            addSubscribe(disposable);
        }
    }
}
