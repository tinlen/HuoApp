package com.example.huoapp.ui.mine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.SharedElementCallback;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.example.huoapp.R;
import com.example.huoapp.base.baseActivity.BaseSwipeBackActivity;
import com.example.huoapp.ui.adapter.MainPagerAdapter;
import com.example.huoapp.ui.mine.mineGame.MineGameBookingFragment;
import com.example.huoapp.ui.mine.mineGame.MineGamePlayingFragment;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * 我的游戏
 * Created by tinle on 2017/11/8.
 */

public class MineGameActivity extends BaseSwipeBackActivity {
    @BindView(R.id.sliding_tab)
    SlidingTabLayout slidingTab;
    @BindView(R.id.vp)
    ViewPager viewPager;

    private List<Fragment> mFragments;
    private String[] titles = {"在玩的游戏", "预约的游戏"};

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_mine_game;
    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {
        mFragments = new ArrayList<>();
        mFragments.add(MineGamePlayingFragment.newInstance());
        mFragments.add(MineGameBookingFragment.newInstance());

        viewPager.setAdapter(new MainPagerAdapter(getSupportFragmentManager(),titles,mFragments));
        slidingTab.setViewPager(viewPager);

    }

}
