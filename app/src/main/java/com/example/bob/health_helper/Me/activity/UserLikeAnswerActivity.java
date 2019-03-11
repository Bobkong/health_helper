package com.example.bob.health_helper.Me.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.bob.health_helper.Bean.Answer;
import com.example.bob.health_helper.Bean.Question;
import com.example.bob.health_helper.Me.adapter.UserAnswerAdapter;
import com.example.bob.health_helper.Me.adapter.UserQuestionAdapter;
import com.example.bob.health_helper.R;

import java.util.ArrayList;
import java.util.List;

public class UserLikeAnswerActivity extends BaseRefreshableListActivity {

    private List<Answer> answerList=new ArrayList<>();
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataList.setAdapter(new UserAnswerAdapter(answerList));
        startLoadData();
    }

    public void startLoadData() {

    }

    @Override
    void startRefresh() {

    }

    @Override
    void startLoadMoreData() {

    }
}
