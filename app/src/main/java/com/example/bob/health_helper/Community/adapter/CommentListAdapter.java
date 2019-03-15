package com.example.bob.health_helper.Community.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.bob.health_helper.Bean.Comment;
import com.example.bob.health_helper.MyApplication;
import com.example.bob.health_helper.R;
import com.orhanobut.logger.Logger;

import java.util.List;


import butterknife.BindView;
import butterknife.ButterKnife;

public class CommentListAdapter extends LoadingMoreAdapter<Comment>  {
    static class CommentItemViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.user_icon)
        ImageView userIcon;
        @BindView(R.id.user_name)
        TextView userName;
        @BindView(R.id.comment)
        TextView comment;
        @BindView(R.id.publish_date)
        TextView publishDate;

        public CommentItemViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public CommentListAdapter(List<Comment> datas) {
        super(datas);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateNormalViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment,parent,false);
        CommentItemViewHolder commentItemViewHolder=new CommentItemViewHolder(view);
        Logger.e("comment viewHolder created");
        return commentItemViewHolder;
    }

    @Override
    protected void onBindNormalViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        Comment comment=datas.get(position);
        CommentItemViewHolder commentItemViewHolder=(CommentItemViewHolder)viewHolder;
        Glide.with(MyApplication.getContext())
                .load(comment.getAuthorIcon())
                .apply(new RequestOptions().placeholder(R.mipmap.ic_launcher_round))
                .apply(new RequestOptions().circleCrop())
                .into(commentItemViewHolder.userIcon);
        commentItemViewHolder.userName.setText(comment.getAuthorName());
        commentItemViewHolder.comment.setText(comment.getContent());
        commentItemViewHolder.publishDate.setText(comment.getDate());
        Logger.e("comment viewHolder binded");
    }
}
