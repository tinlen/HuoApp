package com.example.huoapp.ui.mine;

import android.os.Bundle;
import android.view.View;

import com.example.huoapp.R;
import com.example.huoapp.base.baseActivity.BaseSwipeBackActivity;

import butterknife.OnClick;

/**
 * Created by tinle on 2017/11/13.
 */

public class InfoChangeActivity extends BaseSwipeBackActivity {

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_info_change;
    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {

    }

    @OnClick(R.id.iv_back)
    void actionClick(View view){
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
