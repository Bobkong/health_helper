package com.example.bob.health_helper.Community.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.bob.health_helper.Base.AppConstant;
import com.example.bob.health_helper.Base.BaseMvpActivity;
import com.example.bob.health_helper.Bean.Answer;
import com.example.bob.health_helper.Local.Dao.LikeDao;
import com.example.bob.health_helper.Local.LocalBean.Favorite;
import com.example.bob.health_helper.Bean.Question;
import com.example.bob.health_helper.Community.adapter.AnswerListAdapter;
import com.example.bob.health_helper.Community.contract.QuestionDetailContract;
import com.example.bob.health_helper.Community.presenter.QuestionDetailPresenter;
import com.example.bob.health_helper.Local.Dao.FavoriteDao;
import com.example.bob.health_helper.Local.LocalBean.Like;
import com.example.bob.health_helper.R;
import com.example.bob.health_helper.Util.SharedPreferenceUtil;

import java.util.ArrayList;
import java.util.List;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class QuestionDetailActivity extends BaseMvpActivity<QuestionDetailContract.Presenter>
            implements QuestionDetailContract.View{

    private static final int HOT_TYPE=0;//按回答点赞数排序
    private static final int TIME_TYPE=1;//按回答创建时间排序

    @BindView(R.id.tool_bar)
    Toolbar toolbar;
    @BindView(R.id.favorite_question)
    ImageButton favoriteButton;
    @BindView(R.id.question_title)
    TextView questionTitle;
    @BindView(R.id.question_description)
    TextView questionDescription;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.answer_list)
    RecyclerView recyclerView;

    private Question question;
    private String uid;
    private FavoriteDao favoriteDao;
    private boolean isFavorite=false;
    private int filterType=TIME_TYPE;//默认按回答创建时间排序
    private List<Answer> answerList=new ArrayList<>();
    private AnswerListAdapter answerListAdapter;
    private LikeDao likeDao;

    @Override
    protected QuestionDetailContract.Presenter bindPresenter() {
        return new QuestionDetailPresenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_detail);
        ButterKnife.bind(this);

        question=(Question)getIntent().getSerializableExtra("question");
        uid=SharedPreferenceUtil.getUser().getUid();
        favoriteDao=new FavoriteDao(this);
        likeDao=new LikeDao(this);
        answerListAdapter=new AnswerListAdapter(answerList,likeDao);

        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("");
        }

        //问题收藏
        if(favoriteDao.queryIsFavorite(uid,question.getId())==null
        ||favoriteDao.queryIsFavorite(uid,question.getId()).size()==0)
            isFavorite=false;
        else
            isFavorite=true;
        favoriteButton.setSelected(isFavorite);

        //问题标题和描述
        questionTitle.setText(question.getTitle());
        if(question.getDescription()==null || question.getDescription().length()==0){
            questionDescription.setVisibility(View.GONE);
        }else {
            questionDescription.setVisibility(View.VISIBLE);
            questionDescription.setText(question.getDescription());
        }

        //回答列表
        refreshLayout.setColorSchemeResources(R.color.colorAccent);
        refreshLayout.setOnRefreshListener(()->{
                if(filterType==TIME_TYPE)
                    mPresenter.LoadRecentAnswer(question.getId());
                else if(filterType==HOT_TYPE)
                    mPresenter.LoadHotAnswer(question.getId());
        });
        refreshLayout.setRefreshing(true);

        //点赞回答
        answerListAdapter.setOnLikeClickListener((position,likeView,likeCountView)->{
                Answer answer=answerList.get(position);
                if(likeDao.queryIsLike(uid,answer.getId())==null
                        ||likeDao.queryIsLike(uid,answer.getId()).size()==0){//点赞
                    ((ImageView)likeView).setColorFilter(getResources().getColor(R.color.colorPrimary));
                    likeCountView.setText((Integer.valueOf(likeCountView.getText().toString())+1)+"");
                    likeDao.addLike(new Like(uid,answer.getId()));
                    mPresenter.Like(uid,answer.getId());
                }else{//取消点赞
                    ((ImageView)likeView).setColorFilter(getResources().getColor(R.color.primary_light));
                    likeCountView.setText((Integer.valueOf(likeCountView.getText().toString())-1)+"");
                    likeDao.deleteLike(likeDao.queryIsLike(uid,answer.getId()).get(0));
                    mPresenter.CancelLike(uid,answer.getId());
                }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(answerListAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState==RecyclerView.SCROLL_STATE_IDLE&&shouldLoadMore()){
                    if(filterType==TIME_TYPE)
                        mPresenter.LoadMoreRecentAnswer(question.getId());
                    else if(filterType==HOT_TYPE)
                        mPresenter.LoadMoreHotAnswer(question.getId());
                }
            }
        });

        //进入时加载数据
        mPresenter.LoadRecentAnswer(question.getId());
    }

    @OnClick({R.id.favorite_question,R.id.filter,R.id.add_answer})
    void onClicked(View view){
        switch (view.getId()){
            case R.id.favorite_question:
                processFavorite();
                break;
            case R.id.filter:
                showPopupMenu(view);
                break;
            case R.id.add_answer:
                Intent intent=new Intent(QuestionDetailActivity.this,AddAnswerActivity.class);
                intent.putExtra("question_id",question.getId());
                startActivity(intent);
                break;
        }
    }

    private void processFavorite() {
        isFavorite=!isFavorite;
        favoriteButton.setSelected(isFavorite);

        if(isFavorite==true){
            favoriteDao.addFavorite(new Favorite(uid,question.getId()));
            mPresenter.Favorite(uid,question.getId());
        }else {
            favoriteDao.deleteFavorite(favoriteDao.queryIsFavorite(uid,question.getId()).get(0));
            mPresenter.CancelFavorite(uid,question.getId());
        }
    }

    private void showPopupMenu(View view){
        PopupMenu popupMenu=new PopupMenu(this,view);
        popupMenu.inflate(R.menu.answer_filter);
        popupMenu.setOnMenuItemClickListener((menuItem)->{
            switch (menuItem.getItemId()){
                case R.id.filter_time:
                    filterType=TIME_TYPE;
                    mPresenter.LoadRecentAnswer(question.getId());
                    return true;
                case R.id.filter_hot:
                    filterType=HOT_TYPE;
                    mPresenter.LoadHotAnswer(question.getId());
                    return true;
            }
            return false;
        });
        popupMenu.show();
    }

    private boolean shouldLoadMore(){
        //应该加载更多判断
        LinearLayoutManager linearLayoutManager=(LinearLayoutManager)recyclerView.getLayoutManager();
        return (linearLayoutManager.findLastVisibleItemPosition()==recyclerView.getAdapter().getItemCount()-1
                && recyclerView.getAdapter().getItemCount()>= AppConstant.DEFAULT_PAGE_SIZE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLoadAnswerSuccess(List<Answer> answers,boolean hasMore) {
        refreshLayout.setRefreshing(false);
        this.answerList=answers;
        answerListAdapter.updateDatas(answerList,hasMore);
    }

    @Override
    public void onLoadAnswerFailed(String msg) {
        refreshLayout.setRefreshing(false);
        showTips(msg);
    }

    @Override
    public void onLoadMoreAnswerSuccess(List<Answer> answers,boolean hasMore) {
        this.answerList.addAll(answers);
        answerListAdapter.updateDatas(answerList,hasMore);
    }

    @Override
    public void onLoadMoreAnswerFailed(String msg) {
        showTips(msg);
    }

    @Override
    public void onFavoriteSuccess(String result) {
        showTips(getString(R.string.favorite_success));
    }

    @Override
    public void onFavoriteFailed(String msg) {
        showTips(msg);
    }

    @Override
    public void onCancelFavoriteSuccess(String result) {
        showTips(getString(R.string.cancel_favorite_success));
    }

    @Override
    public void onCancelFavoriteFailed(String msg) {
        showTips(msg);
    }

    @Override
    public void onLikeSuccess(String result) {
        showTips(getString(R.string.like_success));
    }

    @Override
    public void onLikeFailed(String msg) {
        showTips((msg));
    }

    @Override
    public void onCancelLikeSuccess(String result) {
        showTips(getString(R.string.cancel_like_success));
    }

    @Override
    public void onCancelLikeFailed(String msg) {
        showTips(msg);
    }
}

