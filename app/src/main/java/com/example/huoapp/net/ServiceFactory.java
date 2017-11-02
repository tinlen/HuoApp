package com.example.huoapp.net;

import com.google.gson.Gson;

import java.lang.reflect.Field;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by tinle on 2017/11/2.
 */

public class ServiceFactory {
    private final Gson mGson;
    private final Retrofit retrofit;

    private ServiceFactory(){
        mGson = new Gson();

        retrofit = new Retrofit.Builder()
                .client(getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create(mGson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    private static class Holder{
        private static final ServiceFactory INSTANCE = new ServiceFactory();
    }

    public static ServiceFactory getInstance(){
        return Holder.INSTANCE;
    }

    public <T> T createService(Class<T> serviceClass){
        String baseUrl = "";

        try {
            Field field = serviceClass.getField("BASE_URL");
            baseUrl = (String) field.get(serviceClass);
        } catch (Exception e) {
            e.printStackTrace();
        }

        retrofit.newBuilder().baseUrl(baseUrl);

        return retrofit.create(serviceClass);
    }

    private OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        //设置


        return httpClientBuilder.build();
    }
}
