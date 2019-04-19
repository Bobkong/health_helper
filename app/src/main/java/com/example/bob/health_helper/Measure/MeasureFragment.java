package com.example.bob.health_helper.Measure;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bob.health_helper.Bean.MeasureBean;
import com.example.bob.health_helper.Bean.MeasureData;
import com.example.bob.health_helper.Measure.adapter.MeasureListAdapter;
import com.example.bob.health_helper.Measure.viewHolder.MeasureDeviceHolder;
import com.example.bob.health_helper.NetService.Api.MeasureService;
import com.example.bob.health_helper.R;
import com.example.bob.health_helper.Util.SharedPreferenceUtil;
import com.lifesense.ble.LsBleManager;
import com.lifesense.ble.PairCallback;
import com.lifesense.ble.ReceiveDataCallback;
import com.lifesense.ble.SearchCallback;
import com.lifesense.ble.bean.LsDeviceInfo;
import com.lifesense.ble.bean.WeightAppendData;
import com.lifesense.ble.bean.WeightData_A3;
import com.lifesense.ble.bean.constant.BroadcastType;
import com.lifesense.ble.bean.constant.DeviceConnectState;
import com.lifesense.ble.bean.constant.DeviceType;
import com.lifesense.ble.bean.constant.ManagerStatus;
import com.lifesense.ble.bean.constant.SexType;
import com.orhanobut.logger.Logger;
import com.tencent.qcloud.uikit.common.component.picture.internal.entity.IncapableCause;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by Bob on 2019/3/1.
 */

public class MeasureFragment extends Fragment {
	private static final String TAG = "MeasureFragment";
	@BindView(R.id.measure_records)
	RecyclerView measureRecords;
	@BindView(R.id.swipe_refresh_layout)
	SwipeRefreshLayout refreshLayout;
	MeasureListAdapter measureAdapter;
	LinearLayoutManager layoutManager;
	List<MeasureBean> mData = new ArrayList<>();
	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.fragment_measure, container, false);
		ButterKnife.bind(this, root);
		measureAdapter = new MeasureListAdapter(getActivity());
		measureRecords.setAdapter(measureAdapter);
		layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
		measureRecords.setLayoutManager(layoutManager);

		initPullRefresh();

		return root;
	}



	private void initPullRefresh() {
		refreshLayout.setColorSchemeResources(R.color.colorAccent);
		refreshLayout.setOnRefreshListener(this::getMeasure);
		refreshLayout.setRefreshing(true);
		getMeasure();
	}

	public void getMeasure(){
		MeasureService.getMeasureService().getMeasure(SharedPreferenceUtil.getUser().getUid())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(response -> {
					if (response.getSuccess()){
						measureAdapter.addDatas(response.getData());
					}else{
						Log.d(TAG,"error: " + response.getErr().toString());
					}
				},Throwable::printStackTrace);
		refreshLayout.setRefreshing(false);
	}

}
