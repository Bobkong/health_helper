package com.example.bob.health_helper.Me.activity;


import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


import com.example.bob.health_helper.Base.BaseActivity;
import com.example.bob.health_helper.Local.Dao.ReminderDao;
import com.example.bob.health_helper.Local.LocalBean.Reminder;
import com.example.bob.health_helper.Local.LocalBean.ReminderType;
import com.example.bob.health_helper.Me.adapter.WeekDayGridAdapter;
import com.example.bob.health_helper.R;
import com.example.bob.health_helper.Util.AlarmManagerUtil;
import com.google.android.flexbox.FlexboxLayout;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReminderDetailActivity extends BaseActivity {

    @BindView(R.id.time)
    TextView timeView;
    @BindView(R.id.recycle)
    RecyclerView repeatView;
    @BindView(R.id.type)
    FlexboxLayout typeView;
    @BindView(R.id.remark)
    EditText remarkView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @OnClick(R.id.time)
    void onClicked(){
        showTimePickerDialog();
    }

    private final List<String> textList=new ArrayList<>();
    private List<ReminderType> typeList=new ArrayList<>();

    private Reminder reminder;
    private ReminderDao reminderDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_detail);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(getString(R.string.my_reminder));
        }

        reminderDao=new ReminderDao(this);
        reminder=(Reminder)getIntent().getSerializableExtra("reminder");
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        repeatView.setLayoutManager(linearLayoutManager);
        textList.addAll(Arrays.asList(getString(R.string.medicine),getString(R.string.measure),
                getString(R.string.sports),getString(R.string.sleep),getString(R.string.wakeup),getString(R.string.common)));
        for(int i=0;i<textList.size();i++)
            typeList.add(new ReminderType(textList.get(i)));
        if(reminder!=null){
            int hourOfDay=reminder.getHour();
            int minute=reminder.getMinute();
            timeView.setText((hourOfDay<10?("0"+hourOfDay):hourOfDay)+":"+((minute<10)?("0"+minute):minute));
            repeatView.setAdapter(new WeekDayGridAdapter(reminder.getRepeat()));
            for(int i=0;i<textList.size();i++){
                if(textList.get(i).equals(reminder.getType()))
                    typeList.get(i).setChecked(true);
            }
            remarkView.setText(reminder.getContent());
        }else{
            reminder=new Reminder();
            repeatView.setAdapter(new WeekDayGridAdapter(0));
        }
        for(int i=0;i<typeList.size();i++)
            addChildToFlexboxLayout(typeList.get(i));
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
                if(TextUtils.isEmpty(timeView.getText().toString()))
                    showTips(getString(R.string.to_select_time));
                else if(((WeekDayGridAdapter)repeatView.getAdapter()).getSelected()==0)
                    showTips(getString(R.string.to_select_repeat));
                else if(TextUtils.isEmpty(reminder.getType()))
                    showTips(getString(R.string.to_select_type));
                else if(TextUtils.isEmpty(remarkView.getText().toString()))
                    showTips(getString(R.string.to_add_remark));
                else{
                    reminder.setRepeat(((WeekDayGridAdapter)repeatView.getAdapter()).getSelected());
                    reminder.setContent(remarkView.getText().toString());
                    reminder.setEnable(true);
                    reminderDao.addOrUpdateReminder(reminder);
                    AlarmManagerUtil.setAlarm(this,reminder.getId(),reminder.getHour(),reminder.getMinute(),
                            reminder.getRepeat(),reminder.getType()+"-"+reminder.getContent());
                    EventBus.getDefault().post(reminder);
                    finish();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showTimePickerDialog() {
        TimePickerDialog dialog=new TimePickerDialog(this, AlertDialog.THEME_HOLO_LIGHT, (timePicker,hourOfDay,minute)->{
            timeView.setText((hourOfDay<10?("0"+hourOfDay):hourOfDay)+":"+((minute<10)?("0"+minute):minute));
            reminder.setHour(hourOfDay);
            reminder.setMinute(minute);
        },reminder.getHour(),reminder.getMinute(),true);
        dialog.show();
    }

    //添加类型到布局
    private void addChildToFlexboxLayout(ReminderType bean) {
           View view= LayoutInflater.from(this).inflate(R.layout.item_reminder_type,null);
           TextView tv=view.findViewById(R.id.tv);
           tv.setText(bean.getText());
           tv.setTag(bean);
           if(bean.isChecked()){
               tv.setBackgroundResource(R.drawable.shape_reminder_type_label);
               tv.setTextColor(getResources().getColor(R.color.colorOrange));
           }else {
               tv.setBackgroundResource(R.drawable.shape_reminder_type);
               tv.setTextColor(getResources().getColor(R.color.black));
           }
           view.setOnClickListener(view1 -> {
               bean.setChecked(true);
               reminder.setType(bean.getText());
               for(ReminderType reminderType:typeList){
                   if(!reminderType.equals(bean))
                       reminderType.setChecked(false);
               }
               typeView.removeAllViews();
               for(ReminderType reminderType:typeList)
                   addChildToFlexboxLayout(reminderType);
           });
           typeView.addView(view);
    }

}
