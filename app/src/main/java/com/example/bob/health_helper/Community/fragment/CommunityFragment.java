package com.example.bob.health_helper.Community.fragment;

import android.content.Intent;
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

import com.example.bob.health_helper.Community.activity.SearchActivity;
import com.example.bob.health_helper.Community.adapter.CommunityPagerAdapter;
import com.example.bob.health_helper.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Bob on 2019/3/1.
 */

public class CommunityFragment extends Fragment {
	private ArrayList<Fragment> fragments=new ArrayList<>();
	@BindView(R.id.tool_bar)
	Toolbar toolbar;
	@BindView(R.id.tab_layout)
	TabLayout tabLayout;
	@BindView(R.id.view_pager)
	ViewPager viewPager;
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
		View root=inflater.inflate(R.layout.fragment_community,null);
		ButterKnife.bind(this,root);
		return root;
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		//tabLayout+viewPager+fragment
		fragments.add(new NewQuestionFragment());
		fragments.add(new HotQuestionFragment());
		fragments.add(new NewAnswerQuestionFragment());
		viewPager.setAdapter(new CommunityPagerAdapter(getChildFragmentManager(),
				getResources().getStringArray(R.array.community_type),
				fragments));
		tabLayout.setupWithViewPager(viewPager);

		//toolBar
		toolbar.inflateMenu(R.menu.community_search);
		toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem menuItem) {
				Intent intent=new Intent(getActivity(), SearchActivity.class);
				startActivity(intent);
				return true;
			}
		});
	}
}
