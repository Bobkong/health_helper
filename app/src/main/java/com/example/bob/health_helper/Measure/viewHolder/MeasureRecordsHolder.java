package com.example.bob.health_helper.Measure.viewHolder;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;


import com.example.bob.health_helper.Bean.MeasureBean;
import com.example.bob.health_helper.Bean.MeasureData;
import com.example.bob.health_helper.Measure.activity.MeasureReportActivity;
import com.example.bob.health_helper.NetService.Api.MeasureService;
import com.example.bob.health_helper.R;
import com.example.bob.health_helper.Util.DateUtil;

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
	private MeasureBean mMeasureBean;
	private Context mContext;
	public MeasureRecordsHolder(@NonNull View itemView) {
		super(itemView);
		ButterKnife.bind(this, itemView);
		itemView.setOnClickListener(view -> mContext.startActivity(new Intent(mContext, MeasureReportActivity.class)
				.putExtra("MEASURE_DATA",mMeasureBean)));
	}

	public void bind(Context context,MeasureBean weightListData){
		mDate.setText(DateUtil.dateTransform(weightListData.getTime()));
		mWeight.setText(String.valueOf(weightListData.getWeight()) + "kg");
		mMeasureBean = weightListData;
		mContext = context;
	}
}
