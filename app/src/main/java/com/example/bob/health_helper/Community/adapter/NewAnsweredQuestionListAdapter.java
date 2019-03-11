package com.example.bob.health_helper.Community.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bob.health_helper.Bean.Answer;
import com.example.bob.health_helper.Community.activity.QuestionDetailActivity;
import com.example.bob.health_helper.R;
import com.orhanobut.logger.Logger;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewAnsweredQuestionListAdapter extends LoadingMoreAdapter<Answer> {

    public static class NewAnsweredQuestionViewHolder extends RecyclerView.ViewHolder{
        View newAnsweredQuestionItemView;//子项布局
        @BindView(R.id.question_title)
        TextView title;
        @BindView(R.id.answer)
        TextView answer;
        @BindView(R.id.favorite_count)
        TextView favoriteCount;
        @BindView(R.id.comment_count)
        TextView commentCount;
        @BindView(R.id.publish_date)
        TextView publishDate;
        public NewAnsweredQuestionViewHolder(@NonNull View itemView) {
            super(itemView);
            newAnsweredQuestionItemView=itemView;
            ButterKnife.bind(this,itemView);
        }
    }

    public NewAnsweredQuestionListAdapter(List datas) {
        super(datas);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateNormalViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_new_answered_question,parent,false);
        NewAnsweredQuestionViewHolder itemViewHolder=new NewAnsweredQuestionViewHolder(view);
        //点击跳转事件设置
        itemViewHolder.newAnsweredQuestionItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(parent.getContext(), QuestionDetailActivity.class);
                int postion=itemViewHolder.getAdapterPosition();
                intent.putExtra("answer",datas.get(postion));
                parent.getContext().startActivity(intent);
                Logger.e("new answered questionList to questionDeatil");
            }
        });
        return itemViewHolder;
    }

    @Override
    protected void onBindNormalViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        Answer answer=datas.get(position);
        NewAnsweredQuestionViewHolder newAnsweredQuestionViewHolder=(NewAnsweredQuestionViewHolder)viewHolder;
        newAnsweredQuestionViewHolder.title.setText(answer.getQuestionTitle());
        newAnsweredQuestionViewHolder.answer.setText(answer.getContent());
        newAnsweredQuestionViewHolder.favoriteCount.setText(answer.getLikeCount());
        newAnsweredQuestionViewHolder.commentCount.setText(answer.getCommentCount());
        newAnsweredQuestionViewHolder.publishDate.setText(answer.getDate());
        Logger.e("new answered item binded");
    }
}
