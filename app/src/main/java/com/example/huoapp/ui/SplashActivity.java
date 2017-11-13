package com.example.huoapp.ui;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.KeyEvent;
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

    private int i = 2;
    Handler handler = new Handler();

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return false;
    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {
        handler.postDelayed(runnable, 1000);
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            i--;
            KLog.i(i + "秒");
            handler.postDelayed(this,1000);

            if (i == 0){
                handler.removeCallbacks(runnable);
                readyGoThenKill(MainActivity.class);
            }else {
                tvCount.setText( i +"秒");
            }

        }
    };

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return true;
    }
}
