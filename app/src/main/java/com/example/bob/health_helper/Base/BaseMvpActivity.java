package com.example.bob.health_helper.Base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public abstract class BaseMvpActivity<T extends BaseMvpPresenter> extends AppCompatActivity implements BaseMvpView {
    protected T mPresenter;

    protected abstract T bindPresenter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter=bindPresenter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.attachView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.dettachView();
    }

    public void navigateForResultTo(Class to, int requestCode) {
        Intent intent = new Intent(this, to);
        startActivityForResult(intent, requestCode);
    }

    public void navigateTo(Class to) {
        Intent intent = new Intent(this, to);
        startActivity(intent);
        finish();
    }
}
