package com.example.bob.health_helper.Me.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bob.health_helper.Bean.Question;
import com.example.bob.health_helper.Community.activity.QuestionDetailActivity;
import com.example.bob.health_helper.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserQuestionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Question> questions;

    static class UserQuestionViewHolder extends RecyclerView.ViewHolder{
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

        public UserQuestionViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public UserQuestionAdapter(List<Question> questions){
        this.questions=questions;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_question,parent,false);
        UserQuestionViewHolder viewHolder=new UserQuestionViewHolder(view);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(parent.getContext(), QuestionDetailActivity.class);
                // data to do
                parent.getContext().startActivity(intent);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        Question question=questions.get(i);
        UserQuestionViewHolder holder=(UserQuestionViewHolder)viewHolder;
        holder.questionTitle.setText(question.getTitle());
        holder.questionDescription.setText(question.getDescription());
        holder.favoriteCount.setText(question.getFavoriteCount());
        holder.answerCount.setText(question.getAnswerCount());
        holder.publishDate.setText(question.getDate());
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }
}
