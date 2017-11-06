package com.example.huoapp.ui.pupil;

import android.os.Bundle;

import com.example.huoapp.R;
import com.example.huoapp.base.baseFragment.BaseLazyFragment;

/**
 * Created by tinle on 2017/11/6.
 */

public class PupilFragment extends BaseLazyFragment {

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
    protected void onFirstUserVisible() {

    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }
}
