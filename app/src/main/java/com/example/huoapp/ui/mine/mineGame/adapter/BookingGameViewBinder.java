package com.example.huoapp.ui.mine.mineGame.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.blankj.utilcode.util.SizeUtils;
import com.example.huoapp.R;
import com.example.huoapp.listener.IRvItemOnClickListener;
import com.example.huoapp.ui.album.PhotoBrowseActivity;
import com.example.huoapp.widget.decoration.GridItemDecoration;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by tinle on 2017/11/8.
 */
public class BookingGameViewBinder extends ItemViewBinder<BookingGame, BookingGameViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_booking_game, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull final ViewHolder holder, final  @NonNull BookingGame bookingGame) {

        MultiTypeAdapter typeAdapter = new MultiTypeAdapter(bookingGame.getPhotoGrids());

        PhotoGridViewBinder photoGridViewBinder = new PhotoGridViewBinder();
        photoGridViewBinder.setIRvItemOnClickListener(new IRvItemOnClickListener() {
            @Override
            public void click(int pos, View view) {
                PhotoBrowseActivity.startWithElement((Activity) holder.itemView.getContext(),bookingGame.getPhotoGrids(),pos,(ImageView)view);
            }
        });
        typeAdapter.register(String.class,photoGridViewBinder);

        holder.rvGamePhoto.setLayoutManager(new GridLayoutManager(holder.itemView.getContext(),2));
        holder.rvGamePhoto.setAdapter(typeAdapter);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.rv_game_photo)
        RecyclerView rvGamePhoto;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
