package com.example.bob.health_helper.NetService.Api;



import com.example.bob.health_helper.Local.LocalBean.Comment;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface CommentApi {
    //获取某个回答的所有评论
    @GET("api/comments")
    Observable<Response<List<Comment>>> getCommentsByAnswerId(
            @Query("answer_id") int answerId,
            @Query("start") int start
    );
    //对某个回答发表评论
    @POST("api/comments")
    @FormUrlEncoded
    Observable<Response<String>> publishComment(
            @Field("comment") String comment,
            @Field("uid") String uid,
            @Field("answer_id") int answerId
    );
}
