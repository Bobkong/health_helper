package com.example.bob.health_helper.Community.activity;

import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ScrollView;

import com.example.bob.health_helper.Base.BaseMvpActivity;
import com.example.bob.health_helper.Community.contract.AddAnswerContract;
import com.example.bob.health_helper.Community.presenter.AddAnswerPresenter;
import com.example.bob.health_helper.R;
import com.example.bob.health_helper.Util.SharedPreferenceUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddAnswerActivity extends BaseMvpActivity<AddAnswerContract.Presenter>
            implements AddAnswerContract.View{

    @BindView(R.id.answer)
    EditText answerInput;
    @BindView(R.id.scroll_view)
    ScrollView scrollView;

    private int questionId;

    @Override
    protected AddAnswerContract.Presenter bindPresenter() {
        return new AddAnswerPresenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_answer);
        ButterKnife.bind(this);

        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);//显示返回键
            actionBar.setTitle(R.string.answer);
        }

        questionId=getIntent().getIntExtra("question_id",0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.publish,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            case R.id.publish:
                String answer=answerInput.getText().toString().trim();
                if(answer.length()==0)
                    Snackbar.make(scrollView,R.string.answer_empty,Snackbar.LENGTH_SHORT).show();
                else{
                    mPresenter.publishAnswer(answer,questionId, SharedPreferenceUtil.getUser().getUid());
                }

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPublishAnswerSuccess() {
        Snackbar.make(scrollView,R.string.publish_success,Snackbar.LENGTH_SHORT)
                .addCallback(new Snackbar.Callback(){
                    @Override
                    public void onDismissed(Snackbar transientBottomBar, int event) {
                        finish();
                    }
                }).show();
    }

    @Override
    public void onPublishAnswerFailed() {
        Snackbar.make(scrollView,R.string.pubish_failed,Snackbar.LENGTH_SHORT).show();
    }
}
