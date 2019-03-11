package com.example.bob.health_helper.Base;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class BaseMvpPresenter<T extends BaseMvpContract.BaseView> implements BaseMvpContract.BasePresenter<T>{
    protected T mView;
    protected CompositeDisposable compositeDisposable;

    //与view关联
    @Override
    public void attachView(T view){
        this.mView=view;
    }

    //与view解绑
    @Override
    public void detachView(){
        this.mView=null;
        if(compositeDisposable!=null)
            compositeDisposable.dispose();
    }

    //解除订阅
    public void addSubscribe(Disposable disposable){
        if(compositeDisposable==null)
            compositeDisposable=new CompositeDisposable();
        compositeDisposable.add(disposable);
    }

}

