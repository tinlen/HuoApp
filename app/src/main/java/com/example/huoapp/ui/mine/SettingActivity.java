package com.example.huoapp.ui.mine;

import android.os.Bundle;
import android.view.View;

import com.example.huoapp.R;
import com.example.huoapp.base.baseActivity.BaseSwipeBackActivity;
import com.example.huoapp.util.FileUtils;
import com.example.huoapp.util.ToastUtil;

import butterknife.OnClick;

/**
 * 设置
 * Created by tinle on 2017/11/10.
 */

public class SettingActivity extends BaseSwipeBackActivity {
    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {

    }

    @OnClick({R.id.ll_change_password,R.id.ll_clear_cache})
    void actionClick(View view){
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;

            case R.id.ll_change_password:
                readyGo(ChangePasswordActivity.class);
                break;

            case R.id.ll_clear_cache:
                FileUtils.clearCache(this);

                ToastUtil.toast(this,"清理成功");
                break;
        }
    }
}
