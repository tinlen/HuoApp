package com.example.huoapp.ui.mine;

import android.os.Bundle;
import android.view.View;

import com.example.huoapp.R;
import com.example.huoapp.base.baseActivity.BaseSwipeBackActivity;

import butterknife.OnClick;

/**
 * 提现
 * Created by tinle on 2017/11/10.
 */

public class WithdrawActivity extends BaseSwipeBackActivity {
    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_withdraw;
    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {

    }

    @OnClick({R.id.ll_mobile_recharge})
    void actionClick(View view){
        switch (view.getId()){
            case R.id.ll_mobile_recharge:
                readyGo(MobileRechargeActivity.class);
                break;
        }
    }
}
