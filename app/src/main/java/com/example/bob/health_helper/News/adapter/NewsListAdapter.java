package com.example.bob.health_helper.News.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.bob.health_helper.Bean.News;
import com.example.bob.health_helper.Community.adapter.LoadingMoreAdapter;
import com.example.bob.health_helper.MyApplication;
import com.example.bob.health_helper.News.activity.NewsDetailActivity;
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
        @BindView(R.id.news_source)
        TextView newsSource;
        @BindView(R.id.publish_date)
        TextView newsDate;
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
        return holder;
    }

    @Override
    protected void onBindNormalViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        News news=datas.get(position);
        NewsItemViewHolder holder=(NewsItemViewHolder)viewHolder;
        Glide.with(MyApplication.getContext())
                .load(news.getImg())
                .apply(new RequestOptions().placeholder(R.mipmap.ic_launcher))
                .into(holder.newsImg);
        holder.newsTitle.setText(news.getTitle());
        holder.newsSource.setText(news.getSource());
        holder.newsDate.setText(news.getDate());
        //跳转
        holder.itemView.setOnClickListener((clickView)-> {
            Intent intent=new Intent(clickView.getContext(), NewsDetailActivity.class);
            intent.putExtra("news_url",news.getUrl());
            clickView.getContext().startActivity(intent);
        });
    }
}
