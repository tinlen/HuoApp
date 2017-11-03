package com.example.huoapp.ui;

import android.os.Bundle;
import android.os.CountDownTimer;

import com.example.huoapp.R;
import com.example.huoapp.base.baseActivity.BaseActivity;

/**
 * Created by tinle on 2017/11/3.
 */

public class SplashActivity extends BaseActivity {

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {

        new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                readyGoThenKill(MainActivity.class);
            }
        }.start();
    }
}
