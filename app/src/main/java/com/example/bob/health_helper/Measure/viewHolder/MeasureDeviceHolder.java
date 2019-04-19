package com.example.bob.health_helper.Measure.viewHolder;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bob.health_helper.R;
import com.lifesense.ble.LsBleManager;
import com.lifesense.ble.PairCallback;
import com.lifesense.ble.ReceiveDataCallback;
import com.lifesense.ble.SearchCallback;
import com.lifesense.ble.bean.LsDeviceInfo;
import com.lifesense.ble.bean.WeightData_A3;
import com.lifesense.ble.bean.constant.BroadcastType;
import com.lifesense.ble.bean.constant.DeviceConnectState;
import com.lifesense.ble.bean.constant.DeviceType;
import com.lifesense.ble.bean.constant.ManagerStatus;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by Bob on 2019/3/31.
 */

public class MeasureDeviceHolder extends RecyclerView.ViewHolder {
	@BindView(R.id.device_info)
	LinearLayout deviceInfo;
	@BindView(R.id.pair_device)
	TextView pairDevice;
	Context mContext;

	public MeasureDeviceHolder(Context context,@NonNull View itemView) {
		super(itemView);
		mContext = context;
		ButterKnife.bind(this,itemView);
	}

	public void setDeviceLayout(){
		new Thread(() -> {
			deviceInfo.post(() -> deviceInfo.setVisibility(View.VISIBLE));
			pairDevice.post(() -> pairDevice.setVisibility(View.GONE));
		}).start();
	}

	@OnClick(R.id.pair_device)
	public void onClicked() {
		LsBleManager.getInstance().searchLsDevice(mSearchCallback, getDeviceTypes(), BroadcastType.ALL);
	}

	private SearchCallback mSearchCallback = new SearchCallback() {
		@Override
		public void onSearchResults(LsDeviceInfo lsDevice) {
			Logger.d(lsDevice);
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
			LsBleManager.getInstance().pairingWithDevice(lsDevice, mPairCallback);
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
			Toast.makeText(mContext, R.string.open_bluetooth_hint,Toast.LENGTH_SHORT).show();
			return;
		}
		if (lsDeviceInfo == null) {
			Toast.makeText(mContext, R.string.no_device_hint,Toast.LENGTH_SHORT).show();
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
		setDeviceLayout();
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
}
