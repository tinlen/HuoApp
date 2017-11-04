package com.example.huoapp.http.rx;

import android.content.Context;

import com.example.huoapp.http.HttpMethod;
import com.example.huoapp.http.HuoCreator;

import java.util.Map;
import java.util.WeakHashMap;

import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * Created by lun on 2017/11/4.
 */

public final class RxHuoClient {
    private static final WeakHashMap<String, Object> PARAMS = HuoCreator.getParams();
    private final String URL;
    private final RequestBody BODY;
    private final Context CONTEXT;

    RxHuoClient(String url,
                Map<String, Object> params,
                RequestBody body,
                Context context){
        this.URL = url;
        PARAMS.putAll(params);
        this.BODY = body;
        this.CONTEXT = context;
    }

    public static RxHuoClientBuilder builder(){
        return new RxHuoClientBuilder();
    }

    private Observable<String> request(HttpMethod method){
        final RxHuoService service = HuoCreator.getRxtHuoService();

        Observable<String> observable = null;


        switch (method){
            case GET:
                observable = service.get(URL, PARAMS);
                break;
            case POST:
                observable = service.post(URL, PARAMS);
                break;
            case POST_JSON:
                observable = service.postJson(URL, BODY);
                break;
            default:
                break;
        }

        return observable;
    }


    public final Observable<String> get() {
        return request(HttpMethod.GET);
    }

    public final Observable<String> post() {
        if (BODY == null) {
            return request(HttpMethod.POST);
        } else {
            if (!PARAMS.isEmpty()) {
                throw new RuntimeException("params must be null!");
            }
            return request(HttpMethod.POST_JSON);
        }
    }
}
