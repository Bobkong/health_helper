package com.example.bob.health_helper.Community.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bob.health_helper.Community.activity.CommentActivity;
import com.example.bob.health_helper.Data.Bean.Answer;
import com.example.bob.health_helper.Data.Bean.Like;
import com.example.bob.health_helper.Data.Dao.LikeDao;
import com.example.bob.health_helper.MyApplication;
import com.example.bob.health_helper.R;
import com.example.bob.health_helper.Util.SharedPreferenceUtil;
import com.example.bob.health_helper.Widget.ExpandableTextView;
import com.orhanobut.logger.Logger;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewAnsweredQuestionListAdapter extends LoadingMoreAdapter<Answer> {

    public static class NewAnsweredQuestionViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.question_title)
        TextView title;
        @BindView(R.id.answer)
        ExpandableTextView answer;
        @BindView(R.id.publish_date)
        TextView publishDate;
        @BindView(R.id.like_layout)
        LinearLayout likeLayout;
        @BindView(R.id.like)
        ImageButton like;
        @BindView(R.id.like_count)
        TextView likeCount;
        @BindView(R.id.comment_layout)
        LinearLayout commentLayout;
        @BindView(R.id.comment)
        ImageButton comment;
        @BindView(R.id.comment_count)
        TextView commentCount;
        public NewAnsweredQuestionViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    private String uid;
    private LikeDao likeDao;

    public NewAnsweredQuestionListAdapter(List datas) {
        super(datas);
        uid= SharedPreferenceUtil.getUser().getUid();
        likeDao=new LikeDao(MyApplication.getContext());
    }

    @Override
    protected RecyclerView.ViewHolder onCreateNormalViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_new_answered_question,parent,false);
        NewAnsweredQuestionViewHolder itemViewHolder=new NewAnsweredQuestionViewHolder(view);
        return itemViewHolder;
    }

    @Override
    protected void onBindNormalViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        Answer answer=datas.get(position);
        NewAnsweredQuestionViewHolder newAnsweredQuestionViewHolder=(NewAnsweredQuestionViewHolder)viewHolder;
        newAnsweredQuestionViewHolder.title.setText(answer.getQuestionTitle());
        newAnsweredQuestionViewHolder.answer.setContent(answer.getAuthorName()+" : "+answer.getContent());
        newAnsweredQuestionViewHolder.likeCount.setText(answer.getLikeCount());
        newAnsweredQuestionViewHolder.commentCount.setText(answer.getCommentCount());
        newAnsweredQuestionViewHolder.publishDate.setText(answer.getDate());

        if(likeDao.queryIsLike(uid,answer.getId())!=null){
            newAnsweredQuestionViewHolder.like.setColorFilter(MyApplication.getContext().getResources().getColor(R.color.colorPrimary));
        }
        else{
            newAnsweredQuestionViewHolder.like.setColorFilter(MyApplication.getContext().getResources().getColor(R.color.primary_light));
        }

        //点击事件设置
        newAnsweredQuestionViewHolder.likeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(likeDao.queryIsLike(uid,answer.getId())==null){//点赞
                    newAnsweredQuestionViewHolder.like.setColorFilter(MyApplication.getContext().getResources().getColor(R.color.colorPrimary));
                    newAnsweredQuestionViewHolder.likeCount.setText((Integer.valueOf(newAnsweredQuestionViewHolder.likeCount.getText().toString())+1));
                    likeDao.addLike(new Like(uid,answer.getId()));
                    //todo server logic
                }else{//取消点赞
                    newAnsweredQuestionViewHolder.like.setColorFilter(MyApplication.getContext().getResources().getColor(R.color.primary_light));
                    newAnsweredQuestionViewHolder.likeCount.setText((Integer.valueOf(newAnsweredQuestionViewHolder.likeCount.getText().toString())-1));
                    likeDao.deleteLike(likeDao.queryIsLike(uid,answer.getId()).get(0));
                    //todo server logic
                }
            }
        });

        newAnsweredQuestionViewHolder.commentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(view.getContext(), CommentActivity.class);
                intent.putExtra("answer_id",answer.getId());
                view.getContext().startActivity(intent);
            }
        });
    }
}
