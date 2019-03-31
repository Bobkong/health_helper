package com.example.bob.health_helper.Measure.viewHolder;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.bob.health_helper.R;
import com.example.bob.health_helper.Util.DateUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Bob on 2018/12/7.
 * "身体数据"视图容器
 */
public class MostRecentWeightHolder extends RecyclerView.ViewHolder{
	@BindView(R.id.weight_data)
	TextView mWeight;
	@BindView(R.id.time)
	TextView mTime;
	public MostRecentWeightHolder(@NonNull View itemView) {
		super(itemView);
		ButterKnife.bind(this, itemView);
	}

	@SuppressLint("SetTextI18n")
	public void bind(double weight, String time){
		mWeight.setText(String.valueOf(weight) + "kg");
		mTime.setText(DateUtil.dateTransform(time));
	}
}
