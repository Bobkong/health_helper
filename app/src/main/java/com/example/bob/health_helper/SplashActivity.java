package com.example.bob.health_helper;

import android.os.Handler;
import android.os.Bundle;
import android.widget.Toast;

import com.example.bob.health_helper.Base.AppConstant;
import com.example.bob.health_helper.Base.BaseActivity;
import com.orhanobut.logger.Logger;


public class SplashActivity extends BaseActivity {
    private Handler handler=new Handler();
    private static final int DELAY_TIME = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        boolean isValid=MyApplication.getTencent().checkSessionValid(AppConstant.QQ_APPID);
        if(!isValid){
            Toast.makeText(this,"登录已过期，请重新登录",Toast.LENGTH_SHORT).show();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    navigateTo(LoginActivity.class);
                }
            },DELAY_TIME);
        }else {
            Logger.e("已授权");
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    navigateTo(MainActivity.class);
                }
            },DELAY_TIME);
        }
        //Activity 转场动画，淡入淡出
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }
}
