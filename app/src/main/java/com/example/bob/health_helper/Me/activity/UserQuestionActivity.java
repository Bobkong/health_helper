package com.example.bob.health_helper.Me.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.example.bob.health_helper.Bean.Question;
import com.example.bob.health_helper.Me.adapter.UserQuestionAdapter;
import com.example.bob.health_helper.NetService.Api.QuestionService;
import com.example.bob.health_helper.R;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class UserQuestionActivity extends BaseRefreshableListActivity {

    private List<Question> questionList=new ArrayList<>();
    private int curPage=0;

    @Override
    RecyclerView.Adapter<RecyclerView.ViewHolder> createAdapter() {
        return new UserQuestionAdapter(questionList);
    }

    @Override
    void startRefresh() {
        curPage=0;
        QuestionService.getInstance().getUserQuestions(uid,curPage++)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(datas->this.onRefreshSuccess(datas),
                        throwable -> this.onRefreshFailed());
    }

    @Override
    void startLoadMoreData() {
        QuestionService.getInstance().getUserQuestions(uid,curPage++)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(datas->this.onLoadMoreDataSuccess(datas),
                        throwable -> this.onLoadMoreDataFailed());
    }

    public void onRefreshSuccess(List<Question> datas){
        swipeRefreshLayout.setRefreshing(false);
        this.questionList=datas;
        adapter.notifyDataSetChanged();
    }

    public void onRefreshFailed(){
        swipeRefreshLayout.setRefreshing(false);
        showTips(getString(R.string.network_error));
    }

    public void onLoadMoreDataSuccess(List<Question> datas){
        this.questionList.addAll(datas);
        adapter.notifyDataSetChanged();
    }

    public void onLoadMoreDataFailed(){
        showTips(getString(R.string.network_error));
    }


}
