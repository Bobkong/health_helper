package com.example.bob.health_helper;

import android.app.Application;
import android.content.Context;
import android.os.Build;

import com.example.bob.health_helper.Base.AppConstant;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.lifesense.ble.LsBleManager;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.session.SessionWrapper;
import com.tencent.qcloud.uikit.BaseUIKitConfigs;
import com.tencent.qcloud.uikit.IMEventListener;
import com.tencent.qcloud.uikit.TUIKit;
import com.tencent.qcloud.uikit.common.utils.UIUtils;
import com.tencent.tauth.Tencent;
import com.xiaomi.mipush.sdk.MiPushClient;

import java.util.Locale;

import static com.example.bob.health_helper.Constants.MI_PUSH_APP_ID;
import static com.example.bob.health_helper.Constants.MI_PUSH_APP_KEY;

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
        registerPush();
        //初始化 SDK 基本配置
        //判断是否是在主线程
        if (SessionWrapper.isMainProcess(getApplicationContext())) {
            /**
             * TUIKit的初始化函数
             *
             * @param context  应用的上下文，一般为对应应用的ApplicationContext
             * @param sdkAppID 您在腾讯云注册应用时分配的sdkAppID
             * @param configs  TUIKit的相关配置项，一般使用默认即可，需特殊配置参考API文档
             */
            long current = System.currentTimeMillis();
            TUIKit.init(this, Constants.sdkAppId, BaseUIKitConfigs.getDefaultConfigs());
            System.out.println(">>>>>>>>>>>>>>>>>>"+(System.currentTimeMillis()-current));
            //添加自定初始化配置
            customConfig();
            System.out.println(">>>>>>>>>>>>>>>>>>"+(System.currentTimeMillis()-current));
            // 设置离线推送监听器
            TIMManager.getInstance().setOfflinePushListener(notification -> notification.doNotify(getApplicationContext(), R.drawable.ic_launcher));

            //init LsBleManager
            LsBleManager.getInstance().initialize(getApplicationContext());
            //register bluetooth broadacst receiver
            LsBleManager.getInstance().registerBluetoothBroadcastReceiver(getApplicationContext());
        }
    }

    public static Context getContext(){
        return context;
    }

    public static Tencent getTencent(){return tencent;}

    private void customConfig() {
        if (TUIKit.getBaseConfigs() != null) {
            //注册IM事件回调，这里示例为用户被踢的回调，更多事件注册参考文档
            TUIKit.getBaseConfigs().setIMEventListener(new IMEventListener() {
                @Override
                public void onForceOffline() {
                    UIUtils.toastLongMessage("您的账号已在其它终端登录");
                }
            });

        }
    }

    public void registerPush(){
        String vendor = Build.MANUFACTURER;
        if(vendor.toLowerCase(Locale.ENGLISH).contains("xiaomi")) {
            //注册小米推送服务
            MiPushClient.registerPush(this, MI_PUSH_APP_ID, MI_PUSH_APP_KEY);
        }else if(vendor.toLowerCase(Locale.ENGLISH).contains("huawei")) {
            //请求华为推送设备 token
            //PushManager.requestToken(this);
        }
    }
}
