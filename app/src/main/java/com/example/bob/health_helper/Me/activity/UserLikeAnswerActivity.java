package com.example.bob.health_helper.Me.activity;
import android.support.v7.widget.RecyclerView;
import com.example.bob.health_helper.Bean.Answer;
import com.example.bob.health_helper.Me.adapter.UserAnswerAdapter;
import com.example.bob.health_helper.NetService.Api.AnswerService;
import com.example.bob.health_helper.R;
import java.util.ArrayList;
import java.util.List;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class UserLikeAnswerActivity extends BaseRefreshableListActivity {

    private List<Answer> answerList=new ArrayList<>();
    private int curPage=0;

    @Override
    RecyclerView.Adapter<RecyclerView.ViewHolder> createAdapter() {
        return new UserAnswerAdapter(answerList);
    }

    @Override
    void startRefresh() {
        curPage=0;
        AnswerService.getInstance().getUserLikeAnswers(uid,curPage++)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(datas->this.onRefreshSuccess(datas),
                        throwable -> this.onRefreshFailed());
    }

    @Override
    void startLoadMoreData() {
        AnswerService.getInstance().getUserLikeAnswers(uid,curPage++)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(datas->this.onLoadMoreDataSuccess(datas),
                        throwable -> this.onLoadMoreDataFailed());
    }

    public void onRefreshSuccess(List<Answer> datas){
        swipeRefreshLayout.setRefreshing(false);
        answerList=datas;
        adapter.notifyDataSetChanged();
    }

    public void onRefreshFailed(){
        swipeRefreshLayout.setRefreshing(false);
        showTips(getString(R.string.network_error));
    }

    public void onLoadMoreDataSuccess(List<Answer> datas){
        answerList.addAll(datas);
        adapter.notifyDataSetChanged();
    }

    public void onLoadMoreDataFailed(){
        showTips(getString(R.string.network_error));
    }
}
