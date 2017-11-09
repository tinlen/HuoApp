package com.example.huoapp.ui.pupil;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.subutil.util.ClipboardUtils;
import com.example.huoapp.R;
import com.example.huoapp.base.baseFragment.BaseLazyFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by tinle on 2017/11/7.
 */

public class InviteFragment extends BaseLazyFragment {

    @BindView(R.id.tv_invite_code)
    TextView tvInviteCode;

    public static InviteFragment newInstance() {
        
        Bundle args = new Bundle();
        
        InviteFragment fragment = new InviteFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected int getContentViewLayoutId() {
        return R.layout.fragment_invite;
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

    @OnClick({R.id.ll_copy})
    void actionClick(View view){
        switch (view.getId()){

            case R.id.ll_copy:
                if (!TextUtils.isEmpty(tvInviteCode.getText())){
                    ClipboardUtils.copyText(tvInviteCode.getText());
                    Toast.makeText(mContext,"邀请码已复制",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
