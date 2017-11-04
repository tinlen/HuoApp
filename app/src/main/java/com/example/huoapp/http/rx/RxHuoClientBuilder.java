package com.example.huoapp.http.rx;

import android.content.Context;

import com.example.huoapp.http.HuoCreator;
import com.example.huoapp.http.callback.IError;
import com.example.huoapp.http.callback.IFailure;
import com.example.huoapp.http.callback.IRequest;
import com.example.huoapp.http.callback.ISuccess;

import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by lun on 2017/11/4.
 */

public class RxHuoClientBuilder {
    private static final WeakHashMap<String, Object> PARAMS = HuoCreator.getParams();
    private String mUrl = null;
    private RequestBody mBody = null;
    private Context mContext = null;

    RxHuoClientBuilder() {
    }

    public final RxHuoClientBuilder url(String url) {
        this.mUrl = url;
        return this;
    }

    public final RxHuoClientBuilder params(WeakHashMap<String, Object> params) {
        PARAMS.putAll(params);
        return this;
    }

    public final RxHuoClientBuilder params(String key, Object value) {
        PARAMS.put(key, value);
        return this;
    }

    public final RxHuoClientBuilder json(String json) {
        this.mBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), json);
        return this;
    }

    public final RxHuoClient build() {
        return new RxHuoClient(mUrl, PARAMS,mBody, mContext);
    }
}
