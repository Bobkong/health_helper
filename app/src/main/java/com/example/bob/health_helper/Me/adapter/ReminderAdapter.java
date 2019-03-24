package com.example.bob.health_helper.Me.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import com.example.bob.health_helper.Local.LocalBean.Reminder;
import com.example.bob.health_helper.Me.activity.ReminderDetailActivity;
import com.example.bob.health_helper.MyApplication;
import com.example.bob.health_helper.R;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReminderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Reminder> reminderList;
    private OnClickListener onClickListener;

    public ReminderAdapter(List<Reminder> reminders){
        reminderList=reminders;
    }

    static class ReminderItemViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.type)
        TextView type;
        @BindView(R.id.content)
        TextView content;
        @BindView(R.id.repeat)
        TextView repeat;
        @BindView(R.id.switcher)
        SwitchCompat switcher;
        public ReminderItemViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_reminder,viewGroup,false);
        ReminderItemViewHolder holder=new ReminderItemViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        Reminder reminder=reminderList.get(i);
        ReminderItemViewHolder holder=(ReminderItemViewHolder)viewHolder;
        int hourOfDay=reminder.getHour();
        int minute=reminder.getMinute();
        holder.time.setText((hourOfDay<10?("0"+hourOfDay):hourOfDay)+":"+((minute<10)?("0"+minute):minute));
        holder.type.setText(reminder.getType());
        holder.content.setText(reminder.getContent());
        holder.repeat.setText(parseWeekDay(reminder.getRepeat()));
        holder.switcher.setChecked(reminder.isEnable());
        holder.itemView.setOnClickListener((view ->{
            Intent intent=new Intent(view.getContext(), ReminderDetailActivity.class);
            intent.putExtra("reminder",reminder);
            view.getContext().startActivity(intent);
        }));
        holder.itemView.setOnLongClickListener(view -> {
            onClickListener.onLongClick(i);
            return false;
        });
        holder.switcher.setOnCheckedChangeListener((compoundButton,isChecked)->{
            onClickListener.OnCheckedChange(compoundButton,isChecked,i);
        });
    }

    @Override
    public int getItemCount() {
        return reminderList.size();
    }

    public interface OnClickListener{
        void onLongClick(int position);
        void OnCheckedChange(CompoundButton compoundButton,boolean isChecked,int position);
    }

    public void setOnClickListener(OnClickListener listener){
        this.onClickListener=listener;
    }

    private String parseWeekDay(int weekDay){
        String[] weekdays= MyApplication.getContext().getResources().getStringArray(R.array.week_days);
        StringBuilder stringBuilder=new StringBuilder();
        for(int i=0;i<weekdays.length;i++){
            if((weekDay>>i)%2==1)
                stringBuilder.append(weekdays[i]+" ");
        }
        return stringBuilder.toString();
    }
}
