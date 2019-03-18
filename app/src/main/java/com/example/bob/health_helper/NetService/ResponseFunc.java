package com.example.bob.health_helper.NetService;

import com.example.bob.health_helper.Bean.Response;
import io.reactivex.functions.Function;

/**
 * 数据返回错误时，抛出异常
 * 将数据部分剥离出来
 * @param <T>
 */
public class ResponseFunc<T> implements Function<Response<T>,T> {
    @Override
    public T apply(Response<T> response) throws Exception {
        if(response.getSuccess()==false)
            throw new Exception(response.getErr().toString());
        return response.getData();
    }
}
