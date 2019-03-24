package com.example.bob.health_helper.Me.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bob.health_helper.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WeekDayGridAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private String[] weekDayList={"日","一","二","三","四","五","六"};
    private int selected=0;

    public WeekDayGridAdapter(int selected){
        this.selected=selected;
    }

    static class WeekDayItemViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.weekday_item)
        TextView weekDayText;
        public WeekDayItemViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_reminder_weekday,viewGroup,false);
        WeekDayItemViewHolder holder=new WeekDayItemViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        WeekDayItemViewHolder holder=(WeekDayItemViewHolder)viewHolder;
        holder.weekDayText.setText(weekDayList[i]);
        if((selected>>i)%2==1)
            holder.weekDayText.setSelected(true);
        else
            holder.weekDayText.setSelected(false);
        holder.weekDayText.setOnClickListener(view->{
            if(view.isSelected()){//取消
                selected-=(1<<i);
                if(selected<=0){
                    selected+=(1<<i);
                    return;
                }
                holder.weekDayText.setTextColor(view.getContext().getResources().getColor(R.color.colorTextPrimaryMoreLight));
                view.setSelected(false);
            }else{//选中
                selected+=(1<<i);
                holder.weekDayText.setTextColor(view.getContext().getResources().getColor(R.color.colorOrange));
                view.setSelected(true);
            }
        });
    }

    @Override
    public int getItemCount() {
        return weekDayList.length;
    }

    public int getSelected(){
        return selected;
    }
}
