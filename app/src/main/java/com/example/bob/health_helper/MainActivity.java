package com.example.bob.health_helper;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.bob.health_helper.Chat.fragment.ChatFragment;
import com.example.bob.health_helper.Community.CommunityFragment;
import com.example.bob.health_helper.Event.LogoutEvent;
import com.example.bob.health_helper.Me.MeFragment;
import com.example.bob.health_helper.NetService.Api.UserService;
import com.example.bob.health_helper.News.NewsFragment;

import com.example.bob.health_helper.Receiver.MiPushMessageReceiver;
import com.example.bob.health_helper.Util.SharedPreferenceUtil;
import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMOfflinePushToken;
import com.orhanobut.logger.Logger;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;

import static com.example.bob.health_helper.Me.MeFragment.tencentLogout;

public class MainActivity extends AppCompatActivity {
	@BindView(R.id.main_navigation)
	BottomNavigationView bottomNavigationView;
	@BindView(R.id.main_content)
	ViewPager viewPager;
	private MenuItem preMenuItem;
	private final static String TAG = "MainActivity";
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if (TIMManager.getInstance().getLoginUser().equals("")) {
			SplashActivity.tencentIMLogin();
		}
		ButterKnife.bind(this);
		EventBus.getDefault().register(this);
		bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
			@Override
			public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
				switch (menuItem.getItemId()){
					case R.id.navigation_measure:
						viewPager.setCurrentItem(0);
						break;
					case R.id.navigation_chat:
						viewPager.setCurrentItem(1);
						break;
					case R.id.navigation_news:
						viewPager.setCurrentItem(2);
						break;
					case R.id.navigation_community:
						viewPager.setCurrentItem(3);
						break;
					case R.id.navigation_me:
						viewPager.setCurrentItem(4);
						break;
				}
				return false;
			}
		});

		viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int i, float v, int i1) {

			}

			@Override
			public void onPageSelected(int i) {
				if(preMenuItem!=null)
					preMenuItem.setChecked(false);
				else
					bottomNavigationView.getMenu().getItem(0).setChecked(false);
				bottomNavigationView.getMenu().getItem(i).setChecked(true);
				preMenuItem=bottomNavigationView.getMenu().getItem(i);
			}

			@Override
			public void onPageScrollStateChanged(int i) {

			}
		});

		FragmentStatePagerAdapter adapter=new FragmentStatePagerAdapter(getSupportFragmentManager()) {
			@Override
			public Fragment getItem(int i) {
				switch (i){
					case 0:return new MeasureFragment();
					case 1:return new ChatFragment();
					case 2:return new NewsFragment();
					case 3:return new CommunityFragment();
					case 4:return new MeFragment();
					default:return new MeasureFragment();
				}
			}

			@Override
			public int getCount() {
				return 5;
			}
		};

		viewPager.setAdapter(adapter);
	}

	@Subscribe(threadMode = ThreadMode.MAIN)
	public void onLogout(LogoutEvent logoutEvent){
		Logger.e("finish");
		finish();
	}

	@Override
	protected void onDestroy() {
		tencentLogout();
		super.onDestroy();
	}

}


