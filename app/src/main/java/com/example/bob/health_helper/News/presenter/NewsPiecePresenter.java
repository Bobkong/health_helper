package com.example.bob.health_helper.News.presenter;

import com.example.bob.health_helper.Base.BaseMvpPresenter;
import com.example.bob.health_helper.Bean.News;
import com.example.bob.health_helper.NetService.Api.NewsService;
import com.example.bob.health_helper.News.contract.NewsPieceContract;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class NewsPiecePresenter extends BaseMvpPresenter<NewsPieceContract.View>
            implements NewsPieceContract.Presenter{
    private int curPage=0;
    @Override
    public void loadNewsByTag(int tag) {
        curPage=0;
        NewsService.getInstance().getNews(News.TAGS[tag],curPage++)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(datas -> mView.onLoadNewsSuccess(datas,!(datas.size()==0||datas==null)),
                        throwable -> mView.onLoadNewsFailed());
    }

    @Override
    public void loadMoreNewsByTag(int tag) {
        NewsService.getInstance().getNews(News.TAGS[tag],curPage++)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(datas -> mView.onLoadMoreNewsSuccess(datas,!(datas.size()==0||datas==null)),
                        throwable -> mView.onLoadMoreNewsFailed());
    }
}
