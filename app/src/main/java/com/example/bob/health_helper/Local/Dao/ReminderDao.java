package com.example.bob.health_helper.Local.Dao;

import android.content.Context;

import com.example.bob.health_helper.Local.DatabaseHelper;
import com.example.bob.health_helper.Local.LocalBean.Reminder;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

public class ReminderDao {
    private Dao<Reminder,Integer> reminderDao;
    private DatabaseHelper helper;

    public ReminderDao(Context context){
        try{
            helper=DatabaseHelper.getInstance(context);
            reminderDao=helper.getDao(Reminder.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addOrUpdateReminder(Reminder reminder){
        try{
            reminderDao.createOrUpdate(reminder);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteReminder(Reminder reminder){
        try{
            reminderDao.delete(reminder);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Reminder> queryAll(){
        try{
            return reminderDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
