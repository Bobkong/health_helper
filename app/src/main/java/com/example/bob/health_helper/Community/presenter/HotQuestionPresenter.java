package com.example.bob.health_helper.Community.presenter;
import com.example.bob.health_helper.Base.BaseMvpPresenter;
import com.example.bob.health_helper.Community.contract.HotQuestionContract;
import com.example.bob.health_helper.NetService.Api.QuestionService;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class HotQuestionPresenter extends BaseMvpPresenter<HotQuestionContract.View>
        implements HotQuestionContract.Presenter {
    private int curPage=0;
    @Override
    public void loadHotQuestion() {
        curPage=0;
        QuestionService.getInstance().getHotQuestions(curPage++)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(datas -> mView.onLoadHotQuestionSuccess(datas,!(datas.size()==0||datas==null)),
                        throwable ->mView.onLoadHotQuestionFailed());
    }

    @Override
    public void loadMoreHotQuestion() {
        QuestionService.getInstance().getHotQuestions(curPage++)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(datas -> mView.onLoadMoreHotQuestionSuccess(datas,!(datas.size()==0||datas==null)),
                        throwable ->mView.onLoadMoreHotQuestionFailed());
    }
}
