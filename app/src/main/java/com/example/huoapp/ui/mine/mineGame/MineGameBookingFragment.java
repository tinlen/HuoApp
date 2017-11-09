package com.example.huoapp.ui.mine.mineGame;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Size;

import com.blankj.utilcode.util.SizeUtils;
import com.example.huoapp.R;
import com.example.huoapp.base.baseFragment.BaseLazyFragment;
import com.example.huoapp.ui.mine.mineGame.adapter.BookingGame;
import com.example.huoapp.ui.mine.mineGame.adapter.BookingGameViewBinder;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by tinle on 2017/11/8.
 */

public class MineGameBookingFragment extends BaseLazyFragment {

    @BindView(R.id.rv_game)
    RecyclerView rvGame;

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;

    private MultiTypeAdapter multiType;


    public static MineGameBookingFragment newInstance() {
        
        Bundle args = new Bundle();
        
        MineGameBookingFragment fragment = new MineGameBookingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {
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
    protected int getContentViewLayoutId() {
        return R.layout.fragment_mine_game_booking;
    }

    @Override
    protected void onFirstUserVisible() {
        multiType = new MultiTypeAdapter(getTestData());

        rvGame.setLayoutManager(new LinearLayoutManager(mContext));

        multiType.register(BookingGame.class,new BookingGameViewBinder());

        rvGame.addItemDecoration(new HorizontalDividerItemDecoration.Builder(mContext)
                .color(Color.TRANSPARENT)
                .size(SizeUtils.dp2px(10))
                .build());

        rvGame.setAdapter(multiType);
    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }

    public List<BookingGame> getTestData(){
        List<BookingGame> bookingGames = new ArrayList<>();
        BookingGame bookingGame = new BookingGame();

        List<String> urls = new ArrayList<>();

        urls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1510135445321&di=da971be6c7083eb1318dafb77e99f0c9&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimgad%2Fpic%2Fitem%2Fe61190ef76c6a7ef38b3161bf6faaf51f3de6672.jpg");
        urls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1510135655582&di=0c9ceb4f5c5ef24c3cedfda921851be8&imgtype=0&src=http%3A%2F%2Fimg5.web07.cn%2FUPics%2FBizhi%2F2016%2F1209%2F240218091041431.jpg");

        bookingGame.setPhotoGrids(urls);
        for (int i = 0;i<10;i++){
            bookingGames.add(bookingGame);
        }

        return bookingGames;
    }
}
