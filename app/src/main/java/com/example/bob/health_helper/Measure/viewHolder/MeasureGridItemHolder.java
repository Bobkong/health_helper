package com.example.bob.health_helper.Measure.viewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.bob.health_helper.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Bob on 2019/3/26.
 */

public class MeasureGridItemHolder extends RecyclerView.ViewHolder{
	@BindView(R.id.data_name)
	public TextView mDataName;
	@BindView(R.id.data_value)
	public TextView mDataValue;
	@BindView(R.id.data_unit)
	public TextView mDataUnit;
	@BindView(R.id.data_state)
	public TextView mDataState;

	public MeasureGridItemHolder(@NonNull View itemView) {
		super(itemView);
		ButterKnife.bind(this, itemView);
	}
}
