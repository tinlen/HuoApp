package com.example.huoapp.ui.game;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.SizeUtils;
import com.example.huoapp.R;
import com.example.huoapp.base.baseActivity.BaseSwipeBackActivity;
import com.example.huoapp.util.HuoUtils;

import butterknife.BindView;
import butterknife.OnClick;
import qiu.niorgai.StatusBarCompat;

/**
 * 游戏搜索
 * Created by tinle on 2017/11/13.
 */

public class SearchGameActivity extends BaseSwipeBackActivity {
    @BindView(R.id.ll_top)
    LinearLayout llTop;

    @Override
    protected int getContentViewLayoutId() {
        StatusBarCompat.translucentStatusBar(this,true);
        return R.layout.activity_search_game;
    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            int statusBarHeight = HuoUtils.getStatusBarHeight(this);
            llTop.getLayoutParams().height = statusBarHeight + SizeUtils.dp2px(50);
            llTop.setPadding(0,statusBarHeight,0,0);
        }
    }

    @OnClick({R.id.ib_back})
    void actionClick(View view){
        switch (view.getId()){
            case R.id.ib_back:
                finish();
                break;
        }
    }
}
