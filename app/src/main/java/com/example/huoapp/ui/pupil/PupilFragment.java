package com.example.huoapp.ui.pupil;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.SizeUtils;
import com.example.huoapp.R;
import com.example.huoapp.base.baseFragment.BaseLazyFragment;
import com.example.huoapp.ui.adapter.MainPagerAdapter;
import com.example.huoapp.util.HuoUtils;
import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by tinle on 2017/11/6.
 */

public class PupilFragment extends BaseLazyFragment {
    @BindView(R.id.ll_top)
    LinearLayout llTop;
    @BindView(R.id.st_bar)
    SegmentTabLayout stBar;
    @BindView(R.id.vp_pupil)
    ViewPager vpPupil;

    private String[] titles = {"邀请", "徒弟"};

    private List<Fragment> mFragments;

    public static PupilFragment newInstance() {

        Bundle args = new Bundle();

        PupilFragment fragment = new PupilFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected int getContentViewLayoutId() {
        return R.layout.fragment_pupil;
    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            int statusBarHeight = HuoUtils.getStatusBarHeight(mContext);
            llTop.getLayoutParams().height = statusBarHeight + SizeUtils.dp2px(50);
            llTop.setPadding(0,statusBarHeight,0,0);
        }

        stBar.setTabData(titles);

        mFragments = new ArrayList<>();
        mFragments.add(InviteFragment.newInstance());
        mFragments.add(MyPupilFragment.newInstance());

        vpPupil.setAdapter(new MainPagerAdapter(getSupportFragmentManager(),titles,mFragments));
        stBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                vpPupil.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });

        vpPupil.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                stBar.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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
