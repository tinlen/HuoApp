package com.example.huoapp.base.baseFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Field;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by tinle on 2017/11/2.
 */

public abstract class BaseFragment extends Fragment{

    private Unbinder mUnbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (null != bundle){
            getBundleExtras(bundle);
        }

        if (isBindEventBusHere()){
            EventBus.getDefault().register(this);
        }
    }

    protected abstract int getContentViewLayoutId();

    protected void getBundleExtras(Bundle bundle) {}

    private boolean isBindEventBusHere() {
        return false;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getContentViewLayoutId() != 0){
            return inflater.inflate(getContentViewLayoutId(),null);
        }else {
            return super.onCreateView(inflater, container, savedInstanceState);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnbinder = ButterKnife.bind(this,view);

        initViewsAndEvents(savedInstanceState);
    }

    protected abstract void initViewsAndEvents(Bundle savedInstanceState);



    @Override
    public void onDestroy() {
        super.onDestroy();
        if (isBindEventBusHere()){
            EventBus.getDefault().unregister(this);
        }

        if (null != mUnbinder){
            mUnbinder.unbind();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        // for bug ---> java.lang.IllegalStateException: Activity has been destroyed
        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    protected void finish(){
        getActivity().finish();
    }

    protected FragmentManager getSupportFragmentManager() {
        return getActivity().getSupportFragmentManager();
    }


    protected void readyGo(Class<?> clazz) {
        Intent intent = new Intent(getActivity(), clazz);
        startActivity(intent);
    }

    protected void readyGo(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(getActivity(), clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }
}
