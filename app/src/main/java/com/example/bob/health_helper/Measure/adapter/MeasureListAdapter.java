package com.example.bob.health_helper.Measure.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bob.health_helper.Bean.MeasureBean;
import com.example.bob.health_helper.Bean.MeasureData;
import com.example.bob.health_helper.Measure.viewHolder.MeasureDeviceHolder;
import com.example.bob.health_helper.Measure.viewHolder.MeasureGridHolder;
import com.example.bob.health_helper.Measure.viewHolder.MeasureRecordsHolder;
import com.example.bob.health_helper.Measure.viewHolder.MostRecentWeightHolder;
import com.example.bob.health_helper.NetService.Api.MeasureService;
import com.example.bob.health_helper.R;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by Bob on 2018/12/6.
 */

public class MeasureListAdapter extends RecyclerView.Adapter {
	private static final int TYPE_DEVICE = -1;
	private static final int TYPE_WEIGHT = 0;
	private static final int TYPE_GRID = 1;
	private static final int TYPE_DATA = 2;
	private static final String TAG = "MeasureListAdapter";
	private List<MeasureBean> mWeightListDataList = new ArrayList<>();

	private Context mContext;

	public MeasureListAdapter(Context context){
		this.mContext = context;
	}

	@NonNull
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view;
		switch (viewType){
			case TYPE_DEVICE:
				view = LayoutInflater.from(mContext).inflate(R.layout.measure_device,parent,false);
				return new MeasureDeviceHolder(view);
			case TYPE_WEIGHT:
				view = LayoutInflater.from(mContext).inflate(R.layout.most_rencent_weight,parent,false);
				return new MostRecentWeightHolder(view);
			case TYPE_GRID:
				view = LayoutInflater.from(mContext).inflate(R.layout.measure_grid,parent,false);
				return new MeasureGridHolder(view);
			case TYPE_DATA:
			default:
				view = LayoutInflater.from(mContext).inflate(R.layout.measure_records_list,parent,false);
				return new MeasureRecordsHolder(view);
		}
	}

	@Override
	public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
		switch (getItemViewType(i)){
			case TYPE_DEVICE:
				//((MeasureDeviceHolder)viewHolder).bind();
				break;
			case TYPE_WEIGHT:
				if (mWeightListDataList.size() > 0){
					((MostRecentWeightHolder)viewHolder).bind(mWeightListDataList.get(0).getWeight(),mWeightListDataList.get(0).getTime());
				}
				break;
			case TYPE_GRID:
				if (mWeightListDataList.size() > 0){
					((MeasureGridHolder)viewHolder).bind(mContext,mWeightListDataList.get(0));
				}
				break;
			case TYPE_DATA:
				if (mWeightListDataList.size() > 1){
					((MeasureRecordsHolder)viewHolder).bind(mContext,mWeightListDataList.get(i - 2));
				}
				break;
		}
	}


	@Override
	public int getItemCount() {
		return mWeightListDataList.size() + 2;
	}

	@Override
	public int getItemViewType(int position) {
		if (position == 0){
			return TYPE_DEVICE;
		}else if (position == 1){
			return TYPE_WEIGHT;
		}else if (position == 2){
			return TYPE_GRID;
		}else {
			return TYPE_DATA;
		}
	}

	public void addData(MeasureBean bodyData){
		mWeightListDataList.add(0,bodyData);
		notifyItemChanged(0);
	}

	public void addDatas(List<MeasureBean> bodyDatas){
		mWeightListDataList.clear();
		mWeightListDataList.addAll(bodyDatas);
		notifyDataSetChanged();
	}
}
