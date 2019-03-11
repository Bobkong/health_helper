package com.example.bob.health_helper.Me;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.example.bob.health_helper.Me.activity.UserQuestionActivity;
import com.example.bob.health_helper.MyApplication;
import com.example.bob.health_helper.R;
import com.example.bob.health_helper.Util.SharedPreferenceUtil;
import com.orhanobut.logger.Logger;

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
				Intent intent=new Intent(getActivity(), UserQuestionActivity.class);
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
								//退出账户
								MyApplication.getTencent().logout(MyApplication.getContext());
								Toast.makeText(getActivity(), "已退出当前账户",Toast.LENGTH_LONG).show();
								Intent intent=new Intent(getActivity(),LoginActivity.class);
								startActivity(intent);
								EventBus.getDefault().post(LogoutEvent.class);
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
}
