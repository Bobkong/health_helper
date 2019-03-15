package com.example.bob.health_helper.Community.adapter;

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
import com.example.bob.health_helper.Data.Bean.Question;
import com.example.bob.health_helper.Community.activity.QuestionDetailActivity;
import com.example.bob.health_helper.MyApplication;
import com.example.bob.health_helper.R;
import com.orhanobut.logger.Logger;

import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class QuestionListAdapter extends LoadingMoreAdapter<Question> {

    public static class QuestionItemViewHolder extends RecyclerView.ViewHolder{
        View questionItemView;//子项布局
        @BindView(R.id.user_icon)
        ImageView userIcon;
        @BindView(R.id.user_name)
        TextView userName;
        @BindView(R.id.question_title)
        TextView questionTitle;
        @BindView(R.id.question_description)
        TextView questionDescription;
        @BindView(R.id.favorite_count)
        TextView favoriteCount;
        @BindView(R.id.answer_count)
        TextView answerCount;
        @BindView(R.id.publish_date)
        TextView publishDate;
        public QuestionItemViewHolder(@NonNull View itemView) {
            super(itemView);
            questionItemView=itemView;
            ButterKnife.bind(this,itemView);
        }
    }

    public QuestionListAdapter(List<Question> datas) {
        super(datas);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateNormalViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_question,parent,false);
        QuestionItemViewHolder questionItemViewHolder=new QuestionItemViewHolder(view);
        return questionItemViewHolder;
    }

    @Override
    protected void onBindNormalViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        Question question=datas.get(position);
        QuestionItemViewHolder holder=(QuestionItemViewHolder)viewHolder;
        Glide.with(MyApplication.getContext())
                .load(question.getAuthorIcon())
                .apply(new RequestOptions().placeholder(R.mipmap.ic_launcher_round))
                .apply(new RequestOptions().circleCrop())
                .into(holder.userIcon);
        holder.userName.setText(question.getAuthorName());
        holder.questionTitle.setText(question.getTitle());
        //问题描述为空时隐藏描述布局
        if(question.getDescription()==null || question.getDescription().length()==0){
            holder.questionDescription.setVisibility(View.GONE);
        }else {
            holder.questionDescription.setVisibility(View.VISIBLE);
            holder.questionDescription.setText("问题描述："+question.getDescription());
        }
        holder.favoriteCount.setText(question.getFavoriteCount());
        holder.answerCount.setText(question.getAnswerCount());
        holder.publishDate.setText(question.getDate());

        //点击跳转事件设置
        holder.questionItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(view.getContext(), QuestionDetailActivity.class);
                intent.putExtra("question",question);
                view.getContext().startActivity(intent);
            }
        });
    }
}
