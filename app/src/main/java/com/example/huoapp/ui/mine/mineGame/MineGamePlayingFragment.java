package com.example.huoapp.ui.mine.mineGame;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.huoapp.R;
import com.example.huoapp.base.baseFragment.BaseLazyFragment;

import butterknife.BindView;

/**
 * Created by tinle on 2017/11/8.
 */

public class MineGamePlayingFragment extends BaseLazyFragment {

    @BindView(R.id.rv_game)
    RecyclerView rvGame;

    public static MineGamePlayingFragment newInstance() {

        Bundle args = new Bundle();

        MineGamePlayingFragment fragment = new MineGamePlayingFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected int getContentViewLayoutId() {
        return R.layout.fragment_mine_game_playing;
    }

    @Override
    protected void onFirstUserVisible() {
        rvGame.setLayoutManager(new GridLayoutManager(mContext,2));
    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }
}
