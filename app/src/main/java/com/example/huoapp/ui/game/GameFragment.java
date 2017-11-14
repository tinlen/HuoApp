package com.example.huoapp.ui.game;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Size;
import android.view.View;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.example.huoapp.HuoApplication;
import com.example.huoapp.R;
import com.example.huoapp.base.baseFragment.BaseLazyFragment;
import com.example.huoapp.ui.game.game_adapter.GameBanner;
import com.example.huoapp.ui.game.game_adapter.GameBannerViewBinder;
import com.example.huoapp.ui.game.game_adapter.GameHot;
import com.example.huoapp.ui.game.game_adapter.GameHotViewBinder;
import com.example.huoapp.ui.game.game_adapter.GameItem;
import com.example.huoapp.ui.game.game_adapter.GameShare;
import com.example.huoapp.ui.game.game_adapter.GameShareViewBinder;
import com.example.huoapp.ui.game.game_adapter.GameNewOrder;
import com.example.huoapp.ui.game.game_adapter.GameNewOrderViewBinder;
import com.example.huoapp.util.HuoUtils;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.yqritc.recyclerviewflexibledivider.VerticalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by tinle on 2017/11/6.
 */

public class GameFragment extends BaseLazyFragment {

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.rv_game)
    RecyclerView rvGame;
    @BindView(R.id.ll_top)
    LinearLayout llTop;

    private MultiTypeAdapter multiType;


    public static GameFragment newInstance() {

        Bundle args = new Bundle();

        GameFragment fragment = new GameFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected int getContentViewLayoutId() {
        return R.layout.fragment_game;
    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            int statusBarHeight = HuoUtils.getStatusBarHeight(mContext);
            llTop.getLayoutParams().height = statusBarHeight + SizeUtils.dp2px(50);
            llTop.setPadding(0,statusBarHeight,0,0);
        }
    }

    @Override
    protected void onFirstUserVisible() {
        multiType = new MultiTypeAdapter();

        multiType.register(GameBanner.class,new GameBannerViewBinder());
        multiType.register(GameShare.class,new GameShareViewBinder());
        multiType.register(GameNewOrder.class,new GameNewOrderViewBinder());
        multiType.register(GameHot.class,new GameHotViewBinder());

        rvGame.addItemDecoration(new HorizontalDividerItemDecoration.Builder(mContext)
                .size(SizeUtils.dp2px(10))
                .color(Color.TRANSPARENT)
                .build());

        rvGame.setLayoutManager(new LinearLayoutManager(mContext));
        rvGame.setAdapter(multiType);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.setRefreshing(false);
                    }
                },3000);
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                multiType.setItems(getTestData());
                multiType.notifyDataSetChanged();
            }
        },1500);
    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }

    @OnClick({R.id.tv_search})
    void actionClick(View view){
        switch (view.getId()){
            case R.id.tv_search:
                readyGo(SearchGameActivity.class);
                break;
        }
    }

    /**
     * 模拟数据
     * @return
     */
    private List<Object> getTestData(){
        List<Object> objects = new ArrayList<>();
        List<String> banner = new ArrayList<>();
        banner.add("http://d.ifengimg.com/mw978_mh598/p3.ifengimg.com/a/2017_45/1912e011d196610_size1285_w1024_h791.jpg");
        banner.add("http://d.ifengimg.com/mw978_mh598/p1.ifengimg.com/a/2017_45/bbef55c4f090a90_size1106_w1024_h791.jpg");
        banner.add("http://p1.ifengimg.com/a/2017_44/aa64d943b9ad0e9_size520_w1000_h667.jpg");
        objects.add(new GameBanner(banner));

        objects.add(new GameShare());

        List<GameItem> gameItems = new ArrayList<>();

        for (int i = 0;i< 9;i++){
            gameItems.add(new GameItem());
        }

        GameNewOrder gameNewOrder = new GameNewOrder();
        gameNewOrder.setGameItems(gameItems);

        GameHot gameHot = new GameHot(gameItems);

        objects.add(gameNewOrder);
        objects.add(gameHot);

        return objects;
    }


}
