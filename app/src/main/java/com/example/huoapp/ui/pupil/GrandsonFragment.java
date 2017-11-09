package com.example.huoapp.ui.pupil;

import android.os.Bundle;

import com.example.huoapp.R;
import com.example.huoapp.base.baseFragment.BaseLazyFragment;

/**
 * Created by tinle on 2017/11/7.
 */

public class GrandsonFragment extends BaseLazyFragment {

    public static GrandsonFragment newInstance() {
        
        Bundle args = new Bundle();
        
        GrandsonFragment fragment = new GrandsonFragment();
        fragment.setArguments(args);
        return fragment;
    }
    
    @Override
    protected int getContentViewLayoutId() {
        return R.layout.fragment_son;
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
