package com.example.huoapp.base;

/**
 * Created by tinle on 2017/11/2.
 */

public abstract class Presenter<V extends BaseView> {
    private V view;

    public Presenter(final V view){
        this.view = view;
    }

    public V getView(){
        return view;
    }

    public abstract void start();

    public abstract void cancel();
}
