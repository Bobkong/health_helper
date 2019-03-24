package com.example.bob.health_helper.NetService;

import com.example.bob.health_helper.Bean.Response;
import com.example.bob.health_helper.NetService.Exception.ServerException;
import com.j256.ormlite.stmt.query.In;

import io.reactivex.functions.Function;

/**
 * 数据返回错误时，抛出异常
 * 将数据部分剥离出来
 * @param <T>
 */
public class ServerResultFunc<T> implements Function<Response<T>,T> {
    @Override
    public T apply(Response<T> response) throws Exception {
        if(response.getSuccess()==false)
            throw new ServerException(Integer.valueOf(response.getErr().getCode()),response.getErr().getMsg());
        return response.getData();
    }
}
