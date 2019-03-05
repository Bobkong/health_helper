package com.example.bob.health_helper.NetService.Api;

import com.example.bob.health_helper.Bean.Question;

import java.util.List;

import io.reactivex.Observable;

import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface QuestionApi {
    //获取最新提问列表（分页）
    @GET("api/questions/recent")
    Observable<Response<List<Question>>> getRecentQuestions(
            @Query("page") int page,
            @Query("per_page") int per_page,
            @Query("sort_by") String date,
            @Query("order") String order
    );
    //获取热门提问列表（分页）
    @GET("api/questions/hot")
    Observable<Response<List<Question>>> getHotQuestions(
            @Query("page") String page,
            @Query("per_page") String per_page,
            @Query("sort_by") String favorite_count,
            @Query("order") String order
    );
    //获取最新被回答的提问列表（分页）
    @GET("api/questions/new_answer")
    Observable<Response<List<Question>>> getNewAnswerQuestions(
            @Query("page") String page,
            @Query("per_page") String per_page,
            @Query("sort_by") String new_answer,
            @Query("order") String order
    );
    //获取某个用户提问的所有问题
    @GET("api/questions")
    Observable<Response<Question>> getQuestionsByUserId(
            @Query("user_id") String UserId
    );
    //获取某个问题
    @GET("api/questions/{id}")
    Observable<Response<Question>> getQuestionById(
            @Path("id") String questionId
    );
    //发表问题
    @POST("api/questions")
    Observable<Response<String>> publishQuestion(
            @Body Question question
    );
    //更新某个问题的统计数据（收藏数/回答数）
    @PUT("api/questions/{id}")
    Observable<Response<String>> updateQuestionStatisticsCountById(
            @Path("id") String questionId,
            @Query("user_id") String userId,
            @Query("favorite_count") String favoriteCount,
            @Query("answer_count") String answerCount
    );
    //更新某个问题的内容
    @PUT("api/questions/{id}")
    Observable<Response<String>> updateQuestionContentById(
            @Path("id") String questionId,
            @Query("title") String title,
            @Query("description") String description
    );
    //删除某个问题
    @DELETE("api/questions/{id}")
    Observable<Response<String>> deleteQuestionById(
            @Path("id") String questionId
    );
}
