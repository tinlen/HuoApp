package com.example.huoapp.ui;

import android.os.Bundle;

import com.example.huoapp.R;
import com.example.huoapp.base.baseActivity.BaseSwipeBackActivity;

/**
 * Created by tinle on 2017/11/4.
 */

public class SwipeDemoActivity extends BaseSwipeBackActivity {
    @Override
    protected int getContentViewLayoutId() {
        return R.layout.fragment_simple;
    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {

    }
}
