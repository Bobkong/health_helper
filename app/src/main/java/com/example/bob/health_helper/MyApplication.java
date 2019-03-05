package com.example.bob.health_helper;

import android.app.Application;
import android.content.Context;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;


public class MyApplication extends Application {
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        //方便一般类获取上下文
        context=getApplicationContext();
        //友盟
        UMConfigure.init(this, BuildConfig.UM_APPKEY,"Umeng", UMConfigure.DEVICE_TYPE_PHONE,"");
        UMConfigure.setLogEnabled(true);//启用log
        //QQ登录
        PlatformConfig.setQQZone(BuildConfig.QQ_APPID, BuildConfig.QQ_APPKEY);
        //新浪微博登录
        PlatformConfig.setSinaWeibo(BuildConfig.WEIBO_APPKEY, BuildConfig.WEIBO_APPSECRET,"http://sns.whalecloud.com");
        //科大讯飞
        SpeechUtility.createUtility(this, SpeechConstant.APPID+"="+BuildConfig.IFLYKE_APPID);
        //Logger
        Logger.addLogAdapter(new AndroidLogAdapter());

    }

    public static Context getContext(){
        return context;
    }
}
