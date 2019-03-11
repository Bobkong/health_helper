package com.example.bob.health_helper.Base;

public interface BaseMvpContract {
    interface BasePresenter<T>{
        void attachView(T view);
        void detachView();
    }
    interface BaseView{

    }

}
