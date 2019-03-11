package com.example.bob.health_helper.Community.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.bob.health_helper.Bean.Answer;
import com.example.bob.health_helper.Community.activity.CommentActivity;
import com.example.bob.health_helper.MyApplication;
import com.example.bob.health_helper.R;
import com.example.bob.health_helper.Widget.ExpandableTextView;
import com.orhanobut.logger.Logger;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AnswerListAdapter extends LoadingMoreAdapter<Answer> {

    public static class AnswerItemViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.user_icon)
        ImageView userIcon;
        @BindView(R.id.user_name)
        TextView userName;
        @BindView(R.id.answer)
        ExpandableTextView answerText;
        @BindView(R.id.publish_date)
        TextView publishDate;
        @BindView(R.id.favorite)
        LinearLayout favorite;
        @BindView(R.id.favorite_count)
        TextView favoriteCount;
        @BindView(R.id.comment)
        LinearLayout comment;
        @BindView(R.id.comment_count)
        TextView commentCount;
        public AnswerItemViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public AnswerListAdapter(List<Answer> datas) {
        super(datas);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateNormalViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_answer,parent,false);
        AnswerItemViewHolder answerItemViewHolder=new AnswerItemViewHolder(view);
        //点击事件设置
        answerItemViewHolder.favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //to do logic
            }
        });
        answerItemViewHolder.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(view.getContext(), CommentActivity.class);
                //to do : answer data transform
            }
        });
        return answerItemViewHolder;
    }

    @Override
    protected void onBindNormalViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        Answer answer=datas.get(position);
        AnswerItemViewHolder answerItemViewHolder=(AnswerItemViewHolder)viewHolder;
        Glide.with(MyApplication.getContext())
                .load(answer.getAuthorIcon())
                .apply(new RequestOptions().placeholder(R.mipmap.ic_launcher_round))
                .apply(new RequestOptions().circleCrop())
                .into(answerItemViewHolder.userIcon);
        answerItemViewHolder.userName.setText(answer.getAuthorName());
        answerItemViewHolder.answerText.setContent(answer.getContent());
        answerItemViewHolder.publishDate.setText(answer.getDate());
        answerItemViewHolder.favoriteCount.setText(answer.getLikeCount());
        answerItemViewHolder.commentCount.setText(answer.getCommentCount());
        Logger.e("answer viewHolder binded");
    }
}
