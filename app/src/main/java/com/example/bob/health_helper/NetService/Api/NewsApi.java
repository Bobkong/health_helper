package com.example.bob.health_helper.NetService.Api;

import com.example.bob.health_helper.Local.LocalBean.News;
import com.example.bob.health_helper.Bean.Response;

import java.util.List;


import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsApi {
    @GET("api/news")
    Observable<Response<List<News>>> getNews(
            @Query("tag") String tag,
            @Query("start") int start
    );
}
