package com.example.huoapp.ui.game;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.blankj.utilcode.util.SizeUtils;
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
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.yqritc.recyclerviewflexibledivider.VerticalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by tinle on 2017/11/6.
 */

public class GameFragment extends BaseLazyFragment {

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.rv_game)
    RecyclerView rvGame;

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
    protected void onFirstUserVisible() {
        multiType = new MultiTypeAdapter(getTestData());

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
    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }

    /**
     * 模拟数据
     * @return
     */
    private List<Object> getTestData(){
        List<Object> objects = new ArrayList<>();
        List<String> banner = new ArrayList<>();
        banner.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1510135445321&di=da971be6c7083eb1318dafb77e99f0c9&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimgad%2Fpic%2Fitem%2Fe61190ef76c6a7ef38b3161bf6faaf51f3de6672.jpg");
        banner.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1510135655582&di=0c9ceb4f5c5ef24c3cedfda921851be8&imgtype=0&src=http%3A%2F%2Fimg5.web07.cn%2FUPics%2FBizhi%2F2016%2F1209%2F240218091041431.jpg");
        banner.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1510135445321&di=da971be6c7083eb1318dafb77e99f0c9&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimgad%2Fpic%2Fitem%2Fe61190ef76c6a7ef38b3161bf6faaf51f3de6672.jpg");
        banner.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1510135655582&di=0c9ceb4f5c5ef24c3cedfda921851be8&imgtype=0&src=http%3A%2F%2Fimg5.web07.cn%2FUPics%2FBizhi%2F2016%2F1209%2F240218091041431.jpg");

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
