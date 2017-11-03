package com.example.huoapp.http;

import android.content.Context;

import com.example.huoapp.app.Huo;
import com.example.huoapp.http.callback.IError;
import com.example.huoapp.http.callback.IFailure;
import com.example.huoapp.http.callback.IRequest;
import com.example.huoapp.http.callback.ISuccess;

import java.io.File;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by lun on 2017/11/4.
 */

public class HuoClientBuilder {
    private static final WeakHashMap<String, Object> PARAMS = HuoCreator.getParams();
    private String mUrl = null;
    private IRequest mIRequest = null;
    private ISuccess mISuccess = null;
    private IFailure mIFailure = null;
    private IError mIError = null;
    private RequestBody mBody = null;
    private Context mContext = null;

    HuoClientBuilder() {
    }

    public final HuoClientBuilder url(String url) {
        this.mUrl = url;
        return this;
    }

    public final HuoClientBuilder params(WeakHashMap<String, Object> params) {
        PARAMS.putAll(params);
        return this;
    }

    public final HuoClientBuilder params(String key, Object value) {
        PARAMS.put(key, value);
        return this;
    }

    public final HuoClientBuilder json(String json) {
        this.mBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), json);
        return this;
    }

    public final HuoClientBuilder onRequest(IRequest iRequest) {
        this.mIRequest = iRequest;
        return this;
    }

    public final HuoClientBuilder success(ISuccess iSuccess) {
        this.mISuccess = iSuccess;
        return this;
    }

    public final HuoClientBuilder failure(IFailure iFailure) {
        this.mIFailure = iFailure;
        return this;
    }

    public final HuoClientBuilder error(IError iError) {
        this.mIError = iError;
        return this;
    }



    public final HuoClient build() {
        return new HuoClient(mUrl, PARAMS,
                mIRequest, mISuccess, mIFailure,
                mIError, mBody, mContext);
    }
}
