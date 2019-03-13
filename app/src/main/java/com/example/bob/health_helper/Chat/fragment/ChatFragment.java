package com.example.bob.health_helper.Chat.fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bob.health_helper.Chat.Constants;
import com.example.bob.health_helper.Chat.activity.ChatActivity;
import com.example.bob.health_helper.R;
import com.tencent.qcloud.uikit.business.session.model.SessionInfo;
import com.tencent.qcloud.uikit.business.session.view.SessionPanel;
import com.tencent.qcloud.uikit.business.session.view.wedgit.SessionClickListener;

import butterknife.BindView;


/**
 * Created by Bob on 2019/3/1.
 */

public class ChatFragment extends Fragment  implements SessionClickListener {
	private View baseView;
	private SessionPanel sessionPanel;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
		baseView = inflater.inflate(R.layout.fragment_chat, container, false);
		initView();
		return baseView;
	}


	private void initView() {
		sessionPanel = baseView.findViewById(R.id.session_panel);
		// 会话面板初始化默认功能
		sessionPanel.initDefault();
		// 这里设置会话列表点击的跳转逻辑，告诉添加完SessionPanel后会话被点击后该如何处理
		sessionPanel.setSessionClick(this);

	}

	@Override
	public void onSessionClick(SessionInfo session) {
		//此处为demo的实现逻辑，更根据会话类型跳转到相关界面，开发者可根据自己的应用场景灵活实现
		if (session.isGroup()) {
			//如果是群组，跳转到群聊界面
			startGroupChat(getActivity(), session.getPeer());
		} else {
			//否则跳转到C2C单聊界面
			startC2CChat(getActivity(), session.getPeer());

		}
	}

	/**
	 * 跳转到C2C聊天
	 *
	 * @param context  跳转容器的Context
	 * @param chatInfo 会话ID(对方identify)
	 */
	public static void startC2CChat(Context context, String chatInfo) {
		Intent intent = new Intent(context, ChatActivity.class);
		intent.putExtra(Constants.IS_GROUP, false);
		intent.putExtra(Constants.INTENT_DATA, chatInfo);
		context.startActivity(intent);
	}

	/**
	 * 跳转到群聊
	 *
	 * @param context  跳转容器的Context
	 * @param chatInfo 会话ID（群ID）
	 */
	public static void startGroupChat(Context context, String chatInfo) {
		Intent intent = new Intent(context, ChatActivity.class);
		intent.putExtra(Constants.IS_GROUP, true);
		intent.putExtra(Constants.INTENT_DATA, chatInfo);
		context.startActivity(intent);
	}
}
