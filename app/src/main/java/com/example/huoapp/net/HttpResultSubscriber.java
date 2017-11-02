package com.example.huoapp.net;


import rx.Subscriber;

/**
 * Created by tinle on 2017/11/2.
 */

public abstract class HttpResultSubscriber<T> extends Subscriber<HttpResult<T>> {

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
    }

    @Override
    public void onNext(HttpResult<T> tHttpResult) {
        onSuccess(tHttpResult.getData());
    }

    public abstract void onSuccess(T t);

    public abstract void onError(String msg);
}
