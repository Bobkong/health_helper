package com.example.bob.health_helper.Measure.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.bob.health_helper.Bean.MeasureData;
import com.example.bob.health_helper.Util.MeasureGridUtils;
import com.example.bob.health_helper.Measure.viewHolder.MeasureGridItemHolder;
import com.example.bob.health_helper.R;

import java.text.DecimalFormat;

/**
 * Created by Bob on 2018/11/20.
 */

public class GridAdapter extends BaseAdapter {
	private Context mContext;

	public GridAdapter(Context context, MeasureData measureData){
		mContext = context;
		MeasureGridUtils.getInstance(measureData);
	}

	@Override
	public int getCount() {
		return MeasureGridUtils.INDEX_NUM;
	}

	@Override
	public Object getItem(int i) {
		return null;
	}


	@Override
	public long getItemId(int i) {
		return i;
	}

	@Override
	public View getView(int i, View view, ViewGroup viewGroup) {
		MeasureGridItemHolder vh;
		if(view == null){
			view = LayoutInflater.from(mContext).inflate(R.layout.measure_grid_item,viewGroup,false);
			vh = new MeasureGridItemHolder(view);
			view.setTag(vh);
		}
		vh = (MeasureGridItemHolder) view.getTag();
		setView(vh,i);
		return view;
	}

	private void setView(MeasureGridItemHolder vh,int index){
		DecimalFormat df = new DecimalFormat( "0.0");

		vh.mDataName.setText(MeasureGridUtils.STANDARD.get(index).getName());
		vh.mDataUnit.setText(MeasureGridUtils.STANDARD.get(index).getUnit());
		vh.mDataValue.setText(df.format(MeasureGridUtils.STANDARD.get(index).getValue()));
		vh.mDataState.setText(MeasureGridUtils.STANDARD.get(index).getState());
		vh.mDataState.setTextColor(mContext.getResources().getColor(MeasureGridUtils.STANDARD.get(index).getColor()));
	}


}
