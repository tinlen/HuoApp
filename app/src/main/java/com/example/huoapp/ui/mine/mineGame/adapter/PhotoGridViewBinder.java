package com.example.huoapp.ui.mine.mineGame.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.huoapp.R;
import com.example.huoapp.listener.IRvItemOnClickListener;
import com.example.huoapp.widget.ForceClickImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by tinle on 2017/11/8.
 */
public class PhotoGridViewBinder extends ItemViewBinder<String, PhotoGridViewBinder.ViewHolder> {
    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_photo_grid, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull final ViewHolder holder, @NonNull String url) {
        Glide.with(holder.itemView.getContext()).load(url).into(holder.ivPhoto);

        holder.ivPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != listener){
                    listener.click(getPosition(holder),holder.ivPhoto);
                }
            }
        });
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_photo)
        ImageView ivPhoto;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    private IRvItemOnClickListener listener;

    public void setIRvItemOnClickListener(IRvItemOnClickListener listener){
        this.listener = listener;
    }



}
