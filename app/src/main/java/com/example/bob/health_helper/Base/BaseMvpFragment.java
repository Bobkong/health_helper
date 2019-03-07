package com.example.bob.health_helper.Base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;


public abstract class BaseMvpFragment<T extends BaseMvpPresenter> extends Fragment implements BaseMvpView {
    protected T mPresenter;

    protected abstract T bindPresenter();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter=bindPresenter();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.attachView(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.dettachView();
    }

    public void navigateTo(Class to) {
        Intent intent = new Intent(getActivity(), to);
        startActivity(intent);
    }
}
