package com.example.bob.health_helper.NetService.Api;

import com.example.bob.health_helper.Bean.Answer;
import com.example.bob.health_helper.NetService.ResponseFunc;
import com.example.bob.health_helper.NetService.ServiceManager;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class AnswerService {
    private static AnswerService instance;
    public static synchronized AnswerService getInstance(){
        if(instance==null)
            instance=new AnswerService();
        return instance;
    }

    private AnswerApi answerApi= ServiceManager.getInstance().create(AnswerApi.class);

    public Observable<List<Answer>> getNewestAnswers(int start){
        return answerApi.getNewestAnswers(start)
                .map(new ResponseFunc<>())
                .subscribeOn(Schedulers.io());
    }

    public Observable<List<Answer>> getRecentAnswers(int questionId,int start){
        return answerApi.getRecentAnswers(questionId,start)
                .map(new ResponseFunc<>())
                .subscribeOn(Schedulers.io());
    }

    public Observable<List<Answer>> getHotAnswers(int questionId,int start){
        return answerApi.getHotAnswers(questionId,start)
                .map(new ResponseFunc<>())
                .subscribeOn(Schedulers.io());
    }

    public Observable<List<Answer>> getUserAnswers(String uid,int start){
        return answerApi.getAnswersByUserId(uid,start)
                .map(new ResponseFunc<>())
                .subscribeOn(Schedulers.io());
    }

    public Observable<List<Answer>> getUserLikeAnswers(String uid,int start){
        return answerApi.getLikeAnswersByUserId(uid,start)
                .map(new ResponseFunc<>())
                .subscribeOn(Schedulers.io());
    }

    public Observable<String> publishAnswer(String answer,String uid,int questionId){
        return answerApi.publishAnswer(answer,uid,questionId)
                .map(new ResponseFunc<>())
                .subscribeOn(Schedulers.io());
    }

    public Observable<String> deleteAnswer(int answerId){
        return answerApi.deleteAnswerById(answerId)
                .map(new ResponseFunc<>())
                .subscribeOn(Schedulers.io());
    }

    public Observable<String> publishLike(String uid,int answerId){
        return answerApi.publishLike(uid,answerId)
                .map(new ResponseFunc<>())
                .subscribeOn(Schedulers.io());
    }

    public Observable<String> publishUnlike(String uid,int answerId){
        return answerApi.publishUnlike(uid,answerId)
                .map(new ResponseFunc<>())
                .subscribeOn(Schedulers.io());
    }
}
