package com.example.bob.health_helper.NetService.Api;


import com.example.bob.health_helper.Bean.Answer;
import com.example.bob.health_helper.Bean.Response;

import java.util.List;


import io.reactivex.Observable;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AnswerApi {
    //获取最新回答列表
    @GET("api/answers/newest")
    Observable<Response<List<Answer>>> getNewestAnswers(
            @Query("start") int start
    );
    //获取某个问题的最新回答列表
    @GET("api/answers/recent")
    Observable<Response<List<Answer>>> getRecentAnswers(
            @Query("question_id") int questionId,
            @Query("start") int start
    );
    //获取某个问题的热门回答列表
    @GET("api/answers/hot")
    Observable<Response<List<Answer>>> getHotAnswers(
            @Query("question_id") int questionId,
            @Query("start") int start
    );
    //获取某个用户的回答列表
    @GET("api/answers/user")
    Observable<Response<List<Answer>>> getAnswersByUserId(
            @Query("user_id") String user_id,
            @Query("start") int start
    );
    //获取某个用户点赞的回答列表
    @GET("api/answers/user_like")
    Observable<Response<List<Answer>>> getLikeAnswersByUserId(
            @Query("user_id") String user_id,
            @Query("start") int start
    );
    //发表回答
    @POST("api/answers")
    @FormUrlEncoded
    Observable<Response<String>> publishAnswer(
            @Field("answer") String answer,
            @Field("uid") String uid,
            @Field("question_id") int questionId
    );
    //删除某个回答
    @DELETE("api/answers/{id}")
    Observable<Response<String>> deleteAnswerById(
            @Path("id") String answerId
    );
    //用户点赞回答
    @POST("api/answers/like")
    @FormUrlEncoded
    Observable<Response<String>> publishLike(
            @Field("uid") String uid,
            @Field("answer_id") int answerId
    );
    //用户取消点赞回答
    @POST("api/answers/unlike")
    Observable<Response<String>> publishUnlike(
            @Field("uid") String uid,
            @Field("answer_id") int answerId
    );
}
