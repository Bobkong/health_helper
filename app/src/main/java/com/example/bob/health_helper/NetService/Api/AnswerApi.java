package com.example.bob.health_helper.NetService.Api;

import com.example.bob.health_helper.Bean.Answer;
import com.example.bob.health_helper.Bean.Response;

import java.util.List;


import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AnswerApi {
    //获取某个问题的回答列表（分页）
    @GET("api/answers")
    Observable<Response<List<Answer>>> getAnswers(
            @Query("question_id") String questionId,
            @Query("page") String page,
            @Query("per_page") String per_page
    );
    //获取某个用户的回答列表
    @GET("api/answers")
    Observable<Response<List<Answer>>> getAnswersByUserId(
            @Query("user_id") String user_id
    );
    //获取具体某个回答
    @GET("api/answers/{id}")
    Observable<Response<Answer>> getAnswerById(
            @Path("id") String answerId
    );
    //发表回答
    @POST("api/answers")
    Observable<Response<String>> publishAnswer(
            @Body Answer answer
    );
    //更新某个回答的统计数据（点赞数/评论数）
    @PUT("api/answers/{id}")
    Observable<Response<String>> updateAnswerStatisticsCountById(
            @Path("id") String answerId,
            @Query("user_id") String userId,
            @Query("like_count") String likeCount,
            @Query("comment_count") String commentCount
    );
    //更新某个回答的内容
    @PUT("api/answers/{id}")
    Observable<Response<String>> updateAnswerContentById(
            @Path("id") String answerId,
            @Query("content") String content
    );
    //删除某个回答
    @DELETE("api/answers/{id}")
    Observable<Response<String>> deleteAnswerById(
            @Path("id") String answerId
    );
}
