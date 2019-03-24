package com.example.bob.health_helper.NetService.Api;
import com.example.bob.health_helper.Bean.News;
import com.example.bob.health_helper.NetService.HttpResultFunc;
import com.example.bob.health_helper.NetService.ServerResultFunc;
import com.example.bob.health_helper.NetService.ServiceManager;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class NewsService {
    private static NewsService instance;
    public static synchronized NewsService getInstance(){
        if(instance==null)
            instance=new NewsService();
        return instance;
    }

    private NewsApi newsApi= ServiceManager.getInstance().create(NewsApi.class);

    public Observable<List<News>> getNews(String tag,int start){
        return newsApi.getNews(tag,start)
                .map(new ServerResultFunc<>())
                .onErrorResumeNext(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io());
    }
}
