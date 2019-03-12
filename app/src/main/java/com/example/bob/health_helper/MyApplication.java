package com.example.bob.health_helper;

import android.app.Application;
import android.content.Context;

import com.example.bob.health_helper.Base.AppConstant;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.tencent.tauth.Tencent;

public class MyApplication extends Application {
    private static Context context;
    private static Tencent tencent;
    @Override
    public void onCreate() {
        super.onCreate();
        //方便一般类获取上下文
        context=getApplicationContext();
        //qq登录初始化
        tencent=Tencent.createInstance(AppConstant.QQ_APPID,context);
        //科大讯飞
        SpeechUtility.createUtility(this, SpeechConstant.APPID+"="+AppConstant.IFLYKE_APPID);
        //Logger
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    public static Context getContext(){
        return context;
    }

    public static Tencent getTencent(){return tencent;}
}
