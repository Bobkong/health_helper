package com.example.bob.health_helper.News.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bob.health_helper.Data.Bean.News;
import com.example.bob.health_helper.Community.adapter.LoadingMoreAdapter;
import com.example.bob.health_helper.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsListAdapter extends LoadingMoreAdapter<News> {

    static class NewsItemViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.news_img)
        ImageView newsImg;
        @BindView(R.id.news_title)
        TextView newsTitle;
        public NewsItemViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public NewsListAdapter(List<News> datas) {
        super(datas);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateNormalViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news,parent,false);
        NewsItemViewHolder holder=new NewsItemViewHolder(view);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // to do
            }
        });
        return holder;
    }

    @Override
    protected void onBindNormalViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        News news=datas.get(position);
        NewsItemViewHolder holder=(NewsItemViewHolder)viewHolder;
        //to do more
    }
}
