package com.example.bob.health_helper.Base;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.orhanobut.logger.Logger;


public abstract class BaseMvpFragment<T extends BaseMvpContract.BasePresenter> extends BaseFragment
        implements BaseMvpContract.BaseView {
    protected T mPresenter;

    protected abstract T bindPresenter();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter=bindPresenter();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.attachView(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }
}
