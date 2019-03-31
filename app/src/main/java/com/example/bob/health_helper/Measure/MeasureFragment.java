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


	/*@OnClick(R.id.pair_ls)
	public void onClicked() {
		LsBleManager.getInstance().searchLsDevice(mSearchCallback, getDeviceTypes(), BroadcastType.ALL);
	}*/

	private SearchCallback mSearchCallback = new SearchCallback() {
		@Override
		public void onSearchResults(LsDeviceInfo lsDevice) {
			ManagerStatus status = LsBleManager.getInstance().getLsBleManagerStatus();
			switch (status) {
				case DATA_RECEIVE:
					LsBleManager.getInstance().stopDataReceiveService();
					break;
				case DEVICE_SEARCH:
					LsBleManager.getInstance().stopSearch();
					break;
				case DEVICE_PAIR:
					LsBleManager.getInstance().cancelDevicePairing(lsDevice);
					break;
				case UPGRADE_FIRMWARE_VERSION:
					LsBleManager.getInstance().cancelAllUpgradeProcess();
					break;
			}
			//delay 5 seconds to binding if need or test
			Handler handler = new Handler();
			handler.postDelayed(() -> LsBleManager.getInstance().pairingWithDevice(lsDevice, mPairCallback),3 * 1000L);
		}
	};

	private List<DeviceType> getDeviceTypes(){
		//返回扫描所有的设备类型
		List<DeviceType> mScanDeviceType = new ArrayList<>();
		mScanDeviceType.add(DeviceType.SPHYGMOMANOMETER);
		mScanDeviceType.add(DeviceType.FAT_SCALE);
		mScanDeviceType.add(DeviceType.WEIGHT_SCALE);
		mScanDeviceType.add(DeviceType.HEIGHT_RULER);
		mScanDeviceType.add(DeviceType.PEDOMETER);
		mScanDeviceType.add(DeviceType.KITCHEN_SCALE);
		return mScanDeviceType;
	}

	private PairCallback mPairCallback = new PairCallback() {
		@Override
		public void onPairResults(LsDeviceInfo lsDeviceInfo, int i) {
			super.onPairResults(lsDeviceInfo, i);
			if (lsDeviceInfo != null) {
				connectDevice(lsDeviceInfo);
			} else {
				Log.d("Prompt", "Pairing failed, please try again");
			}
		}
	};

	private void connectDevice(LsDeviceInfo lsDeviceInfo){
		if (!LsBleManager.getInstance().isOpenBluetooth()) {
			Toast.makeText(getActivity(), R.string.open_bluetooth_hint,Toast.LENGTH_SHORT).show();
			return;
		}
		if (lsDeviceInfo == null) {
			Toast.makeText(getActivity(), R.string.no_device_hint,Toast.LENGTH_SHORT).show();
			return;
		}
		if (LsBleManager.getInstance().checkDeviceConnectState(lsDeviceInfo.getMacAddress()) == DeviceConnectState.CONNECTED_SUCCESS) {
			LsBleManager.getInstance().registerDataSyncCallback(mDataCallback);
			return;
		}
		if (LsBleManager.getInstance().getLsBleManagerStatus() == ManagerStatus.DATA_RECEIVE) {
			return;
		}
		LsBleManager.getInstance().stopDataReceiveService();
		//clear measure measure_device list
		LsBleManager.getInstance().setMeasureDevice(null);
		//add target measurement measure_device
		LsBleManager.getInstance().addMeasureDevice(lsDeviceInfo);
		//start data syncing service
		LsBleManager.getInstance().startDataReceiveService(mDataCallback);
	}

	private ReceiveDataCallback mDataCallback = new ReceiveDataCallback() {
		@Override
		public void onReceiveWeightData_A3(WeightData_A3 weightData_a3) {
			super.onReceiveWeightData_A3(weightData_a3);
			Logger.d("MeasureFragment",weightData_a3.toString());
			//add data

			/*MeasureService.getMeasureService().addMeasure()
					.observeOn(AndroidSchedulers.mainThread())
					.subscribe(response -> {
						if (response.getSuccess()){
							Log.d(TAG,"add measure data success");
						}else{
							Log.d(TAG,"add measure data fails");
						}
					},Throwable::printStackTrace);*/
		}
	};

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
