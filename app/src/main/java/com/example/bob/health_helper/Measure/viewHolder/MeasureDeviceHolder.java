package com.example.bob.health_helper.Measure.viewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bob.health_helper.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Bob on 2019/3/31.
 */

public class MeasureDeviceHolder extends RecyclerView.ViewHolder {
	@BindView(R.id.device_info)
	LinearLayout deviceInfo;
	@BindView(R.id.pair_device)
	TextView pairDevice;

	public MeasureDeviceHolder(@NonNull View itemView) {
		super(itemView);
		ButterKnife.bind(this,itemView);
	}
}
