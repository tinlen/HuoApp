package com.example.huoapp.widget.album;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.SizeUtils;
import com.example.huoapp.R;
import com.example.huoapp.util.HuoPreference;
import com.example.huoapp.widget.decoration.GridItemDecoration;

import me.drakeet.multitype.MultiTypeAdapter;

/**
 * 图片选择器
 * Created by tinle on 2017/11/9.
 */

public class PhotoGridFragment extends Fragment {

    private RecyclerView rvPhoto;
    private String currentAlbumName;
    private MultiTypeAdapter typeAdapter;

    public static PhotoGridFragment newInstance() {

        Bundle args = new Bundle();

        PhotoGridFragment fragment = new PhotoGridFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo,container,false);
        rvPhoto = view.findViewById(R.id.rv_photo);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        rvPhoto.setLayoutManager(new GridLayoutManager(getContext(),4));
        typeAdapter = new MultiTypeAdapter();
        typeAdapter.register(ImageInfo.class,new AlbumViewBinder(getContext()));
        scanImgSync();
    }

    private void scanImgSync() {

        LocalPhotoManager.getInstance().scanImg(new LocalPhotoManager.OnScanListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onFinish() {
                initView();
            }

            @Override
            public void onError(LPException e) {

            }
        });
    }

    private void initView() {
        changeAlbum(LocalPhotoManager.getInstance().getAllPhotoTitle());
    }

    private void changeAlbum(String albumName) {
        if (TextUtils.isEmpty(albumName)) return;
        if (TextUtils.equals(currentAlbumName,albumName)) return;
        currentAlbumName = albumName;

        typeAdapter.setItems(LocalPhotoManager.getInstance().getLocalImages(albumName));
        typeAdapter.notifyDataSetChanged();

        rvPhoto.setAdapter(typeAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
