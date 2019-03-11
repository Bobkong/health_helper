package com.example.bob.health_helper.News.presenter;

import com.example.bob.health_helper.Base.BaseMvpPresenter;
import com.example.bob.health_helper.News.contract.NewsPieceContract;

public class NewsPiecePresenter extends BaseMvpPresenter<NewsPieceContract.View>
            implements NewsPieceContract.Presenter{
    @Override
    public void loadNewsByTag(int tag) {

    }

    @Override
    public void loadMoreNewsByTag(int tag) {

    }
}
