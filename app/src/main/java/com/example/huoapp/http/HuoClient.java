package com.example.huoapp.http;

import android.content.Context;

import com.example.huoapp.http.callback.IError;
import com.example.huoapp.http.callback.IFailure;
import com.example.huoapp.http.callback.IRequest;
import com.example.huoapp.http.callback.ISuccess;
import com.example.huoapp.http.callback.RequestCallbacks;

import java.util.Map;
import java.util.WeakHashMap;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by lun on 2017/11/4.
 */

public final class HuoClient {
    private static final WeakHashMap<String, Object> PARAMS = HuoCreator.getParams();
    private final String URL;
    private final IRequest REQUEST;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError ERROR;
    private final RequestBody BODY;
    private final Context CONTEXT;

    HuoClient(String url,
               Map<String, Object> params,
               IRequest request,
               ISuccess success,
               IFailure failure,
               IError error,
               RequestBody body,
               Context context){
        this.URL = url;
        PARAMS.putAll(params);
        this.REQUEST = request;
        this.SUCCESS = success;
        this.FAILURE = failure;
        this.ERROR = error;
        this.BODY = body;
        this.CONTEXT = context;
    }

    public static HuoClientBuilder builder(){
        return new HuoClientBuilder();
    }

    private void request(HttpMethod method){
        final HuoService service = HuoCreator.getHuoService();

        Call<String> call = null;

        if (REQUEST != null){
            REQUEST.onRequestStart();
        }

        switch (method){
            case GET:
                call = service.get(URL, PARAMS);
                break;
            case POST:
                call = service.post(URL, PARAMS);
                break;
            case POST_JSON:
                call = service.postJson(URL, BODY);
                break;
            default:
                break;
        }
    }

    private Callback<String> getRequestCallback() {
        return new RequestCallbacks(
                REQUEST,
                SUCCESS,
                FAILURE,
                ERROR
        );
    }

    public final void get() {
        request(HttpMethod.GET);
    }

    public final void post() {
        if (BODY == null) {
            request(HttpMethod.POST);
        } else {
            if (!PARAMS.isEmpty()) {
                throw new RuntimeException("params must be null!");
            }
            request(HttpMethod.POST_JSON);
        }
    }
}
