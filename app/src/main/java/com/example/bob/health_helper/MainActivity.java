package com.example.bob.health_helper;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

	private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
			= new BottomNavigationView.OnNavigationItemSelectedListener() {

		@Override
		public boolean onNavigationItemSelected(@NonNull MenuItem item) {
			switch (item.getItemId()) {
				case R.id.navigation_measure:
					mViewPager.setCurrentItem(0);
					return true;
				case R.id.navigation_chat:
					mViewPager.setCurrentItem(1);
					return true;
				case R.id.navigation_news:
					mViewPager.setCurrentItem(2);
					return true;
				case R.id.navigation_community:
					mViewPager.setCurrentItem(3);
					return true;
				case R.id.navigation_me:
					mViewPager.setCurrentItem(4);
					return true;
			}
			return false;
		}

	};

	private ViewPager mViewPager;
	@SuppressLint("RestrictedApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setContentView(R.layout.activity_main);
		FragmentPagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
			@Override
			public int getCount() {
				return 4;
			}

			@Override
			public Fragment getItem(int position) {
				switch (position) {
					case 0:
						return new MeasureFragment();
					case 1:
						return new ChatFragment();
					case 2:
						return new NewsFragment();
					case 3:
						return new CommunityFragment();
					case 4:
						return new MeFragment();
					default:
						return new MeasureFragment();
				}
			}
		};
		mViewPager = findViewById(R.id.content);
		mViewPager.setAdapter(adapter);
		final BottomNavigationView navigation = findViewById(R.id.navigation);
		navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
		BottomNavigationMenuView menuView = (BottomNavigationMenuView) navigation.getChildAt(0);
		for (int i = 0; i < menuView.getChildCount(); i++) {
			BottomNavigationItemView itemView = (BottomNavigationItemView) menuView.getChildAt(i);
			itemView.setShiftingMode(false);
			itemView.setChecked(false);
		}
		mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			public MenuItem mSelectedMenuItem;

			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

			}

			@Override
			public void onPageSelected(int position) {
				if (mSelectedMenuItem != null) {
					mSelectedMenuItem.setChecked(false);
				} else {
					navigation.getMenu().getItem(0).setChecked(false);
				}

				mSelectedMenuItem = navigation.getMenu().getItem(position);
				mSelectedMenuItem.setChecked(true);
			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});

	}
}
