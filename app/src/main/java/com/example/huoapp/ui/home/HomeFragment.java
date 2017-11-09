package com.example.huoapp.ui.home;

import android.os.Bundle;

import com.example.huoapp.R;
import com.example.huoapp.base.baseFragment.BaseLazyFragment;
import com.example.huoapp.ui.SwipeDemoActivity;
import com.example.huoapp.ui.game.GameCommentActivity;

import butterknife.OnClick;

/**
 * Created by tinle on 2017/11/6.
 */

public class HomeFragment extends BaseLazyFragment {

    public static HomeFragment newInstance() {

        Bundle args = new Bundle();

        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected int getContentViewLayoutId() {
        return R.layout.fragment_home;
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

    @OnClick(R.id.tv_sign)
    void action(){
        readyGo(SignActivity.class);
    }

    @OnClick({R.id.btn_comment})
    void actionTest(){
        readyGo(GameCommentActivity.class);
    }
}
