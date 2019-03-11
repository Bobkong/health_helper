package com.example.bob.health_helper.News;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bob.health_helper.Base.BaseFragment;
import com.example.bob.health_helper.News.adapter.NewsPagerAdapter;
import com.example.bob.health_helper.R;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Bob on 2019/3/1.
 */

public class NewsFragment extends BaseFragment {
	@BindView(R.id.tool_bar)
	Toolbar toolBar;
	@BindView(R.id.tab_layout)
	TabLayout tabLayout;
	@BindView(R.id.view_pager)
	ViewPager viewPager;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
		View root=inflater.inflate(R.layout.fragment_news,container,false);
		ButterKnife.bind(this,root);
		return root;
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		//tabLayout+viewPager+fragment
		viewPager.setAdapter(new NewsPagerAdapter(getChildFragmentManager(),getResources().getStringArray(R.array.news_type)));
		tabLayout.setupWithViewPager(viewPager);
		tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);//tablayout可滑动

	}
}
