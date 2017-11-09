package com.example.huoapp.ui.pupil;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.example.huoapp.R;
import com.example.huoapp.base.baseFragment.BaseLazyFragment;
import com.example.huoapp.ui.adapter.MainPagerAdapter;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by tinle on 2017/11/7.
 */

public class MyPupilFragment extends BaseLazyFragment {
    @BindView(R.id.sliding_tab)
    SlidingTabLayout slidingTab;
    @BindView(R.id.vp)
    ViewPager viewPager;

    private List<Fragment> mFragments;
    private String[] titles = {"徒弟", "徒孙"};

    public static MyPupilFragment newInstance() {
        
        Bundle args = new Bundle();
        
        MyPupilFragment fragment = new MyPupilFragment();
        fragment.setArguments(args);
        return fragment;
    }
    
    @Override
    protected int getContentViewLayoutId() {
        return R.layout.fragment_my_pupil;
    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {
        mFragments = new ArrayList<>();
        mFragments.add(SonFragment.newInstance());
        mFragments.add(GrandsonFragment.newInstance());
        viewPager.setAdapter(new MainPagerAdapter(getChildFragmentManager(),titles,mFragments));
        slidingTab.setViewPager(viewPager);
    }

    @Override
    protected void onFirstUserVisible() {

    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }
}
