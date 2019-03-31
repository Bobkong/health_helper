package com.example.bob.health_helper.Measure.viewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.bob.health_helper.R;
import com.example.bob.health_helper.Widget.NoPaddingTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Bob on 2019/3/28.
 */

public class ReportScoreHolder extends RecyclerView.ViewHolder {
	@BindView(R.id.score)
	TextView scoreTv;
	public ReportScoreHolder(@NonNull View itemView) {
		super(itemView);
		ButterKnife.bind(this, itemView);
	}

	public void bind(double score){
		scoreTv.setText(String.valueOf((int)score));
	}
}
