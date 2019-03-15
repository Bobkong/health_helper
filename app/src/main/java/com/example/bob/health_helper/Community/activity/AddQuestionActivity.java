package com.example.bob.health_helper.Community.activity;

import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ScrollView;

import com.example.bob.health_helper.Base.BaseMvpActivity;
import com.example.bob.health_helper.Community.contract.AddQuestionContract;
import com.example.bob.health_helper.Community.presenter.AddQuestionPresenter;
import com.example.bob.health_helper.R;
import com.example.bob.health_helper.Util.SharedPreferenceUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddQuestionActivity extends BaseMvpActivity<AddQuestionContract.Presenter>
        implements AddQuestionContract.View {
    @BindView(R.id.question_title)
    EditText questionTitle;
    @BindView(R.id.question_description)
    EditText questionDescription;
    @BindView(R.id.scroll_view)
    ScrollView scrollView;

    @Override
    protected AddQuestionContract.Presenter bindPresenter() {
        return new AddQuestionPresenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);
        ButterKnife.bind(this);

        //actionBar设置
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setTitle(R.string.add_question);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
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
                String title=questionTitle.getText().toString().trim();
                if(title.length()==0)
                    Snackbar.make(scrollView,R.string.question_title_empty,Snackbar.LENGTH_SHORT).show();
                else {
                    String description=questionDescription.getText().toString().trim();
                    mPresenter.publishQuestion(title,description, SharedPreferenceUtil.getUser().getUid());
                }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPublishQuestionSuccess() {
        Snackbar.make(scrollView,R.string.publish_success,Snackbar.LENGTH_SHORT)
                .addCallback(new Snackbar.Callback(){
                    @Override
                    public void onDismissed(Snackbar transientBottomBar, int event) {
                        finish();
                    }
                }).show();
    }

    @Override
    public void onPublishQuestionFailed() {
        Snackbar.make(scrollView,R.string.pubish_failed,Snackbar.LENGTH_SHORT);
    }
}
