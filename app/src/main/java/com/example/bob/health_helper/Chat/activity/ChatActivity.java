package com.example.bob.health_helper.Chat.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.bob.health_helper.Chat.Constants;
import com.example.bob.health_helper.Chat.fragment.GroupChatFragment;
import com.example.bob.health_helper.Chat.fragment.PersonalChatFragment;
import com.example.bob.health_helper.R;
import com.tencent.qcloud.uikit.business.chat.c2c.model.C2CChatManager;
import com.tencent.qcloud.uikit.business.chat.group.model.GroupChatManager;
import com.tencent.qcloud.uikit.common.BaseFragment;


/**
 * Created by Bob on 2019/3/13.
 */

public class ChatActivity extends Activity {
	BaseFragment mCurrentFragment = null;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);

		Bundle bundle = getIntent().getExtras();
		boolean isGroup = false;
		if (bundle != null){
			isGroup = bundle.getBoolean(Constants.IS_GROUP);
		}

		if (isGroup) {
			mCurrentFragment = new GroupChatFragment();
		} else {
			mCurrentFragment = new PersonalChatFragment();
		}
		mCurrentFragment.setArguments(bundle);
		getFragmentManager().beginTransaction().replace(R.id.empty_view, mCurrentFragment).commitAllowingStateLoss();

	}

	@Override
	public void finish() {
		if (mCurrentFragment instanceof GroupChatFragment) {
			//退出Activity时释放群聊相关资源
			GroupChatManager.getInstance().destroyGroupChat();
		} else if (mCurrentFragment instanceof PersonalChatFragment) {
			//退出Activity时释放单聊相关资源
			C2CChatManager.getInstance().destroyC2CChat();
		}
		super.finish();
	}
}
