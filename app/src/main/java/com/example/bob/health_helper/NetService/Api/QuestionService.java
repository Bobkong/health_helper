package com.example.bob.health_helper.NetService.Api;

import com.example.bob.health_helper.Bean.Question;
import com.example.bob.health_helper.NetService.HttpResultFunc;
import com.example.bob.health_helper.NetService.ServerResultFunc;
import com.example.bob.health_helper.NetService.ServiceManager;
import java.util.List;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;


public class QuestionService {
    private static QuestionService instance;
    public static synchronized QuestionService getInstance(){
        if(instance==null)
            instance=new QuestionService();
        return instance;
    }

    private QuestionApi questionApi=ServiceManager.getInstance().create(QuestionApi.class);

    public Observable<List<Question>> getRecentQuestions(int start){
        return questionApi.getRecentQuestions(start)
                .map(new ServerResultFunc<>())
                .onErrorResumeNext(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io());
    }

    public Observable<List<Question>> getHotQuestions(int start){
        return questionApi.getHotQuestions(start)
                .map(new ServerResultFunc<>())
                .onErrorResumeNext(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io());
    }

    public Observable<List<Question>> getUserQuestions(String uid,int start){
        return questionApi.getQuestionsByUserId(uid,start)
                .map(new ServerResultFunc<>())
                .onErrorResumeNext(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io());
    }

    public Observable<List<Question>> getUserFavoriteQuestions(String uid,int start){
        return questionApi.getFavoriteQuestionsByUserId(uid,start)
                .map(new ServerResultFunc<>())
                .onErrorResumeNext(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io());
    }

    public Observable<String> publishQuestion(String title,String description,String authorId){
        return questionApi.publishQuestion(title,description,authorId)
                .map(new ServerResultFunc<>())
                .onErrorResumeNext(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io());

    }
    public Observable<String> deleteQuestionById(String questionId){
        return questionApi.deleteQuestionById(questionId)
                .map(new ServerResultFunc<>())
                .onErrorResumeNext(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io());

    }
    public Observable<List<Question>> searchQuestion(String searchKey,int start){
        return questionApi.getSearchResult(searchKey,start)
                .map(new ServerResultFunc<>())
                .onErrorResumeNext(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io());
    }

    public Observable<String> publishFavorite(String uid,int questionId){
        return questionApi.publishFavorite(uid,questionId)
                .map(new ServerResultFunc<>())
                .onErrorResumeNext(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io());
    }
    public Observable<String> publishUnfavorite(String uid,int questionId){
        return questionApi.publishUnfavorite(uid,questionId)
                .map(new ServerResultFunc<>())
                .onErrorResumeNext(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io());
    }
}
