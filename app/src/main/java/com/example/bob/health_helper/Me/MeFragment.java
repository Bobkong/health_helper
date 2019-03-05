package com.example.bob.health_helper.Me;

import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bob.health_helper.Base.BaseFragment;
import com.example.bob.health_helper.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Bob on 2019/3/1.
 */

public class MeFragment extends BaseFragment {
	@BindView(R.id.user_icon)
	ImageView userIcon;
	@BindView(R.id.user_name)
	TextView userName;
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
		View root=inflater.inflate(R.layout.fragment_me,null);
		ButterKnife.bind(this,root);
		return root;
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

	}

	@OnClick({R.id.my_question,R.id.my_answer,R.id.my_favorite,R.id.settings,R.id.share,R.id.edit_user_info})
	public void onClicked(View view){
		switch (view.getId()){
			case R.id.edit_user_info:
				break;
			case R.id.my_question:
				navigateTo(UserQuestionActivity.class);
				break;
			case R.id.my_answer:
				break;
			case R.id.my_favorite:
				break;
			case R.id.settings:
				break;
			case R.id.share:
				break;
		}
	}
}
