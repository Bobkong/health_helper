package com.example.bob.health_helper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.example.bob.health_helper.Base.AppConstant;
import com.example.bob.health_helper.Bean.User;
import com.example.bob.health_helper.NetService.Api.UserService;
import com.example.bob.health_helper.Util.AgeUtil;
import com.example.bob.health_helper.Util.SharedPreferenceUtil;
import com.orhanobut.logger.Logger;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;


import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;


public class LoginActivity extends AppCompatActivity {
    private User user=new User();//本地用户类
    private Tencent tencent;
    private BaseUiListener uiListener;
    private UserInfo mUserInfo;//qq登录得到的用户信息
    private final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        tencent=MyApplication.getTencent();
    }

    @OnClick(R.id.qq_login)
    public void onClicked(View view){
        if(!tencent.checkSessionValid(AppConstant.QQ_APPID)){
            uiListener=new BaseUiListener();
            tencent.login(LoginActivity.this,"all",uiListener);
        }
    }

    private class BaseUiListener implements IUiListener{
        @Override
        public void onComplete(Object response) {
            Toast.makeText(LoginActivity.this,"授权成功",Toast.LENGTH_SHORT).show();
            JSONObject obj = (JSONObject) response;
            try {
                String openID = obj.getString("openid");
                String accessToken = obj.getString("access_token");
                String expires = obj.getString("expires_in");
                tencent.setOpenId(openID);
                tencent.setAccessToken(accessToken,expires);
                QQToken qqToken = tencent.getQQToken();
                mUserInfo = new UserInfo(getApplicationContext(),qqToken);
                //用户uid
                user.setUid(openID);
                mUserInfo.getUserInfo(new IUiListener() {
                    @Override
                    public void onComplete(Object response) {
                        if(response==null)
                            return;
                        Logger.e("获取用户信息成功");
                        JSONObject jsonObject=(JSONObject) response;
                        try{
                            user.setName(jsonObject.getString("nickname"));
                            user.setGender(jsonObject.getString("gender"));
                            user.setIconurl(jsonObject.getString("figureurl_qq_2"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        showHeightInputDialog();
                    }
                    @Override
                    public void onError(UiError uiError) {
                        Logger.e("获取信息失败");
                    }

                    @Override
                    public void onCancel() {
                        Logger.e("取消");
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError(UiError uiError) {
            Toast.makeText(LoginActivity.this,"授权失败",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel() {
            Toast.makeText(LoginActivity.this,"授权取消",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == Constants.REQUEST_LOGIN){
            Tencent.onActivityResultData(requestCode,resultCode,data,uiListener);
        }
        super.onActivityResult(requestCode, resultCode, data);
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
        next.setOnClickListener(view1 -> {
			//保存用户身高信息
			user.setHeight(Integer.valueOf(numberPicker.getValue()));
			showWeightInputDialog();
			dialog.dismiss();
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
        next.setOnClickListener(view1 -> {
			//保存用户体重信息
			user.setWeight(Float.valueOf(numberPicker1.getValue()+"."+numberPicker2.getValue()));
			showBirthInputDialog();
			dialog.dismiss();
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
        finish.setOnClickListener(view1 -> {
			//保存用户年龄
			DateFormat fmt=new SimpleDateFormat("yyyy-MM-dd");
			try{
				Date date=fmt.parse(datePicker.getYear()+"-"+(datePicker.getMonth()+1)+"-"+datePicker.getDayOfMonth());
				user.setAge(AgeUtil.getAgeFromDate(date));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			//持久化保存用户个人信息
			SharedPreferenceUtil.saveUser(user);
			dialog.dismiss();
			Intent intent=new Intent(LoginActivity.this,MainActivity.class);
			startActivity(intent);
            addUser2Server();
		});
        dialog.show();
    }

    private void addUser2Server() {
        UserService.getUserService().addUser(user)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                            if (response.getSuccess()){
                                Log.d(TAG,"add user success : user = " + user.toString());
                            }else {
                                Log.d(TAG,"add user failed : err = " + response.getErr().toString());
                            }
                        }
                        , Throwable::printStackTrace);
    }
}
