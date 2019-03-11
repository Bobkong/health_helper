package com.example.bob.health_helper.Community.activity;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import com.example.bob.health_helper.Base.BaseMvpActivity;
import com.example.bob.health_helper.Bean.Answer;
import com.example.bob.health_helper.Community.contract.QuestionDetailContract;
import com.example.bob.health_helper.Community.presenter.QuestionDetailPresenter;
import com.example.bob.health_helper.R;
import com.orhanobut.logger.Logger;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class QuestionDetailActivity extends BaseMvpActivity<QuestionDetailContract.Presenter>
            implements QuestionDetailContract.View{

    private static final int HOT_TYPE=0;//按回答点赞数排序
    private static final int TIME_TYPE=1;//按回答创建时间排序
    private int filterType=TIME_TYPE;//默认按回答创建时间排序

    @BindView(R.id.tool_bar)
    Toolbar toolbar;

    @Override
    protected QuestionDetailContract.Presenter bindPresenter() {
        return new QuestionDetailPresenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_detail);
        ButterKnife.bind(this);

        //actionBar设置
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("");
    }

    @OnClick({R.id.favorite_question,R.id.filter,R.id.add_answer})
    void onClicked(View view){
        switch (view.getId()){
            case R.id.favorite_question:
                //to do
                break;

            case R.id.filter:
                showPopupMenu(view);
                break;

            case R.id.add_answer:
                Intent intent=new Intent(QuestionDetailActivity.this,AddAnswerActivity.class);
                // to do
                break;
        }
    }

    void showPopupMenu(View view){
        PopupMenu popupMenu=new PopupMenu(this,view);
        popupMenu.inflate(R.menu.answer_filter);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                menuItem.setCheckable(true);
                switch (menuItem.getItemId()){
                    case R.id.filter_time:
                        filterType=TIME_TYPE;
                        //to do
                        return true;
                    case R.id.filter_hot:
                        filterType=HOT_TYPE;
                        //to do
                        return true;
                }
                return false;
            }
        });
        popupMenu.show();
    }

    void startLoadData(){

    }

    @Override
    public void onLoadRecentAnswerSuccess(List<Answer> answers) {

    }

    @Override
    public void onLoadRecentAnswerFailed() {

    }

    @Override
    public void onLoadMoreRecentAnswerSuccess(List<Answer> answers) {

    }

    @Override
    public void onLoadMoreRecentAnswerFailed() {

    }

    @Override
    public void onLoadHotAnswerSuccess(List<Answer> answers) {

    }

    @Override
    public void onLoadHotAnswerFailed() {

    }

    @Override
    public void onLoadMoreHotAnswerSuccess(List<Answer> answers) {

    }

    @Override
    public void onLoadMoreHotAnswerFailed() {

    }
}

