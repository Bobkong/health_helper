package com.example.bob.health_helper.Community.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.bob.health_helper.Base.BaseMvpActivity;
import com.example.bob.health_helper.Bean.Comment;
import com.example.bob.health_helper.Community.adapter.CommentListAdapter;
import com.example.bob.health_helper.Community.contract.CommentContract;
import com.example.bob.health_helper.Community.presenter.CommentPresenter;
import com.example.bob.health_helper.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommentActivity extends BaseMvpActivity<CommentContract.Presenter>
            implements CommentContract.View{
    @BindView(R.id.coordinator_layout)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.comment_list)
    RecyclerView commentList;
    @BindView(R.id.send_edit)
    EditText sendEdit;
    @BindView(R.id.send_button)
    ImageButton sendButton;

    private List<Comment> comments=new ArrayList<>();
    private CommentListAdapter commentListAdapter;

    @Override
    protected CommentContract.Presenter bindPresenter() {
        return new CommentPresenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        ButterKnife.bind(this);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle(R.string.comment);
        actionBar.setDisplayHomeAsUpEnabled(true);

        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //to do----mPresenter.loadComments();
            }
        });

        commentList.setLayoutManager(new LinearLayoutManager(this));
        commentListAdapter=new CommentListAdapter(comments);
        commentList.setAdapter(commentListAdapter);
        commentList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //分页刷新
                if(newState==RecyclerView.SCROLL_STATE_IDLE && shouldLoadMore()){
                    //to do---mPresenter.loadMoreComments();
                }
            }
        });

        sendButton.setEnabled(false);
        sendEdit.addTextChangedListener(textWatcher);//当有输入时使能发送按钮
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content=sendEdit.getText().toString().trim();
                //to do-----mPresenter.sendComment();
            }
        });

        startLoadComments();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    private TextWatcher textWatcher=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
        @Override
        public void afterTextChanged(Editable editable) {
            sendButton.setEnabled(editable.length()!=0);
        }
    };

    //屏幕中最后一个条目是数据列表最后一项时-加载更多
    private boolean shouldLoadMore(){
        LinearLayoutManager linearLayoutManager=(LinearLayoutManager)commentList.getLayoutManager();
        return linearLayoutManager.findLastVisibleItemPosition()==commentList.getAdapter().getItemCount()-1;
    }

    //加载评论
    private void startLoadComments() {
        swipeRefreshLayout.setRefreshing(true);
        // to do-----mPresenter.loadComments();
    }

    @Override
    public void onLoadCommentsSuccess(List<Comment> comments) {
        swipeRefreshLayout.setRefreshing(false);
        //to do update
    }

    @Override
    public void onLoadCommentFailed() {
        swipeRefreshLayout.setRefreshing(false);
        Toast.makeText(CommentActivity.this,R.string.load_comment_error,Toast.LENGTH_SHORT);
    }

    @Override
    public void onLoadMoreCommentsSuccess() {
        // to do
    }

    @Override
    public void onLoadMoreCommentFailed() {
        Snackbar.make(coordinatorLayout,R.string.load_comment_error,Snackbar.LENGTH_SHORT);
    }

    @Override
    public void onSendCommentSuccess() {
        Snackbar.make(coordinatorLayout,R.string.send_comment_success,Snackbar.LENGTH_SHORT);
        sendEdit.getEditableText().clear();
        startLoadComments();//发布评论成功后重新加载评论
    }

    @Override
    public void onSendCommentFailed() {
        Snackbar.make(coordinatorLayout,R.string.send_comment_failed,Snackbar.LENGTH_SHORT);
    }
}
