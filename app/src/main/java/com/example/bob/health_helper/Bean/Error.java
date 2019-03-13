package com.example.bob.health_helper.Bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Bob on 2019/3/13.
 */

public class Error {

	@SerializedName("code")
	private String mCode;

	@SerializedName("msg")
	private String mMsg;

	public String getCode() {
		return mCode;
	}

	public void setCode(String code) {
		mCode = code;
	}

	public String getMsg() {
		return mMsg;
	}

	public void setMsg(String msg) {
		mMsg = msg;
	}

	@Override
	public String toString() {
		return "Error{" +
				"mCode='" + mCode + '\'' +
				", mMsg='" + mMsg + '\'' +
				'}';
	}
}
