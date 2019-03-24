package com.example.bob.health_helper.Me;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.bob.health_helper.Local.LocalBean.Reminder;
import com.example.bob.health_helper.Me.activity.ReminderActivity;
import com.example.bob.health_helper.R;
import com.example.bob.health_helper.Util.AlarmManagerUtil;
import com.orhanobut.logger.Logger;

public class ReminderReceiver extends BroadcastReceiver {
    private static int notificationId=0;
    @Override
    public void onReceive(Context context, Intent intent) {
        int id=intent.getIntExtra("id",0);
        int hour=intent.getIntExtra("hour",0);
        int minute=intent.getIntExtra("minute",0);
        int weekday=intent.getIntExtra("weekday",0);
        String tips=intent.getStringExtra("tips");
        Logger.e("receive");
        Intent i=new Intent(context, ReminderActivity.class);
        PendingIntent pi=PendingIntent.getActivity(context,0,i,0);
        NotificationManager manager=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification=new NotificationCompat.Builder(context,"default")
                .setContentTitle(context.getString(R.string.app_name))
                .setContentText(tips)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.health)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.drawable.ic_launcher))
                .setVibrate(new long[]{0,2000,1000,2000})
                .setContentIntent(pi)
                .setAutoCancel(true)
                .build();
        manager.notify(notificationId++,notification);
        Logger.e("notify");

        AlarmManagerUtil.cancelAlarm(context,id);
        AlarmManagerUtil.setAlarm(context,id,hour,minute,weekday,tips);
        Logger.e("reset");
    }
}
