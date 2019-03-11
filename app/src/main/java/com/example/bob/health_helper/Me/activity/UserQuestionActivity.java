package com.example.bob.health_helper.Me.activity;

import android.os.Bundle;
import com.example.bob.health_helper.Bean.Question;
import com.example.bob.health_helper.Me.adapter.UserQuestionAdapter;
import java.util.ArrayList;
import java.util.List;

public class UserQuestionActivity extends BaseRefreshableListActivity {

    private List<Question> questionList=new ArrayList<>();
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataList.setAdapter(new UserQuestionAdapter(questionList));
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
