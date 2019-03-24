package com.example.bob.health_helper.Me.activity;

import android.content.DialogInterface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;

import com.example.bob.health_helper.Base.BaseActivity;
import com.example.bob.health_helper.Local.Dao.ReminderDao;
import com.example.bob.health_helper.Local.LocalBean.Reminder;
import com.example.bob.health_helper.Me.adapter.ReminderAdapter;
import com.example.bob.health_helper.R;
import com.example.bob.health_helper.Util.AlarmManagerUtil;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ReminderActivity extends BaseActivity {
    @BindView(R.id.reminder_list)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private ReminderDao reminderDao;
    private List<Reminder> reminders=new ArrayList<>();
    private ReminderAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setTitle(getString(R.string.my_reminder));
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        reminderDao=new ReminderDao(this);
        List<Reminder> result=reminderDao.queryAll();
        reminders=(result==null||result.size()==0)?reminders:result;
        Logger.e(reminders.toString());
        adapter=new ReminderAdapter(reminders);
        adapter.setOnClickListener(
                new ReminderAdapter.OnClickListener() {
                       @Override
                       public void onLongClick(int position) {
                           showDeleteReminderDialog(position);
                       }

                       @Override
                       public void OnCheckedChange(CompoundButton compoundButton, boolean isChecked,int position) {
                           Reminder reminder=reminders.get(position);
                           if(isChecked){
                               reminder.setEnable(true);
                               reminderDao.addOrUpdateReminder(reminder);
                               AlarmManagerUtil.setAlarm(compoundButton.getContext(),reminder.getId(),reminder.getHour(),reminder.getMinute(),
                                       reminder.getRepeat(),reminder.getType()+"-"+reminder.getContent());
                           }else {
                               reminder.setEnable(false);
                               reminderDao.addOrUpdateReminder(reminder);
                               AlarmManagerUtil.cancelAlarm(compoundButton.getContext(),reminder.getId());
                           }
                       }
                   }
        );
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            case R.id.add:
                navigateTo(ReminderDetailActivity.class);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDeleteReminderDialog(int position) {
        AlertDialog dialog=new AlertDialog.Builder(ReminderActivity.this)
                .setTitle(getString(R.string.confirm))
                .setMessage(getString(R.string.confirm_delete))
                .setPositiveButton(getString(R.string.confirm), (dialogInterface,i)->{
                        reminderDao.deleteReminder(reminders.get(position));
                        AlarmManagerUtil.cancelAlarm(ReminderActivity.this,reminders.get(position).getId());
                        reminders.remove(position);
                        adapter.notifyDataSetChanged();
                    })
                .setNegativeButton(getString(R.string.cancel),((dialogInterface, i) -> {}))
                .create();
        dialog.show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAddReminder(Reminder reminder){
        for(Reminder item:reminders){
            if(item.getId()==reminder.getId()){
                reminders.remove(item);
                break;
            }
        }
        reminders.add(0,reminder);
        adapter.notifyDataSetChanged();
    }
}
