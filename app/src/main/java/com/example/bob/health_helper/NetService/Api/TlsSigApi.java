package com.example.bob.health_helper.NetService.Api;

import com.example.bob.health_helper.Bean.Response;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Bob on 2019/3/13.
 */

public interface TlsSigApi {
	@GET("/tls_sig")
	Observable<Response<String>> getTls(@Query("user_id") String user_id);
}
