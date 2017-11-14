package com.example.huoapp.widget.dialog;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.example.huoapp.R;
import com.example.huoapp.widget.RoundImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by tinle on 2017/11/14.
 */

public class GameTaskDialogFragment extends DialogFragment {
    @BindView(R.id.iv_game_icon)
    RoundImageView ivGameIcon;

    @BindView(R.id.tv_game_name)
    TextView tvGameName;

    @BindView(R.id.tv_game_award)
    TextView tvGameAward;

    @BindView(R.id.tv_game_size)
    TextView tvGameSize;

    @BindView(R.id.rv_rule)
    RecyclerView rvRule;

    public static GameTaskDialogFragment newInstance(Bundle args) {
        GameTaskDialogFragment fragment = new GameTaskDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(false);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View view = inflater.inflate(R.layout.dialog_fragment_game_task, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        rvRule.setLayoutManager(new LinearLayoutManager(getContext()));

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();


    }

    @OnClick({R.id.ib_close,R.id.btn_task,R.id.btn_award})
    void actionClick(View view){
        switch (view.getId()){
            case R.id.ib_close:
                dismiss();
                break;

            case R.id.btn_task:

                break;

            case R.id.btn_award:

                break;
        }
    }

}
