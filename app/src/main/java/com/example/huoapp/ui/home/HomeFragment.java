package com.example.huoapp.ui.home;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.blankj.utilcode.util.SizeUtils;
import com.example.huoapp.R;
import com.example.huoapp.base.baseFragment.BaseLazyFragment;
import com.example.huoapp.ui.album.PhotoSelectActivity;
import com.example.huoapp.ui.game.GameCommentActivity;
import com.example.huoapp.util.HuoUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by tinle on 2017/11/6.
 */

public class HomeFragment extends BaseLazyFragment {

    @BindView(R.id.rl_top)
    RelativeLayout rlTop;

    public static HomeFragment newInstance() {

        Bundle args = new Bundle();

        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            int statusBarHeight = HuoUtils.getStatusBarHeight(mContext);
            rlTop.getLayoutParams().height = statusBarHeight + SizeUtils.dp2px(50);
            rlTop.setPadding(0,statusBarHeight,0,0);
        }
    }

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.fragment_home;
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

    @OnClick(R.id.tv_sign)
    void action(){
        readyGo(SignActivity.class);
    }

    @OnClick({R.id.btn_comment,R.id.btn_photo})
    void actionTest(View view){
        switch (view.getId()){
            case R.id.btn_comment:

                readyGo(GameCommentActivity.class);
                break;

            case R.id.btn_photo:
                readyGo(PhotoSelectActivity.class);
                break;
        }
    }
}
