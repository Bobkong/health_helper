package com.example.bob.health_helper.Base;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class BaseMvpPresenter<T extends BaseMvpView> {
    protected T mView;
    protected CompositeDisposable compositeDisposable;

    //与view关联
    public void attachView(T view){
        this.mView=view;
    }

    //与view解绑
    public void dettachView(){
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

