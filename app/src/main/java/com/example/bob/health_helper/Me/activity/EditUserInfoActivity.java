package com.example.bob.health_helper.Me.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;


import com.example.bob.health_helper.Base.BaseActivity;
import com.example.bob.health_helper.Bean.User;
import com.example.bob.health_helper.R;
import com.example.bob.health_helper.Util.AgeUtil;
import com.example.bob.health_helper.Util.SharedPreferenceUtil;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditUserInfoActivity extends BaseActivity {
    @BindView(R.id.user_name)
    EditText userName;
    @BindView(R.id.user_gender)
    EditText userGender;
    @BindView(R.id.user_height)
    TextView userHeight;
    @BindView(R.id.user_weight)
    TextView userWeight;
    @BindView(R.id.user_age)
    TextView userAge;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private User user=SharedPreferenceUtil.getUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_info);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);//显示返回键
            actionBar.setTitle(R.string.user_info);
        }
        initUserInfo();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.confirm,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            case R.id.confirm:
                //to do--upload to server
                user.setName(userName.getText().toString());
                user.setGender(userGender.getText().toString());
                SharedPreferenceUtil.saveUser(user);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.user_height,R.id.user_weight,R.id.user_age})
    void onClicked(View view){
        switch (view.getId()){
            case R.id.user_height:
                showHeightInputDialog();
                break;
            case R.id.user_weight:
                showWeightInputDialog();
                break;
            case R.id.user_age:
                showBirthInputDialog();
                break;
        }
    }

    private void initUserInfo() {
        userName.setText(user.getName());
        userHeight.setText(String.valueOf(user.getHeight()));
        userWeight.setText(String.valueOf(user.getWeight()));
        userGender.setText(user.getGender());
        userAge.setText(String.valueOf(user.getAge()));
    }

    /**
     * 身高输入
     */
    public void showHeightInputDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        View view= View.inflate(this,R.layout.dialog_height_input,null);
        NumberPicker numberPicker=view.findViewById(R.id.dialog_height_picker);
        Button finish=view.findViewById(R.id.dialog_height_next);
        finish.setText(R.string.finish);

        numberPicker.setMinValue(30);
        numberPicker.setMaxValue(280);
        numberPicker.setValue(160);
        numberPicker.setWrapSelectorWheel(false);
        numberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        AlertDialog dialog=builder.setView(view).create();
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //保存用户身高信息
                user.setHeight(Integer.valueOf(numberPicker.getValue()));
                dialog.dismiss();
                userHeight.setText(String.valueOf(user.getHeight()));
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
        next.setText(R.string.finish);

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
                user.setWeight(Float.valueOf(numberPicker1.getValue()+"."+numberPicker2.getValue()));
                dialog.dismiss();
                userWeight.setText(String.valueOf(user.getWeight()));
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
                //保存用户年龄
                DateFormat fmt=new SimpleDateFormat("yyyy-MM-dd");
                try{
                    Date date=fmt.parse(datePicker.getYear()+"-"+(datePicker.getMonth()+1)+"-"+datePicker.getDayOfMonth());
                    user.setAge(AgeUtil.getAgeFromDate(date));

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                dialog.dismiss();
                userAge.setText(String.valueOf(user.getAge()));
            }
        });
        dialog.show();
    }
}
