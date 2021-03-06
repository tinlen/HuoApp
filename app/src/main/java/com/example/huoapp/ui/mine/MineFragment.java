package com.example.huoapp.ui.mine;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.blankj.utilcode.util.SizeUtils;
import com.example.huoapp.R;
import com.example.huoapp.base.baseFragment.BaseLazyFragment;
import com.example.huoapp.util.HuoUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by tinle on 2017/11/6.
 */

public class MineFragment extends BaseLazyFragment {
    @BindView(R.id.rl_top)
    RelativeLayout rlTop;

    public static MineFragment newInstance() {

        Bundle args = new Bundle();

        MineFragment fragment = new MineFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected int getContentViewLayoutId() {
        return R.layout.fragment_mine;
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
    protected void onFirstUserVisible() {

    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }

    @OnClick({
            R.id.ll_withdraw,
            R.id.layout_person_info,
            R.id.layout_my_game,
            R.id.layout_my_feed,
            R.id.layout_record_detail,
            R.id.layout_invited_record,
            R.id.layout_message_record,
            R.id.layout_setting})

    void actionLayout(View view){
        switch (view.getId()){

            case R.id.ll_withdraw://提现
                readyGo(WithdrawActivity.class);
                break;
            case R.id.layout_person_info://个人资料
                readyGo(InfoChangeActivity.class);
                break;
            case R.id.layout_my_game://我的游戏
                readyGo(MineGameActivity.class);
                break;

            case R.id.layout_my_feed://我的动态

                break;

            case R.id.layout_record_detail://记录明细
                readyGo(RecordDetailActivity.class);
                break;
            case R.id.layout_invited_record://邀请记录

                break;

            case R.id.layout_message_record://消息记录

                break;

            case R.id.layout_setting://设置
                readyGo(SettingActivity.class);
                break;

            default:
                    break;
        }
    }
}
