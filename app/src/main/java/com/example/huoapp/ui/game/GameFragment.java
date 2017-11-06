package com.example.huoapp.ui.game;

import android.os.Bundle;

import com.example.huoapp.R;
import com.example.huoapp.base.baseFragment.BaseLazyFragment;
import com.example.huoapp.ui.SwipeDemoActivity;

import butterknife.OnClick;

/**
 * Created by tinle on 2017/11/6.
 */

public class GameFragment extends BaseLazyFragment {


    public static GameFragment newInstance() {

        Bundle args = new Bundle();

        GameFragment fragment = new GameFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected int getContentViewLayoutId() {
        return R.layout.fragment_game;
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

    @OnClick(R.id.btn_test)
    void action(){
        readyGo(SwipeDemoActivity.class);
    }

}
