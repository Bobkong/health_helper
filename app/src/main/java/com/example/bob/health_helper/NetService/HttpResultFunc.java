package com.example.bob.health_helper.NetService;


import com.example.bob.health_helper.NetService.Exception.ExceptionEngine;
/**
 * 拦截onError事件
 */
import io.reactivex.Observable;
import io.reactivex.functions.Function;

public class HttpResultFunc<T> implements Function<Throwable, Observable<T>> {
    @Override
    public Observable<T> apply(Throwable throwable) {
        return Observable.error(ExceptionEngine.handleException(throwable));
    }
}
