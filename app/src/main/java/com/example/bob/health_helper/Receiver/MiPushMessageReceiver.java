package com.example.bob.health_helper.Receiver;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.xiaomi.mipush.sdk.ErrorCode;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.xiaomi.mipush.sdk.MiPushCommandMessage;
import com.xiaomi.mipush.sdk.MiPushMessage;
import com.xiaomi.mipush.sdk.PushMessageReceiver;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Bob on 2019/3/21.
 */

public class MiPushMessageReceiver extends PushMessageReceiver {

	private final String TAG = "MiPushMessageReceiver";
	public static String mRegId;

	@Override
	public void onNotificationMessageClicked(Context context, MiPushMessage message) {
		Log.d(TAG,"onNotificationMessageClicked is called. " + message.toString());
		Log.d(TAG, getSimpleDate() + " " + message.getContent());
	}

	@Override
	public void onNotificationMessageArrived(Context context, MiPushMessage message) {
		Log.d(TAG,"onNotificationMessageArrived is called. " + message.toString());
		Log.d(TAG, getSimpleDate() + " " + message.getContent());
	}

	@Override
	public void onReceiveRegisterResult(Context context, MiPushCommandMessage message) {
		Log.d(TAG, "onReceiveRegisterResult is called. " + message.toString());
		String command = message.getCommand();
		List<String> arguments = message.getCommandArguments();
		String cmdArg1 = ((arguments != null && arguments.size() > 0) ? arguments.get(0) : null);

		Log.d(TAG, "cmd: " + command + " | arg: " + cmdArg1
				+ " | result: " + message.getResultCode() + " | reason: " + message.getReason());

		if (MiPushClient.COMMAND_REGISTER.equals(command)) {
			if (message.getResultCode() == ErrorCode.SUCCESS) {
				mRegId = cmdArg1;
			}
		}

		Log.d(TAG, "regId: " + mRegId);
	}

	@SuppressLint("SimpleDateFormat")
	private static String getSimpleDate() {
		return new SimpleDateFormat("MM-dd hh:mm:ss").format(new Date());
	}

}
