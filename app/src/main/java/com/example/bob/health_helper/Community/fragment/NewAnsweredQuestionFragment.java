package com.example.bob.health_helper.Community.fragment;

import android.widget.ImageView;
import com.example.bob.health_helper.Bean.Answer;
import com.example.bob.health_helper.Community.adapter.LoadingMoreAdapter;
import com.example.bob.health_helper.Community.adapter.NewAnsweredQuestionListAdapter;
import com.example.bob.health_helper.Community.contract.NewAnsweredQuestionContract;
import com.example.bob.health_helper.Community.presenter.NewAnsweredQuestionPresenter;
import com.example.bob.health_helper.Local.Dao.LikeDao;
import com.example.bob.health_helper.Local.LocalBean.Like;
import com.example.bob.health_helper.MyApplication;
import com.example.bob.health_helper.R;
import com.example.bob.health_helper.Util.SharedPreferenceUtil;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;


public class NewAnsweredQuestionFragment extends BaseRefreshableListFragment<NewAnsweredQuestionContract.Presenter, Answer>
        implements NewAnsweredQuestionContract.View {

    private List<Answer> newAnsweredQuestionList=new ArrayList<>();
    private LikeDao likeDao;

    @Override
    protected LoadingMoreAdapter<Answer> createAdapter() {
        likeDao=new LikeDao(getActivity());
        String uid= SharedPreferenceUtil.getUser().getUid();
        NewAnsweredQuestionListAdapter adapter=new NewAnsweredQuestionListAdapter(newAnsweredQuestionList,likeDao);
        adapter.setOnLikeClickListener((position,likeView,likeCountView)->{
            Answer answer=newAnsweredQuestionList.get(position);
            if(likeDao.queryIsLike(uid,answer.getId())==null||likeDao.queryIsLike(uid,answer.getId()).size()==0){//点赞
                ((ImageView)likeView).setColorFilter(MyApplication.getContext().getResources().getColor(R.color.colorPrimary));
                likeCountView.setText((Integer.valueOf(likeCountView.getText().toString())+1)+"");
                likeDao.addLike(new Like(uid,answer.getId()));
                mPresenter.Like(uid,answer.getId());
            }else{//取消点赞
                ((ImageView)likeView).setColorFilter(MyApplication.getContext().getResources().getColor(R.color.primary_light));
                likeCountView.setText((Integer.valueOf(likeCountView.getText().toString())-1)+"");
                likeDao.deleteLike(likeDao.queryIsLike(uid,answer.getId()).get(0));
                mPresenter.CancelLike(uid,answer.getId());
            }
        });
        return adapter;
    }

    @Override
    protected NewAnsweredQuestionContract.Presenter bindPresenter() {
        return new NewAnsweredQuestionPresenter();
    }

    @Override
    protected void startRefresh() {
        mPresenter.loadNewAnsweredQuestion();
    }

    @Override
    protected void startLoadMore() {
        mPresenter.loadMoreNewAnsweredQuestion();
    }

    @Override
    public void onLoadNewAnsweredQuestionSuccess(List<Answer> datas,boolean hasMore) {
        swipeRefreshLayout.setRefreshing(false);
        newAnsweredQuestionList=datas;
        adapter.updateDatas(newAnsweredQuestionList,hasMore);
    }

    @Override
    public void onLoadNewAnsweredQuestionFailed(String msg) {
        swipeRefreshLayout.setRefreshing(false);
        showTips(msg);
    }

    @Override
    public void onLoadMoreNewAnsweredQuestionSuccess(List<Answer> datas,boolean hasMore) {
        newAnsweredQuestionList.addAll(datas);
        adapter.updateDatas(newAnsweredQuestionList,hasMore);
    }

    @Override
    public void onLoadMoreNewAnsweredQuestionFailed(String msg) {
        showTips(msg);
    }

    @Override
    public void onLikeSuccess(String result) {
        showTips(getString(R.string.like_success));
    }

    @Override
    public void onLikeFailed(String msg) {
        showTips(msg);
    }

    @Override
    public void onCancelLikeSuccess(String result) {
        showTips(getString(R.string.cancel_like_success));
    }

    @Override
    public void onCancelLikeFailed(String msg) {
        showTips(msg);
    }
}
