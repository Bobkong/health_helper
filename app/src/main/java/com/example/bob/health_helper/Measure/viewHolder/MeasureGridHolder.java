package com.example.bob.health_helper.Measure.viewHolder;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.bob.health_helper.Bean.MeasureData;
import com.example.bob.health_helper.Measure.activity.MeasureReportActivity;
import com.example.bob.health_helper.Measure.adapter.GridAdapter;
import com.example.bob.health_helper.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Bob on 2018/12/7.
 */

public class MeasureGridHolder extends RecyclerView.ViewHolder{
	@BindView(R.id.data_list)
	GridView mGridView;

	public MeasureGridHolder(@NonNull View itemView) {
		super(itemView);
		ButterKnife.bind(this, itemView);
	}

	public void bind(Context context,MeasureData bodyData){
		GridAdapter adapter = new GridAdapter(context, bodyData);
		mGridView.setAdapter(adapter);
		mGridView.setOnItemClickListener((adapterView, view, i, l) -> context.startActivity(new Intent(context, MeasureReportActivity.class).putExtra("MEASURE_DATA",bodyData)));
	}
}
