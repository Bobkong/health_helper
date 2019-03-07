package com.example.bob.health_helper.NetService.Api;

import com.example.bob.health_helper.Bean.Response;
import com.example.bob.health_helper.Bean.User;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserApi {
    @POST("api/user")
    Observable<Response<String>> addUser(@Body User user);
}