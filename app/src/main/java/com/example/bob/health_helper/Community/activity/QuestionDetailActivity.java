package com.example.bob.health_helper.Community.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.bob.health_helper.Base.AppConstant;
import com.example.bob.health_helper.Base.BaseMvpActivity;
import com.example.bob.health_helper.Data.Bean.Answer;
import com.example.bob.health_helper.Data.Bean.Favorite;
import com.example.bob.health_helper.Data.Bean.Question;
import com.example.bob.health_helper.Community.adapter.AnswerListAdapter;
import com.example.bob.health_helper.Community.contract.QuestionDetailContract;
import com.example.bob.health_helper.Community.presenter.QuestionDetailPresenter;
import com.example.bob.health_helper.Data.Dao.FavoriteDao;
import com.example.bob.health_helper.R;
import com.example.bob.health_helper.Util.SharedPreferenceUtil;
import com.orhanobut.logger.Logger;

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
    private AnswerListAdapter answerListAdapter=new AnswerListAdapter(answerList);

    @Override
    protected QuestionDetailContract.Presenter bindPresenter() {
        return new QuestionDetailPresenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_detail);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("");
        }

        //问题收藏
        uid=SharedPreferenceUtil.getUser().getUid();
        favoriteDao=new FavoriteDao(this);
        if(favoriteDao.queryIsFavorite(uid,question.getId())==null)
            isFavorite=false;
        else
            isFavorite=true;
        favoriteButton.setSelected(isFavorite);

        //问题标题和描述
        question=(Question)getIntent().getSerializableExtra("question");
        questionTitle.setText(question.getTitle());
        if(question.getDescription()==null || question.getDescription().length()==0){
            questionDescription.setVisibility(View.GONE);
        }else {
            questionDescription.setVisibility(View.VISIBLE);
            questionDescription.setText(question.getDescription());
        }

        //回答列表
        refreshLayout.setColorSchemeResources(R.color.colorPrimary);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(filterType==TIME_TYPE)
                    mPresenter.LoadRecentAnswer();
                else if(filterType==HOT_TYPE)
                    mPresenter.LoadHotAnswer();
            }
        });
        refreshLayout.setRefreshing(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(answerListAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState==RecyclerView.SCROLL_STATE_IDLE&&shouldLoadMore()){
                    if(filterType==TIME_TYPE)
                        mPresenter.LoadMoreRecentAnswer();
                    else if(filterType==HOT_TYPE)
                        mPresenter.LoadMoreHotAnswer();
                }
            }
        });

        //进入时加载数据
        mPresenter.LoadRecentAnswer();
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
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.filter_time:
                        filterType=TIME_TYPE;
                        mPresenter.LoadRecentAnswer();
                        return true;
                    case R.id.filter_hot:
                        filterType=HOT_TYPE;
                        mPresenter.LoadHotAnswer();
                        return true;
                }
                return false;
            }
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
    public void onLoadAnswerFailed() {
        refreshLayout.setRefreshing(false);
        showTips(getString(R.string.network_error));
    }

    @Override
    public void onLoadMoreAnswerSuccess(List<Answer> answers,boolean hasMore) {
        this.answerList.addAll(answers);
        answerListAdapter.updateDatas(answerList,hasMore);
    }

    @Override
    public void onLoadMoreAnswerFailed() {
        showTips(getString(R.string.network_error));
    }

    @Override
    public void onFavoriteSuccess() {
        showTips(getString(R.string.favorite_success));
    }

    @Override
    public void onFavoriteFailed() {
        showTips(getString(R.string.favorite_failed));
    }

    @Override
    public void onCancelFavoriteSuccess() {
        showTips(getString(R.string.cancel_favorite_success));
    }

    @Override
    public void onCancelFavoriteFailed() {
        showTips(getString(R.string.cancel_favorite_failed));
    }
}

