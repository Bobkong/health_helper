package com.example.bob.health_helper.Chat.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bob.health_helper.Chat.Constants;
import com.example.bob.health_helper.R;
import com.tencent.qcloud.uikit.business.chat.c2c.view.C2CChatPanel;
import com.tencent.qcloud.uikit.common.BaseFragment;
import com.tencent.qcloud.uikit.common.component.titlebar.PageTitleBar;


/**
 * Created by Bob on 2019/3/13.
 */

public class PersonalChatFragment extends BaseFragment {
	private View mBaseView;
	private String chatId;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
		mBaseView = inflater.inflate(R.layout.fragment_c2cchat, container, false);
		Bundle datas = getArguments();
		//由会话列表传入的会话ID
		chatId = datas.getString(Constants.INTENT_DATA);
		initView();
		return mBaseView;
	}


	private void initView() {

		//从布局文件中获取聊天面板组件
		C2CChatPanel chatPanel = mBaseView.findViewById(R.id.chat_panel);
		//单聊组件的默认UI和交互初始化
		chatPanel.initDefault();
        /*
         * 需要指定会话ID（即聊天对象的identify，具体可参考IMSDK接入文档）来加载聊天消息。在上一章节SessionClickListener中回调函数的参数SessionInfo对象中持有每一会话的会话ID，所以在会话列表点击时都可传入会话ID。
         * 特殊的如果用户应用不具备类似会话列表相关的组件，则需自行实现逻辑获取会话ID传入。
         */
		chatPanel.setBaseChatId(chatId);

		//获取单聊面板的标题栏
		PageTitleBar chatTitleBar = chatPanel.getTitleBar();

		chatTitleBar.setLeftClick(view -> getActivity().finish());

	}
}
