package com.example.bob.health_helper.Me.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.bob.health_helper.Bean.Answer;
import com.example.bob.health_helper.R;
import com.example.bob.health_helper.Widget.ExpandableTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserAnswerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Answer> answers;

    static class UserAnswerViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.question_title)
        TextView questionTitle;
        @BindView(R.id.answer)
        ExpandableTextView answer;
        @BindView(R.id.like_count)
        TextView likeCount;
        @BindView(R.id.comment_count)
        TextView commentCount;
        @BindView(R.id.publish_date)
        TextView publishDate;
        public UserAnswerViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public UserAnswerAdapter(List<Answer> answers){
        this.answers=answers;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_answer,parent,false);
        UserAnswerViewHolder viewHolder=new UserAnswerViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        Answer answer=answers.get(i);
        UserAnswerViewHolder holder=(UserAnswerViewHolder)viewHolder;
        holder.questionTitle.setText(answer.getQuestionTitle());
        holder.answer.setContent(answer.getContent());
        holder.likeCount.setText(answer.getLikeCount());
        holder.commentCount.setText(answer.getCommentCount());
        holder.publishDate.setText(answer.getDate());
    }

    @Override
    public int getItemCount() {
        return answers.size();
    }
}
