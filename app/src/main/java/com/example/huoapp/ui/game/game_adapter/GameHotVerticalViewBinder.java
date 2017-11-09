package com.example.huoapp.ui.game.game_adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.huoapp.R;
import com.example.huoapp.manager.AppManager;
import com.example.huoapp.ui.game.GameDetailsActivity;

import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by tinle on 2017/11/8.
 */
public class GameHotVerticalViewBinder extends ItemViewBinder<GameItem, GameHotVerticalViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_game_vertical, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull final ViewHolder holder, @NonNull GameItem gameHotVertical) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppManager.getInstance().readyGo(holder.itemView.getContext(), GameDetailsActivity.class,null);
            }
        });
    }

    static class ViewHolder extends RecyclerView.ViewHolder {


        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
