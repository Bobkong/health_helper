package com.example.bob.health_helper.Community.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bob.health_helper.Data.Bean.SearchHistory;
import com.example.bob.health_helper.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchHistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    static class SearchHistoryItemViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.history_item)
        TextView text;
        @BindView(R.id.delete)
        ImageView delete;
        public SearchHistoryItemViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    private List<SearchHistory> datas;
    private OnDeleteClickListener onDeleteClickListener=null;

    public SearchHistoryAdapter(List<SearchHistory> datas){
        this.datas=datas;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_search,viewGroup,false);
        SearchHistoryItemViewHolder holder=new SearchHistoryItemViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        SearchHistoryItemViewHolder holder=(SearchHistoryItemViewHolder)viewHolder;
        holder.text.setText(datas.get(i).getText());
        if(onDeleteClickListener!=null){
            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onDeleteClickListener.onClick(holder.getAdapterPosition());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public interface OnDeleteClickListener{
        void onClick(int position);
    }

    public void setOnDeleteClickListener(OnDeleteClickListener listener){
        this.onDeleteClickListener=listener;
    }
}
