package com.example.huoapp.widget.album;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.blankj.utilcode.util.ScreenUtils;
import com.bumptech.glide.Glide;
import com.example.huoapp.R;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by tinle on 2017/11/10.
 */
public class AlbumViewBinder extends ItemViewBinder<ImageInfo, AlbumViewBinder.ViewHolder> {
    static int width = ScreenUtils.getScreenWidth();
    static int item = width / 4;
    private int lastPos = -1;
    private int currentSelectPos = -1;

    private Context context;

    public AlbumViewBinder(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_album, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull final ViewHolder holder, @NonNull final ImageInfo album) {
        Glide.with(context).load(album.thumbnailPath).into(holder.iv);

        holder.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = getPosition(holder);

                if (currentSelectPos == pos){
                    return;
                }

                EventBus.getDefault().post(album);
                lastPos = currentSelectPos;
                currentSelectPos = pos;

                getAdapter().notifyItemChanged(lastPos);


                holder.ivConver.setVisibility(View.VISIBLE);
            }
        });

        holder.ivConver.setVisibility(getPosition(holder) == currentSelectPos?View.VISIBLE:View.GONE);

    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv)
        ImageView iv;
        @BindView(R.id.iv_conver)
        ImageView ivConver;
        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            iv.getLayoutParams().height = item;
            ivConver.getLayoutParams().height = item;
        }
    }
}
