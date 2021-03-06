package com.example.bob.health_helper.Me;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.bob.health_helper.Base.BaseFragment;
import com.example.bob.health_helper.Event.LogoutEvent;
import com.example.bob.health_helper.LoginActivity;
import com.example.bob.health_helper.Me.activity.EditUserInfoActivity;
import com.example.bob.health_helper.Me.activity.ReminderActivity;
import com.example.bob.health_helper.Me.activity.SettingActivity;
import com.example.bob.health_helper.Me.activity.UserAnswerActivity;
import com.example.bob.health_helper.Me.activity.UserFavoriteQuestionActivity;
import com.example.bob.health_helper.Me.activity.UserLikeAnswerActivity;
import com.example.bob.health_helper.Me.activity.UserQuestionActivity;
import com.example.bob.health_helper.MyApplication;
import com.example.bob.health_helper.R;
import com.example.bob.health_helper.Util.SharedPreferenceUtil;
import com.orhanobut.logger.Logger;
import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMManager;

import org.greenrobot.eventbus.EventBus;
import java.util.Map;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Bob on 2019/3/1.
 */

public class MeFragment extends BaseFragment {
	private final static String TAG = "MeFragment";
	@BindView(R.id.user_icon)
	ImageView userIcon;
	@BindView(R.id.user_name)
	TextView userName;
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
		View root=inflater.inflate(R.layout.fragment_me,container,false);
		ButterKnife.bind(this,root);
		return root;
	}

	@Override
	public void onResume() {
		super.onResume();
		//用户信息初始化
		Glide.with(MyApplication.getContext())
				.load(SharedPreferenceUtil.getUser().getIconurl())
				.apply(new RequestOptions().placeholder(R.mipmap.ic_launcher_round))
				.apply(new RequestOptions().circleCrop())
				.into(userIcon);
		userName.setText(SharedPreferenceUtil.getUser().getName());
	}

	@OnClick({R.id.my_question,R.id.my_answer,R.id.my_favorite,R.id.my_like,R.id.my_reminder,R.id.settings,R.id.share,R.id.edit_user_info,R.id.me_login_out})
	public void onClicked(View view){
		switch (view.getId()){
			case R.id.edit_user_info:
				navigateTo(EditUserInfoActivity.class);
				break;
			case R.id.my_question:
				navigateTo(UserQuestionActivity.class);
				break;
			case R.id.my_answer:
				navigateTo(UserAnswerActivity.class);
				break;
			case R.id.my_favorite:
				navigateTo(UserFavoriteQuestionActivity.class);
				break;
			case R.id.my_like:
				navigateTo(UserLikeAnswerActivity.class);
				break;
			case R.id.my_reminder:
				navigateTo(ReminderActivity.class);
				break;
			case R.id.settings:
				navigateTo(SettingActivity.class);
				break;
			case R.id.share:
				break;
			case R.id.me_login_out:
				AlertDialog dialog=new AlertDialog.Builder(getActivity())
						.setMessage(getResources().getString(R.string.logout_confirm))
						.setPositiveButton(R.string.confirm, (dialogInterface, i) -> {
							//退出账户
							MyApplication.getTencent().logout(MyApplication.getContext());
							Toast.makeText(getActivity(), "已退出当前账户",Toast.LENGTH_LONG).show();
							tencentLogout();
							Intent intent=new Intent(getActivity(),LoginActivity.class);
							startActivity(intent);
							EventBus.getDefault().post(new LogoutEvent());
						})
						.setNegativeButton(R.string.cancel, (dialogInterface, i) -> {

						})
						.create();
				dialog.show();
				break;
		}
	}
	public static void tencentLogout(){
		//登出
		TIMManager.getInstance().logout(new TIMCallBack() {
			@Override
			public void onError(int code, String desc) {

				//错误码 code 和错误描述 desc，可用于定位请求失败原因
				//错误码 code 列表请参见错误码表
				Log.d(TAG, "logout failed. code: " + code + " errmsg: " + desc);
			}

			@Override
			public void onSuccess() {
				//登出成功
				Log.d(TAG, "logout success");
			}
		});
	}
}
