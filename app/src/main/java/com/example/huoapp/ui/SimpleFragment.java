package com.example.huoapp.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.LogUtils;
import com.example.huoapp.R;
import com.example.huoapp.base.baseFragment.BaseLazyFragment;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import butterknife.BindView;

/**
 * Created by tinle on 2017/11/3.
 */

public class SimpleFragment extends BaseLazyFragment {

    private String title;

    @BindView(R.id.tv_title)
    Button tvTitle;

    public static SimpleFragment newInstance(String title) {

        Bundle args = new Bundle();
        args.putString("TITLE", title);
        SimpleFragment fragment = new SimpleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.fragment_simple;
    }

    @Override
    protected void getBundleExtras(Bundle bundle) {
        title = bundle.getString("TITLE");
    }

    @Override
    protected void onFirstUserVisible() {
        tvTitle.setText(title);
        LogUtils.i(title,"onFirstUserVisible");

    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {
        tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onGetTest();
            }
        });
    }

    //TODO: 测试方法
    private void onGetTest() {
        OkGo.<String>get("http://www.baidu.com/")
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Toast.makeText(mContext,response.body(),Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(Response<String> response) {
                    }
                });
    }

    @Override
    protected void onUserVisible() {
        LogUtils.i(title,"onUserVisible");
    }

    @Override
    protected void onUserInvisible() {
        LogUtils.i(title,"onUserInvisible");
        OkGo.getInstance().cancelTag(this);
    }

}
