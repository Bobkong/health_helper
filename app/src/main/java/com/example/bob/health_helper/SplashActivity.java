package com.example.bob.health_helper;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;

import com.example.bob.health_helper.Base.BaseActivity;
import com.orhanobut.logger.Logger;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

public class SplashActivity extends BaseActivity {
    private Handler handler=new Handler();
    private static final int DELAY_TIME = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //QQ或者新浪微博是否已授权
        final boolean isAuth= UMShareAPI.get(this).isAuthorize(SplashActivity.this, SHARE_MEDIA.QQ)||
                UMShareAPI.get(this).isAuthorize(SplashActivity.this, SHARE_MEDIA.SINA);

        if(isAuth){//跳转到主界面
            Logger.d("已授权");
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    navigateTo(MainActivity.class);
                }
            },DELAY_TIME);
        }else{//跳转到登录界面
            Logger.d("未授权");
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    navigateTo(LoginActivity.class);
                }
            },DELAY_TIME);
        }

        //Activity 转场动画，淡入淡出
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }
}
