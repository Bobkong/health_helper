package com.example.bob.health_helper.Base;

import android.os.Bundle;
import android.support.annotation.Nullable;


public abstract class BaseMvpActivity<T extends BaseMvpContract.BasePresenter> extends BaseActivity
        implements BaseMvpContract.BaseView {
    protected T mPresenter;

    protected abstract T bindPresenter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter=bindPresenter();
        mPresenter.attachView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }
}
