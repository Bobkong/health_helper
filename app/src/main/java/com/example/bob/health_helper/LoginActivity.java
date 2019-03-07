package com.example.bob.health_helper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.Toast;
import com.example.bob.health_helper.Bean.User;
import com.example.bob.health_helper.Util.SharedPreferenceUtil;
import com.orhanobut.logger.Logger;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Calendar;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoginActivity extends AppCompatActivity {
    private User user=new User();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.qq_login,R.id.weibo_login})
    public void onClicked(View view){
        switch (view.getId()){
            case R.id.qq_login:
                UMShareAPI.get(this).getPlatformInfo(this, SHARE_MEDIA.QQ,umAuthListener);
                break;
            case R.id.weibo_login:
                UMShareAPI.get(this).getPlatformInfo(this, SHARE_MEDIA.SINA,umAuthListener);
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
            //持久化保存登录方式
            if(platform==SHARE_MEDIA.QQ)
                SharedPreferenceUtil.saveBooleanKeyValue("isQQLogin",true);
            else if(platform==SHARE_MEDIA.SINA)
                SharedPreferenceUtil.saveBooleanKeyValue("isSinaLogin",true);
            //获取用户信息
            user.setUid(data.get("uid"));
            user.setName(data.get("name"));
            user.setGender(data.get("gender"));
            user.setIconurl(data.get("iconurl"));
            //弹出完善用户信息
            Toast.makeText(LoginActivity.this, "成功了", Toast.LENGTH_SHORT).show();
            showHeightInputDialog();
        }

        /**
         * @desc 授权失败的回调
         * @param platform 平台名称
         * @param action 行为序号
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText(LoginActivity.this, "授权失败：" + t.getMessage(),Toast.LENGTH_LONG).show();
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

    /**
     * 身高输入
     */
    public void showHeightInputDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        View view= View.inflate(this,R.layout.dialog_height_input,null);
        NumberPicker numberPicker=view.findViewById(R.id.dialog_height_picker);
        Button next=view.findViewById(R.id.dialog_height_next);
        numberPicker.setMinValue(30);
        numberPicker.setMaxValue(280);
        numberPicker.setValue(160);
        numberPicker.setWrapSelectorWheel(false);
        numberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        AlertDialog dialog=builder.setView(view).create();
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logger.e(numberPicker.getValue()+"");
                //保存用户身高信息
                user.setHeight(numberPicker.getValue()+"cm");
                showWeightInputDialog();
                dialog.dismiss();
            }
        });
       dialog.show();
    }

    /**
     * 体重输入
     */
    public void showWeightInputDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        View view= View.inflate(this,R.layout.dialog_weight_input,null);
        NumberPicker numberPicker1=view.findViewById(R.id.dialog_weight_picker1);
        NumberPicker numberPicker2=view.findViewById(R.id.dialog_weight_picker2);
        Button next=view.findViewById(R.id.dialog_weight_next);
        numberPicker1.setMinValue(1);
        numberPicker1.setMaxValue(150);
        numberPicker1.setValue(45);
        numberPicker2.setMinValue(0);
        numberPicker2.setMaxValue(9);
        numberPicker2.setValue(5);
        numberPicker1.setWrapSelectorWheel(false);
        numberPicker2.setWrapSelectorWheel(false);
        numberPicker1.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        numberPicker2.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        AlertDialog dialog=builder.setView(view).create();
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logger.e(numberPicker1.getValue()+"."+numberPicker2.getValue());
                //保存用户体重信息
                user.setWeight(numberPicker1.getValue()+"."+numberPicker2.getValue()+"kg");
                showBirthInputDialog();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    /**
     * 出生日期输入
     */
    public void showBirthInputDialog() {
        final Calendar calendar = Calendar.getInstance();
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        View view= View.inflate(this,R.layout.dialog_birth_input,null);
        DatePicker datePicker=view.findViewById(R.id.dialog_birth_picker);
        Button finish=view.findViewById(R.id.dialog_birth_finish);
        AlertDialog dialog=builder.setView(view).create();
        datePicker.init(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH),
                new DatePicker.OnDateChangedListener(){
                    @Override
                    public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {

                    }
                });
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //保存用户出生信息xxxx-xx-xx
                user.setBirth(datePicker.getYear()+"-"+(datePicker.getMonth()+1)+"-"+datePicker.getDayOfMonth());
                //持久化保存用户个人信息
                SharedPreferenceUtil.saveUser(user);
                dialog.dismiss();
                Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        dialog.show();
    }
}
