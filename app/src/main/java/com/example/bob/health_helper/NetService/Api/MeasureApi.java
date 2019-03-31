package com.example.bob.health_helper.NetService.Api;

import com.example.bob.health_helper.Bean.MeasureBean;
import com.example.bob.health_helper.Bean.MeasureData;
import com.example.bob.health_helper.Bean.Response;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Bob on 2019/3/31.
 */

public interface MeasureApi {
	@POST("/api/measure")
	Observable<Response> addMeasure(@Body MeasureBean measureData);

	@GET("/api/measure")
	Observable<Response<List<MeasureBean>>> getMeasure(@Query("uid") String uid);
}
