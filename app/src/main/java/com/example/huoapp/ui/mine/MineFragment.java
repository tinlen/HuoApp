package com.example.huoapp.ui.mine;

import android.os.Bundle;

import com.example.huoapp.R;
import com.example.huoapp.base.baseFragment.BaseLazyFragment;

/**
 * Created by tinle on 2017/11/6.
 */

public class MineFragment extends BaseLazyFragment {

    public static MineFragment newInstance() {

        Bundle args = new Bundle();

        MineFragment fragment = new MineFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected int getContentViewLayoutId() {
        return R.layout.fragment_mine;
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
