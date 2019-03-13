package com.example.bob.health_helper.NetService;

import com.example.bob.health_helper.Bean.Response;
import com.example.bob.health_helper.NetService.Api.TlsSigApi;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Bob on 2019/3/13.
 */

public class TlsSigService {
	private static TlsSigService sTlsService = new TlsSigService();

	public static TlsSigService getTlsService(){
		return sTlsService;
	}

	public Observable<Response<String>> getSig(String user_id){
		return ServiceManager.getInstance()
				.create(TlsSigApi.class)
				.getTls(user_id)
				.subscribeOn(Schedulers.io());
	}
}
