package com.example.bob.health_helper.Measure.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bob.health_helper.Bean.MeasureData;
import com.example.bob.health_helper.Util.MeasureGridUtils;
import com.example.bob.health_helper.Measure.viewHolder.ReportScoreHolder;
import com.example.bob.health_helper.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Bob on 2019/3/28.
 */

public class ReportListAdapter extends RecyclerView.Adapter {
	private static final int TYPE_SCORE = 0;
	private static final int TYPE_DATA = 1;
	Context mContext;
	MeasureData mMeasureData;

	public ReportListAdapter(Context context, MeasureData measureData) {
		mContext = context;
		mMeasureData = measureData;
		MeasureGridUtils.getInstance(measureData);
	}

	@NonNull
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
		View view;
		RecyclerView.ViewHolder viewHolder;
		switch (i) {
			case TYPE_SCORE:
				view = LayoutInflater.from(mContext).inflate(R.layout.measure_report_score, viewGroup, false);
				viewHolder = new ReportScoreHolder(view);
				break;
			case TYPE_DATA:
				view = LayoutInflater.from(mContext).inflate(R.layout.report_list_data, viewGroup, false);
				viewHolder = new ReportDataHolder(view);
				break;
			default:
				view = LayoutInflater.from(mContext).inflate(R.layout.measure_report_score, viewGroup, false);
				viewHolder = new ReportScoreHolder(view);
				break;
		}
		return viewHolder;
	}

	@Override
	public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
		switch (getItemViewType(i)) {
			case TYPE_SCORE:
				((ReportScoreHolder) viewHolder).bind(mMeasureData.getBodyScore());
				break;
			case TYPE_DATA:
				((ReportDataHolder) viewHolder).bind(mContext, i);
				break;
		}
	}

	@Override
	public int getItemCount() {
		return MeasureGridUtils.INDEX_NUM - 1;
	}

	@Override
	public int getItemViewType(int position) {
		if (position == 0) {
			return TYPE_SCORE;
		} else {
			return TYPE_DATA;
		}
	}

	private int opened = -1;

	class ReportDataHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
		@BindView(R.id.name)
		TextView name;
		@BindView(R.id.value)
		TextView value;
		@BindView(R.id.state)
		TextView state;
		@BindView(R.id.unit)
		TextView unit;
		@BindView(R.id.msg_ll)
		LinearLayout msgLl;
		@BindView(R.id.msg_more)
		TextView msgMore;
		@BindView(R.id.arrow)
		ImageView arrow;

		public ReportDataHolder(@NonNull View itemView) {
			super(itemView);
			itemView.setOnClickListener(this);
			ButterKnife.bind(this, itemView);
		}
		Animation mAnimation;
		public void bind(Context context, int index) {
			name.setText(MeasureGridUtils.STANDARD.get(index - 1).getName());
			value.setText(String.valueOf(MeasureGridUtils.STANDARD.get(index - 1).getValue()));
			state.setText(MeasureGridUtils.STANDARD.get(index - 1).getState());
			unit.setText(MeasureGridUtils.STANDARD.get(index - 1).getUnit());
			state.setTextColor(context.getResources().getColor(MeasureGridUtils.STANDARD.get(index - 1).getColor()));
			msgMore.setText((MeasureGridUtils.STANDARD.get(index - 1).getSuggestion()));
			if (opened == index) {
				msgLl.setVisibility(View.VISIBLE);
				/*mAnimation = AnimationUtils.loadAnimation(mContext, R.anim.arrow_anim_open);
				arrow.startAnimation(mAnimation);*/
			} else {
				msgLl.setVisibility(View.GONE);/*
				mAnimation = AnimationUtils.loadAnimation(mContext, R.anim.arrow_anim_close);
				arrow.startAnimation(mAnimation);*/
			}
		}

		@Override
		public void onClick(View view) {
			if (opened == getAdapterPosition()) {
				//当点击的item已经被展开了, 就关闭.
				opened = -1;
				notifyItemChanged(getAdapterPosition());
			} else {
				int oldOpened = opened;
				opened = getAdapterPosition();
				notifyItemChanged(oldOpened);
				notifyItemChanged(opened);
			}
		}
	}
}
