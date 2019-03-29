package com.example.bob.health_helper.Measure.viewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;


import com.example.bob.health_helper.Bean.MeasureData;
import com.example.bob.health_helper.R;

import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Bob on 2018/12/7.
 * 体重数据列表项的视图容器
 */
public class MeasureRecordsHolder extends RecyclerView.ViewHolder {
	@BindView(R.id.date)
	TextView mDate;
	@BindView(R.id.weight)
	TextView mWeight;
	public MeasureRecordsHolder(@NonNull View itemView) {
		super(itemView);
		ButterKnife.bind(this, itemView);
	}

	public void bind(MeasureData weightListData){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String time = dateFormat.format(weightListData.getTime());
		mDate.setText(time);
		mWeight.setText(String.valueOf(weightListData.getWeight()) + "kg");
	}
}
