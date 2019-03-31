package com.example.bob.health_helper.NetService.Api;

import com.example.bob.health_helper.Bean.MeasureBean;
import com.example.bob.health_helper.Bean.MeasureData;
import com.example.bob.health_helper.Bean.Response;
import com.example.bob.health_helper.NetService.ServiceManager;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Bob on 2019/3/31.
 */

public class MeasureService {
	private static MeasureService sMeasureService = new MeasureService();

	public static MeasureService getMeasureService() {
		return sMeasureService;
	}


	public Observable<Response> addMeasure(MeasureBean measureData) {
		return ServiceManager.getInstance()
				.create(MeasureApi.class)
				.addMeasure(measureData)
				.subscribeOn(Schedulers.io());
	}

	public Observable<Response<List<MeasureBean>>> getMeasure(String uid){
		return ServiceManager.getInstance()
				.create(MeasureApi.class)
				.getMeasure(uid)
				.subscribeOn(Schedulers.io());
	}
}
