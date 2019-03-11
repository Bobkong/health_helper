package com.example.bob.health_helper.Community;

import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.bob.health_helper.Base.BaseFragment;
import com.example.bob.health_helper.Community.activity.AddQuestionActivity;
import com.example.bob.health_helper.Community.activity.SearchActivity;
import com.example.bob.health_helper.Community.adapter.CommunityPagerAdapter;
import com.example.bob.health_helper.Community.fragment.HotQuestionFragment;
import com.example.bob.health_helper.Community.fragment.NewAnsweredQuestionFragment;
import com.example.bob.health_helper.Community.fragment.BaseRefreshableListFragment;
import com.example.bob.health_helper.Community.fragment.RecentQuestionFragment;
import com.example.bob.health_helper.R;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Bob on 2019/3/1.
 */

public class CommunityFragment extends BaseFragment {

	private ArrayList<Fragment> fragments=new ArrayList<>(Arrays.asList(new RecentQuestionFragment(),
			new HotQuestionFragment(),new NewAnsweredQuestionFragment()));

	@BindView(R.id.tool_bar)
	Toolbar toolbar;
	@BindView(R.id.tab_layout)
	TabLayout tabLayout;
	@BindView(R.id.view_pager)
	ViewPager viewPager;
	@OnClick(R.id.add_question)
	public void onClicked(){
		navigateTo(AddQuestionActivity.class);
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
		View root=inflater.inflate(R.layout.fragment_community,container,false);
		ButterKnife.bind(this,root);
		return root;
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		//tabLayout+viewPager+fragment
		viewPager.setAdapter(new CommunityPagerAdapter(getChildFragmentManager(),
				getResources().getStringArray(R.array.community_type),
				fragments));
		tabLayout.setupWithViewPager(viewPager);

		//toolBar
		toolbar.inflateMenu(R.menu.community_search);
		toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem menuItem) {
				if(menuItem.getItemId()==R.id.search_question)
					navigateTo(SearchActivity.class);
				return true;
			}
		});
	}

}
