package com.example.bob.health_helper.Measure.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bob.health_helper.Bean.MeasureBean;
import com.example.bob.health_helper.Bean.MeasureData;
import com.example.bob.health_helper.Measure.adapter.ReportListAdapter;
import com.example.bob.health_helper.R;
import com.example.bob.health_helper.Util.DateUtil;
import com.example.bob.health_helper.Widget.MyDecoration;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Bob on 2019/3/28.
 */

public class MeasureReportActivity extends Activity {
	@BindView(R.id.report_list)
	RecyclerView reportList;
	@BindView(R.id.time)
	TextView time;
	ReportListAdapter adapter;
	MeasureBean measureData;
	LinearLayoutManager layoutManager;
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_measure_report);
		ButterKnife.bind(this);
		measureData = (MeasureBean) getIntent().getSerializableExtra("MEASURE_DATA");
		adapter = new ReportListAdapter(this,measureData);
		reportList.setAdapter(adapter);
		layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
		reportList.setLayoutManager(layoutManager);
		reportList.addItemDecoration(new MyDecoration(this.getApplicationContext(), MyDecoration.VERTICAL_LIST));
		time.setText(DateUtil.dateTransform(measureData.getTime()));

	}
}
