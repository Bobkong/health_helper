package com.example.bob.health_helper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.bob.health_helper.Base.BaseActivity;
import com.example.bob.health_helper.Community.activity.SearchActivity;
import com.orhanobut.logger.Logger;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoginActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.qq_login,R.id.weibo_login,R.id.qq_logout,R.id.weibo_logout})
    public void onClicked(View view){
        switch (view.getId()){
            case R.id.qq_login:
                UMShareAPI.get(this).getPlatformInfo(this, SHARE_MEDIA.QQ,umAuthListener);
                break;
            case R.id.weibo_login:
                UMShareAPI.get(this).getPlatformInfo(this, SHARE_MEDIA.SINA,umAuthListener);
                break;
            case R.id.qq_logout:
                UMShareAPI.get(this).deleteOauth(this, SHARE_MEDIA.QQ, umAuthListener);
                break;
            case R.id.weibo_logout:
                UMShareAPI.get(this).deleteOauth(this, SHARE_MEDIA.SINA, umAuthListener);
                break;
        }
    }

    private UMAuthListener umAuthListener = new UMAuthListener() {
        /**
         * @desc 授权开始的回调
         * @param platform 平台名称
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @desc 授权成功的回调
         * @param platform 平台名称
         * @param action 行为序号
         * @param data 用户资料返回
         */
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            Logger.d("授权成功");
            Toast.makeText(LoginActivity.this, "成功了", Toast.LENGTH_SHORT).show();
            //navigateTo(SearchActivity.class);
        }

        /**
         * @desc 授权失败的回调
         * @param platform 平台名称
         * @param action 行为序号
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText(LoginActivity.this, "授权失败：" + t.getMessage(),                                     Toast.LENGTH_LONG).show();
        }

        /**
         * @desc 授权取消的回调
         * @param platform 平台名称
         * @param action 行为序号
         */
        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText(LoginActivity.this, "授权取消", Toast.LENGTH_LONG).show();
        }
    };

    //qq与新浪微博授权回调
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
}
