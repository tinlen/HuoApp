package com.example.huoapp.widget.album;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.Size;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.SizeUtils;
import com.example.huoapp.R;
import com.example.huoapp.widget.decoration.GridItemDecoration;

/**
 *
 * Created by tinle on 2017/11/9.
 */

public class PhotoFragment extends Fragment {

    private RecyclerView rvPhoto;

    public static PhotoFragment newInstance() {

        Bundle args = new Bundle();

        PhotoFragment fragment = new PhotoFragment();
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
        rvPhoto.addItemDecoration(new GridItemDecoration(4, SizeUtils.dp2px(4),false));
    }
}
