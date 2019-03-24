package com.example.bob.health_helper.Community.presenter;
import com.example.bob.health_helper.Base.BaseMvpPresenter;
import com.example.bob.health_helper.Community.contract.HotQuestionContract;
import com.example.bob.health_helper.NetService.Api.QuestionService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class HotQuestionPresenter extends BaseMvpPresenter<HotQuestionContract.View>
        implements HotQuestionContract.Presenter {
    private int curPage=0;
    private boolean hasMore=false;
    @Override
    public void loadHotQuestion() {
        curPage=0;
        Disposable disposable=QuestionService.getInstance().getHotQuestions(curPage++)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(datas -> {
                    hasMore=!(datas.size()<10);
                    mView.onLoadHotQuestionSuccess(datas,hasMore);
                        }, throwable ->mView.onLoadHotQuestionFailed(throwable.getMessage()));
        addSubscribe(disposable);
    }

    @Override
    public void loadMoreHotQuestion() {
        if(hasMore){
            Disposable disposable=QuestionService.getInstance().getHotQuestions(curPage++)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(datas -> {
                        hasMore=!(datas.size()<10);
                        mView.onLoadMoreHotQuestionSuccess(datas,hasMore);
                        }, throwable ->mView.onLoadMoreHotQuestionFailed(throwable.getMessage()));
            addSubscribe(disposable);
        }
    }
}
