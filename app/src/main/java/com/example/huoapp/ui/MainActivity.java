package com.example.huoapp.ui;

import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;

import com.example.huoapp.R;
import com.example.huoapp.base.baseActivity.BaseActivity;
import com.example.huoapp.manager.AppManager;
import com.lzy.okgo.OkGo;

public class MainActivity extends BaseActivity {
    private static long DOUBLE_CLICK_TIME = 0L;

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_MENU) {
            return true;
        } else if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {

            if ((System.currentTimeMillis() - DOUBLE_CLICK_TIME) > 2000) {
                DOUBLE_CLICK_TIME = System.currentTimeMillis();
                Toast.makeText(this,getString(R.string.exit_app),Toast.LENGTH_SHORT).show();
            } else {
                AppManager.getInstance().exit();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
