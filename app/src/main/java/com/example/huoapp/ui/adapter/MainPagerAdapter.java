package com.example.huoapp.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by tinle on 2017/11/3.
 */

public class MainPagerAdapter extends FragmentPagerAdapter {
    private String[] mTitles;
    private List<Fragment> mFragments;

    public MainPagerAdapter(FragmentManager fm, String[] mTitles, List<Fragment> mFragments) {
        super(fm);
        this.mFragments = mFragments;
        this.mTitles = mTitles;
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }
}
