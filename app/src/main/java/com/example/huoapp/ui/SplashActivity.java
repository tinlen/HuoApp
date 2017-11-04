package com.example.huoapp.ui;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

import com.example.huoapp.R;
import com.example.huoapp.base.baseActivity.BaseActivity;
import com.socks.library.KLog;

import butterknife.BindView;

/**
 * Created by tinle on 2017/11/3.
 */

public class SplashActivity extends BaseActivity {
    @BindView(R.id.tv_count)
    TextView tvCount;

    private int i = 3;

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {


        new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long l) {
                i--;
                tvCount.setText(i+"秒");
            }

            @Override
            public void onFinish() {
                readyGoThenKill(MainActivity.class);
            }
        }.start();
    }
}
