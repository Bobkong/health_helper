package com.example.bob.health_helper.Me;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.bob.health_helper.Event.LogoutEvent;
import com.example.bob.health_helper.LoginActivity;
import com.example.bob.health_helper.R;
import com.example.bob.health_helper.Util.SharedPreferenceUtil;
import com.orhanobut.logger.Logger;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import org.greenrobot.eventbus.EventBus;
import java.util.Map;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Bob on 2019/3/1.
 */

public class MeFragment extends Fragment {
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

	@OnClick({R.id.my_question,R.id.my_answer,R.id.my_favorite,R.id.settings,R.id.share,R.id.edit_user_info,R.id.me_login_out})
	public void onClicked(View view){
		switch (view.getId()){
			case R.id.edit_user_info:
				break;
			case R.id.my_question:
				Intent intent=new Intent(getActivity(),UserQuestionActivity.class);
				startActivity(intent);
				break;
			case R.id.my_answer:
				break;
			case R.id.my_favorite:
				break;
			case R.id.settings:
				break;
			case R.id.share:
				break;
			case R.id.me_login_out:
				AlertDialog dialog=new AlertDialog.Builder(getActivity())
						.setMessage(getResources().getString(R.string.logout_confirm))
						.setPositiveButton(R.string.confirm,new DialogInterface.OnClickListener(){
							@Override
							public void onClick(DialogInterface dialogInterface, int i) {
								Logger.e("删除授权");
								//删除授权
								if (SharedPreferenceUtil.getBooleanKeyValue("isQQLogin")) {
									UMShareAPI.get(getActivity()).deleteOauth(getActivity(), SHARE_MEDIA.QQ, umAuthListener);
									SharedPreferenceUtil.saveBooleanKeyValue("isQQLogin", false);
								} else if (SharedPreferenceUtil.getBooleanKeyValue("isSinaLogin")) {
									UMShareAPI.get(getActivity()).deleteOauth(getActivity(), SHARE_MEDIA.SINA, umAuthListener);
									SharedPreferenceUtil.saveBooleanKeyValue("isSinaLogin", false);
								}
							}
						})
						.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialogInterface, int i) {

							}
						})
						.create();
				dialog.show();
				break;
		}
	}

	private UMAuthListener umAuthListener=new UMAuthListener() {
		@Override
		public void onStart(SHARE_MEDIA share_media) {

		}

		@Override
		public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
			Toast.makeText(getActivity(), "已退出当前账户",Toast.LENGTH_LONG).show();
			Intent intent=new Intent(getActivity(),LoginActivity.class);
			startActivity(intent);
			EventBus.getDefault().post(LogoutEvent.class);
		}

		@Override
		public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
			Toast.makeText(getActivity(), "退出失败：" + throwable.getMessage(),Toast.LENGTH_LONG).show();
		}

		@Override
		public void onCancel(SHARE_MEDIA share_media, int i) {
			Toast.makeText(getActivity(), "退出已被取消",Toast.LENGTH_LONG).show();
		}
	};
}
