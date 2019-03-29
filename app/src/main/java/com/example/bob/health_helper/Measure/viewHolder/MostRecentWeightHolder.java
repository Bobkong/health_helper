package com.example.bob.health_helper.Measure.viewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.bob.health_helper.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Bob on 2018/12/7.
 * "身体数据"视图容器
 */
public class MostRecentWeightHolder extends RecyclerView.ViewHolder{
	@BindView(R.id.weight_data)
	TextView mWeight;
	public MostRecentWeightHolder(@NonNull View itemView) {
		super(itemView);
		ButterKnife.bind(this, itemView);
	}

	public void bind(double weight){
		mWeight.setText(String.valueOf(weight) + "kg");
	}
}
