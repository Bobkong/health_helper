package com.example.bob.health_helper.Community.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import java.util.ArrayList;

public class CommunityPagerAdapter extends FragmentPagerAdapter {
    private final String[] titles;
    private final ArrayList<Fragment> fragments;

    public CommunityPagerAdapter(FragmentManager fm,String[] titles,ArrayList<Fragment> fragments) {
        super(fm);
        this.titles=titles;
        this.fragments=fragments;
    }

    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
