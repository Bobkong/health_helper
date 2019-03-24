package com.example.bob.health_helper.Me.activity;
import android.support.v7.widget.RecyclerView;
import com.example.bob.health_helper.Bean.Answer;
import com.example.bob.health_helper.Me.adapter.UserAnswerAdapter;
import com.example.bob.health_helper.NetService.Api.AnswerService;
import com.example.bob.health_helper.R;
import java.util.ArrayList;
import java.util.List;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

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
        Disposable disposable=AnswerService.getInstance().getUserLikeAnswers(uid,curPage++)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(datas->this.onRefreshSuccess(datas),
                        throwable -> this.onRefreshFailed(throwable.getMessage()));
        addSubscribe(disposable);
    }

    @Override
    void startLoadMoreData() {
        Disposable disposable=AnswerService.getInstance().getUserLikeAnswers(uid,curPage++)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(datas->this.onLoadMoreDataSuccess(datas),
                        throwable -> this.onLoadMoreDataFailed(throwable.getMessage()));
        addSubscribe(disposable);
    }

    public void onRefreshSuccess(List<Answer> datas){
        swipeRefreshLayout.setRefreshing(false);
        answerList=datas;
        adapter.notifyDataSetChanged();
    }

    public void onRefreshFailed(String msg){
        swipeRefreshLayout.setRefreshing(false);
        showTips(msg);
    }

    public void onLoadMoreDataSuccess(List<Answer> datas){
        answerList.addAll(datas);
        adapter.notifyDataSetChanged();
    }

    public void onLoadMoreDataFailed(String msg){
        showTips(msg);
    }
}
