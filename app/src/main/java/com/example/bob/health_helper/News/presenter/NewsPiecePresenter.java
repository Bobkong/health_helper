package com.example.bob.health_helper.News.presenter;

import com.example.bob.health_helper.Base.BaseMvpPresenter;
import com.example.bob.health_helper.Bean.News;
import com.example.bob.health_helper.NetService.Api.NewsService;
import com.example.bob.health_helper.News.contract.NewsPieceContract;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class NewsPiecePresenter extends BaseMvpPresenter<NewsPieceContract.View>
            implements NewsPieceContract.Presenter{
    private int curPage=0;
    private boolean hasMore=false;
    @Override
    public void loadNewsByTag(int tag) {
        curPage=0;
        Disposable disposable=NewsService.getInstance().getNews(News.TAGS[tag],curPage++)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(datas -> {
                    hasMore=!(datas.size()<10);
                    mView.onLoadNewsSuccess(datas,hasMore);
                        },
                        throwable -> mView.onLoadNewsFailed(throwable.getMessage()));
        addSubscribe(disposable);
    }

    @Override
    public void loadMoreNewsByTag(int tag) {
        Disposable disposable=NewsService.getInstance().getNews(News.TAGS[tag],curPage++)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(datas -> {
                    hasMore=!(datas.size()<10);
                    mView.onLoadMoreNewsSuccess(datas,hasMore);
                        },
                        throwable -> mView.onLoadMoreNewsFailed(throwable.getMessage()));
        addSubscribe(disposable);
    }
}
