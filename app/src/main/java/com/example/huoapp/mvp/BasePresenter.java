package com.example.huoapp.mvp;

/**
 * Created by tinle on 2017/11/2.
 */

public class BasePresenter<M extends IModel, V extends IView> implements IPresenter{

    protected M mModel;
    protected V mRootView;

    public BasePresenter(M mModel, V mRootView) {
        this.mModel = mModel;
        this.mRootView = mRootView;
        onStart();
    }

    public BasePresenter(V mRootView) {
        this.mRootView = mRootView;
        onStart();
    }

    public BasePresenter() {
        onStart();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onDestroy() {

    }
}
