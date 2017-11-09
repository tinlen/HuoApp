package com.example.huoapp.ui.pupil;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.huoapp.R;
import com.example.huoapp.base.baseFragment.BaseLazyFragment;
import com.example.huoapp.ui.test.RvTestViewBinder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by tinle on 2017/11/7.
 */

public class SonFragment extends BaseLazyFragment {

    @BindView(R.id.rv_game)
    RecyclerView rvGame;

    private MultiTypeAdapter multitype;
    private List<String> datas = new ArrayList<>();

    public static SonFragment newInstance() {

        Bundle args = new Bundle();

        SonFragment fragment = new SonFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.fragment_son;
    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {
        rvGame.setLayoutManager(new LinearLayoutManager(mContext));

        for (int i = 0 ; i < 20;i++){
            datas.add(""+i);
        }

        multitype = new MultiTypeAdapter(datas);
    }

    @Override
    protected void onFirstUserVisible() {

        multitype.register(String.class,new RvTestViewBinder());

        rvGame.setAdapter(multitype);
    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }
}
