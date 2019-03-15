package com.example.bob.health_helper.News.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.bob.health_helper.Data.Bean.News;
import com.example.bob.health_helper.News.fragment.NewsPieceFragment;

public class NewsPagerAdapter extends FragmentPagerAdapter {

    private final String[] titles;

    public NewsPagerAdapter(FragmentManager fm,String[] titles) {
        super(fm);
        this.titles=titles;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case News.TAG_FIND:
                return NewsPieceFragment.newInstance(News.TAG_FIND);
            case News.TAG_HYPERTENSION:
                return NewsPieceFragment.newInstance(News.TAG_HYPERTENSION);
            case News.TAG_DIABETES:
                return NewsPieceFragment.newInstance(News.TAG_DIABETES);
            case News.TAG_HYPERLIPIDEMIA:
                return NewsPieceFragment.newInstance(News.TAG_HYPERLIPIDEMIA);
            case News.TAG_STROKE:
                return NewsPieceFragment.newInstance(News.TAG_STROKE);
            case News.TAG_CORONARY_HEART_DISEASE:
                return NewsPieceFragment.newInstance(News.TAG_CORONARY_HEART_DISEASE);
        }
        return null;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
