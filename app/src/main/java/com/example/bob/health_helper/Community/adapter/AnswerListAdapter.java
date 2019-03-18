package com.example.bob.health_helper.Community.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.bob.health_helper.Bean.Answer;
import com.example.bob.health_helper.Community.activity.CommentActivity;
import com.example.bob.health_helper.Local.Dao.LikeDao;
import com.example.bob.health_helper.MyApplication;
import com.example.bob.health_helper.R;
import com.example.bob.health_helper.Util.DateUtil;
import com.example.bob.health_helper.Util.SharedPreferenceUtil;
import com.example.bob.health_helper.Widget.ExpandableTextView;

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
        @BindView(R.id.like)
        ImageView like;
        @BindView(R.id.like_count)
        TextView likeCount;
        @BindView(R.id.comment)
        ImageView comment;
        @BindView(R.id.comment_count)
        TextView commentCount;
        public AnswerItemViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    private String uid;
    private LikeDao likeDao;
    private OnLikeClickListener onLikeClickListener;

    public AnswerListAdapter(List<Answer> datas,LikeDao likeDao) {
        super(datas);
        uid= SharedPreferenceUtil.getUser().getUid();
        this.likeDao=likeDao;
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
        answerItemViewHolder.publishDate.setText(DateUtil.dateTransform(answer.getDate()));
        answerItemViewHolder.likeCount.setText(answer.getLikeCount());
        answerItemViewHolder.commentCount.setText(answer.getCommentCount());

        //用户本身点赞情况设置
        if(likeDao.queryIsLike(uid,answer.getId())!=null
        &&likeDao.queryIsLike(uid,answer.getId()).size()!=0){
            answerItemViewHolder.like.setColorFilter(MyApplication.getContext().getResources().getColor(R.color.colorPrimary));
        }
        else{
            answerItemViewHolder.like.setColorFilter(MyApplication.getContext().getResources().getColor(R.color.primary_light));
        }

        //点击事件设置
        answerItemViewHolder.like.setOnClickListener((likeView)->{
            onLikeClickListener.onClick(answerItemViewHolder.getAdapterPosition(),likeView,answerItemViewHolder.likeCount);
        });

        answerItemViewHolder.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(view.getContext(), CommentActivity.class);
                intent.putExtra("answer_id",answer.getId());
                view.getContext().startActivity(intent);
            }
        });
    }

    public interface OnLikeClickListener{
        void onClick(int position,View likeView,TextView likeCountView);
    }

    public void setOnLikeClickListener(OnLikeClickListener listener){
        this.onLikeClickListener=listener;
    }

}
