package com.example.bob.health_helper.NetService.Api;

import com.example.bob.health_helper.Bean.Comment;
import com.example.bob.health_helper.NetService.HttpResultFunc;
import com.example.bob.health_helper.NetService.ServerResultFunc;
import com.example.bob.health_helper.NetService.ServiceManager;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class CommentService {
    private static CommentService instance;
    public static synchronized CommentService getInstance(){
        if(instance==null)
            instance=new CommentService();
        return instance;
    }

    private CommentApi commentApi= ServiceManager.getInstance().create(CommentApi.class);

    public Observable<List<Comment>> getComments(int answerId,int start){
        return commentApi.getCommentsByAnswerId(answerId,start)
                .map(new ServerResultFunc<>())
                .onErrorResumeNext(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io());
    }

    public Observable<String> publishComment(String comment,String uid,int answerId){
        return commentApi.publishComment(comment,uid,answerId)
                .map(new ServerResultFunc<>())
                .onErrorResumeNext(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io());
    }
}
