package com.example.bob.health_helper.NetService.Api;


import com.example.bob.health_helper.Bean.Response;
import com.example.bob.health_helper.Bean.User;
import com.example.bob.health_helper.NetService.ServiceManager;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Bob on 2019/3/15.
 */

public class UserService {
	private static UserService sUserService = new UserService();

	public static UserService getUserService() {
		return sUserService;
	}


	public Observable<Response> addUser(User user) {
		return ServiceManager.getInstance()
				.create(UserApi.class)
				.addUser(user)
				.subscribeOn(Schedulers.io());
	}

	public Observable<Response<String>> getSig(String userName){
		return ServiceManager.getInstance()
				.create(UserApi.class)
				.getSig(userName)
				.subscribeOn(Schedulers.io());
	}
}
