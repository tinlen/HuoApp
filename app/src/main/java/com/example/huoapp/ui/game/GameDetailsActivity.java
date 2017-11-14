package com.example.huoapp.ui.game;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;
import com.example.huoapp.R;
import com.example.huoapp.base.baseActivity.BaseSwipeBackActivity;
import com.example.huoapp.ui.adapter.MainPagerAdapter;
import com.example.huoapp.ui.pupil.GrandsonFragment;
import com.example.huoapp.ui.pupil.SonFragment;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by tinle on 2017/11/7.
 */

public class GameDetailsActivity extends BaseSwipeBackActivity {
    @BindView(R.id.vp_content)
    ViewPager vpContent;
    @BindView(R.id.sliding_tab)
    SlidingTabLayout slidingTabLayout;
    @BindView(R.id.appbar_layout)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.tv_game_title)
    TextView tvGameTitle;


    private String[] mTitles = {"热评", "开服", "礼包"};
    private List<Fragment> fragments = new ArrayList<>();


    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_game_details;
    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {

        fragments.add(SonFragment.newInstance());
        fragments.add(GrandsonFragment.newInstance());
        fragments.add(GrandsonFragment.newInstance());

        vpContent.setOffscreenPageLimit(3);

        vpContent.setAdapter(new MainPagerAdapter(getSupportFragmentManager(),mTitles,fragments));

        slidingTabLayout.setViewPager(vpContent);

        initListener();

    }

    private void initListener() {
        tvGameTitle.setText("呗宝");
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                float percent = Float.valueOf(Math.abs(verticalOffset));

                if (percent == 1){
                    if (tvGameTitle.getVisibility() != View.GONE){
                        tvGameTitle.setVisibility(View.GONE);
                    }
                }else {
                    if (tvGameTitle.getVisibility() != View.VISIBLE){
                        tvGameTitle.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }
}
