package com.example.huoapp.ui.game.game_adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.huoapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by tinle on 2017/11/8.
 */
public class GameHotViewBinder extends ItemViewBinder<GameHot, GameHotViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_game_hot, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull GameHot gameHot) {
        holder.rvGame.setLayoutManager(new LinearLayoutManager(holder.rvGame.getContext()));
        MultiTypeAdapter multiType = new MultiTypeAdapter(gameHot.getHots());
        multiType.register(GameItem.class,new GameHotVerticalViewBinder());
        holder.rvGame.setAdapter(multiType);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.rv_game)
        RecyclerView rvGame;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
