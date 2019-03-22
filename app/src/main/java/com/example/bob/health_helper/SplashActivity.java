package com.example.bob.health_helper;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.Toast;

import com.example.bob.health_helper.Base.AppConstant;
import com.example.bob.health_helper.Base.BaseActivity;
import com.example.bob.health_helper.Receiver.MiPushMessageReceiver;
import com.example.bob.health_helper.Util.SharedPreferenceUtil;
import com.orhanobut.logger.Logger;
import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMOfflinePushToken;

import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;


public class SplashActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {
    private static final String TAG = "SplashActivity";
    private Handler handler=new Handler();
    private static final int DELAY_TIME = 2000;

    private static final String[] PERMISSIONS={Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.RECORD_AUDIO};
    private static final int RC_PERM=123;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //权限处理
        checkpermissions();
        if (TIMManager.getInstance().getLoginUser().equals("")) {
            tencentIMLogin();
        }
    }

    public static void tencentIMLogin() {
        if (SharedPreferenceUtil.getUser() == null){
            return;
        }
        TIMManager.getInstance().login(SharedPreferenceUtil.getUser().getName(), SharedPreferenceUtil.getUser().getSig(), new TIMCallBack() {
            @Override
            public void onError(int code, String desc) {
                //错误码 code 和错误描述 desc，可用于定位请求失败原因
                //错误码 code 列表请参见错误码表
                Log.d(TAG, "login failed. code: " + code + " errmsg: " + desc);
            }
            @Override
            public void onSuccess() {

                Log.d(TAG,"login success");
                //登录成功后，上报证书 ID 及设备 token
                TIMOfflinePushToken param = new TIMOfflinePushToken(com.example.bob.health_helper.Constants.MI_PUSH_BUSS_ID, MiPushMessageReceiver.mRegId);
                TIMManager.getInstance().setOfflinePushToken(param, new TIMCallBack() {
                    @Override
                    public void onError(int i, String s) {

                    }

                    @Override
                    public void onSuccess() {

                    }
                });
            }
        });

    }
    private void checkpermissions() {
        if(EasyPermissions.hasPermissions(this,PERMISSIONS))
            processLogic();
        else
            EasyPermissions.requestPermissions(this,getString(R.string.require_permission), RC_PERM,PERMISSIONS);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults,this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        if(EasyPermissions.hasPermissions(this,PERMISSIONS))
            processLogic();
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        //用户勾选了不再询问且拒绝权限，跳转到设置界面手动开启
        if(EasyPermissions.somePermissionPermanentlyDenied(this,perms)){
            new AppSettingsDialog.Builder(this)
                    .setTitle(getString(R.string.required_permission_denied_title))
                    .setRationale(getString(R.string.required_permission_denied_content))
                    .build().show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Logger.e("从setting回来");
        if(requestCode==AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE)
            checkpermissions();
    }

    @RequiresApi(api = Build.VERSION_CODES.ECLAIR)
    private void processLogic() {
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
