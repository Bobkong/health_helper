package com.example.bob.health_helper.NetService.Api;



import com.example.bob.health_helper.Bean.Comment;

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

public interface CommentApi {
    //获取某个回答的所有评论
    @GET("api/comments")
    Observable<Response<List<Comment>>> getCommentsByAnswerId(
            @Query("answer_id") String answerId
    );
    //获取某个用户的所有评论
    @GET("api/comments")
    Observable<Response<List<Comment>>> getCommentsByUserId(
            @Query("user_id") String user_id
    );
    @POST("api/comments")
    Observable<Response<String>> publishComment(
            @Body Comment comment
    );
    //更新某个评论内容
    @PUT("api/comments/{id}")
    Observable<Response<String>> updateCommentContentById(
            @Path("id") String commentId,
            @Query("content") String content
    );
    //删除某个评论
    @DELETE("api/comments/{id}")
    Observable<Response<String>> deleteCommentById(
            @Path("id") String commentId
    );
}
