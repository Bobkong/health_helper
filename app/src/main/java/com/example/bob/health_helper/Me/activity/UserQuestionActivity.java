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
import io.reactivex.disposables.Disposable;

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
        Disposable disposable=QuestionService.getInstance().getUserQuestions(uid,curPage++)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(datas->this.onRefreshSuccess(datas),
                        throwable -> this.onRefreshFailed(throwable.getMessage()));
        addSubscribe(disposable);
    }

    @Override
    void startLoadMoreData() {
        Disposable disposable=QuestionService.getInstance().getUserQuestions(uid,curPage++)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(datas->this.onLoadMoreDataSuccess(datas),
                        throwable -> this.onLoadMoreDataFailed(throwable.getMessage()));
        addSubscribe(disposable);
    }

    public void onRefreshSuccess(List<Question> datas){
        swipeRefreshLayout.setRefreshing(false);
        this.questionList=datas;
        adapter.notifyDataSetChanged();
    }

    public void onRefreshFailed(String msg){
        swipeRefreshLayout.setRefreshing(false);
        showTips(msg);
    }

    public void onLoadMoreDataSuccess(List<Question> datas){
        this.questionList.addAll(datas);
        adapter.notifyDataSetChanged();
    }

    public void onLoadMoreDataFailed(String msg){
        showTips(msg);
    }


}
