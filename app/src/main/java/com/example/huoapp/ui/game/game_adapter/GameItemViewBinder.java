package com.example.huoapp.ui.game.game_adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.ScreenUtils;
import com.example.huoapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by tinle on 2017/11/8.
 */
public class GameItemViewBinder extends ItemViewBinder<GameItem, GameItemViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_game_horizontal, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull GameItem gameItem) {
        int item = ScreenUtils.getScreenWidth() / 4;
        holder.layoutItem.getLayoutParams().width = item;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.layout_item)
        LinearLayout layoutItem;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
