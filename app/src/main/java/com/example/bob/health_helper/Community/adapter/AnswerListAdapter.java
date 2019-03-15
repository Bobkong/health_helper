package com.example.bob.health_helper.Community.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.bob.health_helper.Data.Bean.Answer;
import com.example.bob.health_helper.Community.activity.CommentActivity;
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
        public AnswerItemViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    private String uid;
    private LikeDao likeDao;

    public AnswerListAdapter(List<Answer> datas) {
        super(datas);
        uid= SharedPreferenceUtil.getUser().getUid();
        likeDao=new LikeDao(MyApplication.getContext());
    }

    @Override
    protected RecyclerView.ViewHolder onCreateNormalViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_answer,parent,false);
        AnswerItemViewHolder answerItemViewHolder=new AnswerItemViewHolder(view);
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
        answerItemViewHolder.likeCount.setText(answer.getLikeCount());
        answerItemViewHolder.commentCount.setText(answer.getCommentCount());

        if(likeDao.queryIsLike(uid,answer.getId())!=null){
            answerItemViewHolder.like.setColorFilter(MyApplication.getContext().getResources().getColor(R.color.colorPrimary));
        }
        else{
            answerItemViewHolder.like.setColorFilter(MyApplication.getContext().getResources().getColor(R.color.primary_light));
        }

        //点击事件设置
        answerItemViewHolder.likeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(likeDao.queryIsLike(uid,answer.getId())==null){//点赞
                    answerItemViewHolder.like.setColorFilter(MyApplication.getContext().getResources().getColor(R.color.colorPrimary));
                    answerItemViewHolder.likeCount.setText((Integer.valueOf(answerItemViewHolder.likeCount.getText().toString())+1));
                    likeDao.addLike(new Like(uid,answer.getId()));
                    //todo server logic
                }else{//取消点赞
                    answerItemViewHolder.like.setColorFilter(MyApplication.getContext().getResources().getColor(R.color.primary_light));
                    answerItemViewHolder.likeCount.setText((Integer.valueOf(answerItemViewHolder.likeCount.getText().toString())-1));
                    likeDao.deleteLike(likeDao.queryIsLike(uid,answer.getId()).get(0));
                    //todo server logic
                }
            }
        });

        answerItemViewHolder.commentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(view.getContext(), CommentActivity.class);
                intent.putExtra("answer_id",answer.getId());
                view.getContext().startActivity(intent);
            }
        });
    }


}
