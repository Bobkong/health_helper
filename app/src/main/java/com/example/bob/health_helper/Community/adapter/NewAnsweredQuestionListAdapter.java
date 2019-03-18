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

import com.example.bob.health_helper.Bean.Answer;
import com.example.bob.health_helper.Community.activity.CommentActivity;
import com.example.bob.health_helper.Local.LocalBean.Like;
import com.example.bob.health_helper.Local.Dao.LikeDao;
import com.example.bob.health_helper.MyApplication;
import com.example.bob.health_helper.R;
import com.example.bob.health_helper.Util.DateUtil;
import com.example.bob.health_helper.Util.SharedPreferenceUtil;
import com.example.bob.health_helper.Widget.ExpandableTextView;

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
        @BindView(R.id.like)
        ImageView like;
        @BindView(R.id.like_count)
        TextView likeCount;
        @BindView(R.id.comment)
        ImageView comment;
        @BindView(R.id.comment_count)
        TextView commentCount;
        public NewAnsweredQuestionViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    private String uid;
    private LikeDao likeDao;
    private OnLikeClickListener onLikeClickListener;

    public NewAnsweredQuestionListAdapter(List datas,LikeDao likeDao) {
        super(datas);
        uid= SharedPreferenceUtil.getUser().getUid();
        this.likeDao=likeDao;
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
        NewAnsweredQuestionViewHolder holder=(NewAnsweredQuestionViewHolder)viewHolder;
        holder.title.setText(answer.getQuestionTitle());
        holder.answer.setContent(answer.getAuthorName()+" : "+answer.getContent());
        holder.likeCount.setText(answer.getLikeCount());
        holder.commentCount.setText(answer.getCommentCount());
        holder.publishDate.setText(DateUtil.dateTransform(DateUtil.dateTransform(answer.getDate())));

        if(likeDao.queryIsLike(uid,answer.getId())!=null&&likeDao.queryIsLike(uid,answer.getId()).size()!=0){
            holder.like.setColorFilter(MyApplication.getContext().getResources().getColor(R.color.colorPrimary));
        }
        else{
            holder.like.setColorFilter(MyApplication.getContext().getResources().getColor(R.color.primary_light));
        }

        //点击事件设置
        holder.like.setOnClickListener((view)->
                onLikeClickListener.onClick(holder.getAdapterPosition(),view,holder.likeCount));
        holder.comment.setOnClickListener((view)->{
                Intent intent=new Intent(view.getContext(), CommentActivity.class);
                intent.putExtra("answer_id",answer.getId());
                view.getContext().startActivity(intent);
        });
    }

    public interface OnLikeClickListener{
        void onClick(int position,View likeView,TextView likeCountView);
    }

    public void setOnLikeClickListener(OnLikeClickListener listener){
        this.onLikeClickListener=listener;
    }
}
