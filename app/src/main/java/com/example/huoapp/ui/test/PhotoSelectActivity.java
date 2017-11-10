package com.example.huoapp.ui.test;

import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.huoapp.R;
import com.example.huoapp.base.baseActivity.BaseSwipeBackActivity;
import com.example.huoapp.util.AppFileHelper;
import com.example.huoapp.widget.RoundImageView;
import com.example.huoapp.widget.album.ImageInfo;
import com.example.huoapp.widget.album.LocalPhotoManager;
import com.example.huoapp.widget.album.PhotoGridFragment;

import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;

/**
 * Created by tinle on 2017/11/10.
 */

public class PhotoSelectActivity extends BaseSwipeBackActivity {
    @BindView(R.id.riv_head)
    RoundImageView ivHead;

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_photo_select;
    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {
        AppFileHelper.initStoryPath();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_photo, PhotoGridFragment.newInstance()).commit();
    }

    @Subscribe
    public void getEvent(ImageInfo imageInfo){
        Glide.with(this).load(imageInfo.thumbnailPath).into(ivHead);
    }

    @Override
    protected boolean isBindEventBusHere() {
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalPhotoManager.getInstance().writeToLocal();
    }
}
