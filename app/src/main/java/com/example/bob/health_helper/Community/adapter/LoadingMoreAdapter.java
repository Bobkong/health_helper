package com.example.bob.health_helper.Community.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bob.health_helper.Base.AppConstant;
import com.example.bob.health_helper.R;
import com.orhanobut.logger.Logger;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 上拉加载更多
 */

public abstract class LoadingMoreAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected List<T> datas;

    private static final int ITEM_NORMAL_TYPE=0;//正常条目
    private static final int ITEM_LOADING_TYPE=1;//底部加载条目
    private boolean hasMore;//是否还有数据

    //底部加载视图viewholder
    public static class LoadingViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tips)
        TextView tips;
        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public LoadingMoreAdapter(List<T> datas){
        this.datas=datas;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==ITEM_NORMAL_TYPE)
            return onCreateNormalViewHolder(parent,viewType);
        else{
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loadingview,parent,false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if(getItemViewType(position)==ITEM_NORMAL_TYPE)
            onBindNormalViewHolder(viewHolder,position);//子类根据具体情况实现
        else{
            if(hasMore==true){
                ((LoadingViewHolder)viewHolder).tips.setText(R.string.loading_more);
                Logger.e("bind load more");
            }
            else{
                ((LoadingViewHolder)viewHolder).tips.setText(R.string.no_more);
                Logger.e("bind no more");
            }

        }

    }

    @Override
    public int getItemViewType(int position) {
        //数据集小于默认值时
        if(datas.size()< AppConstant.DEFAULT_PAGE_SIZE)
            return ITEM_NORMAL_TYPE;
        //数据集大于默认值时
        if(position==getItemCount()-1)
            return ITEM_LOADING_TYPE;
        else
            return ITEM_NORMAL_TYPE;
    }

    @Override
    public int getItemCount() {
        if(datas==null || datas.size()==0)
            return 0;
        else if(datas.size()< AppConstant.DEFAULT_PAGE_SIZE)
            return datas.size();
        else
            return datas.size()+1;
    }

    /**
     * 更新数据集
     * @param newDatas
     * @param hasMore
     */
    public void updateDatas(List<T> newDatas,boolean hasMore){
        datas=newDatas;
        this.hasMore=hasMore;
        notifyDataSetChanged();
    }

    /**
     * 子类根据情况需要实现的方法
     * @return
     */
    protected abstract RecyclerView.ViewHolder onCreateNormalViewHolder(@NonNull ViewGroup parent, int viewType);
    protected abstract void onBindNormalViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position);
}
