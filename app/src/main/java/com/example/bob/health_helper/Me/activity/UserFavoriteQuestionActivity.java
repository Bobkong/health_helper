package com.example.bob.health_helper.Me.activity;

import android.os.Bundle;

import com.example.bob.health_helper.Bean.Question;
import com.example.bob.health_helper.Me.adapter.UserQuestionAdapter;
import com.example.bob.health_helper.R;

import java.util.ArrayList;
import java.util.List;

public class UserFavoriteQuestionActivity extends BaseRefreshableListActivity {
    private List<Question> questionList=new ArrayList<>();
    private UserQuestionAdapter adapter=new UserQuestionAdapter(questionList);

    private int currentPage=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataList.setAdapter(adapter);

    }

    @Override
    void startRefresh() {

    }

    @Override
    void startLoadMoreData() {

    }

    public void onRefreshSuccess(){
        swipeRefreshLayout.setRefreshing(false);
        adapter.notifyDataSetChanged();
    }

    public void onRefreshFailed(){
        swipeRefreshLayout.setRefreshing(false);
        showTips(getString(R.string.network_error));
    }

    public void onLoadMoreDataSuccess(){
        adapter.notifyDataSetChanged();
    }

    public void onLoadMoreDataFailed(){
        showTips(getString(R.string.network_error));
    }
}
