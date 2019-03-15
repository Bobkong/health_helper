package com.example.bob.health_helper.NetService.Api;

import com.example.bob.health_helper.Local.LocalBean.Answer;
import com.example.bob.health_helper.Bean.Question;

import java.util.List;

import io.reactivex.Observable;

import retrofit2.Response;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface QuestionApi {
    //获取最新提问列表
    @GET("api/questions/recent")
    Observable<Response<List<Question>>> getRecentQuestions(
            @Query("start") int start
    );
    //获取热门提问列表
    @GET("api/questions/hot")
    Observable<Response<List<Question>>> getHotQuestions(
            @Query("start") int start
    );
    //获取某个用户提问的问题列表
    @GET("api/questions/user")
    Observable<Response<List<Question>>> getQuestionsByUserId(
            @Query("user_id") String UserId,
            @Query("start") int start
    );
    //获取某个用户收藏的问题列表
    @GET("api/questions/user_favorite")
    Observable<Response<List<Question>>> getFavoriteQuestionsByUserId(
            @Query("user_id") String UserId,
            @Query("start") int start
    );
    //发表问题
    @POST("api/questions")
    @FormUrlEncoded
    Observable<Response<String>> publishQuestion(
           @Field("title") String title,
           @Field("description") String description,
           @Field("author_id") String authorId
    );
    //删除某个问题
    @DELETE("api/questions/{id}")
    Observable<Response<String>> deleteQuestionById(
            @Path("question_id") String questionId
    );
    //搜索问题
    @GET("api/questions/search")
    Observable<Response<List<Question>>> getSearchResult(
            @Query("q") String searchKey,
            @Query("start") int start
    );
    //用户收藏问题
    @POST("api/questions/favorite")
    @FormUrlEncoded
    Observable<Response<String>> publishFavorite(
            @Field("uid") String uid,
            @Field("question_id") int questionId
    );
    //用户取消收藏问题
    @POST("api/questions/unfavorite")
    Observable<Response<String>> publishUnfavorite(
            @Field("uid") String uid,
            @Field("question_id") int questionId
    );
}
