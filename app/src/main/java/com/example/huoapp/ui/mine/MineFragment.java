package com.example.huoapp.ui.mine;

import android.os.Bundle;
import android.view.View;

import com.example.huoapp.R;
import com.example.huoapp.base.baseFragment.BaseLazyFragment;

import butterknife.OnClick;

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

    @OnClick({
            R.id.layout_person_info,
            R.id.layout_my_game,
            R.id.layout_my_feed,
            R.id.layout_record_detail,
            R.id.layout_invited_record,
            R.id.layout_message_record,
            R.id.layout_setting})

    void actionLayout(View view){
        switch (view.getId()){
            case R.id.layout_person_info:

                break;
            case R.id.layout_my_game:
                readyGo(MineGameActivity.class);
                break;

            case R.id.layout_my_feed:

                break;

            case R.id.layout_record_detail:

                break;
            case R.id.layout_invited_record:

                break;

            case R.id.layout_message_record:

                break;

            case R.id.layout_setting:

                break;

            default:
                    break;
        }
    }
}
