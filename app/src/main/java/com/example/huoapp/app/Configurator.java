package com.example.huoapp.app;

import android.os.Handler;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Interceptor;

/**
 * Created by lun on 2017/11/4.
 */

public final class Configurator {

    private static final HashMap<Object, Object> HUO_CONFIGS = new HashMap<>();
    private static final Handler HANDLER = new Handler();
    private static final ArrayList<Interceptor> INTERCEPTORS = new ArrayList<>();

    private Configurator(){
        HUO_CONFIGS.put(ConfigKeys.HANDLER,HANDLER);
    }

    static Configurator getInstance() {
        return Holder.INSTANCE;
    }

    final HashMap<Object, Object> getHuoConfigs() {
        return HUO_CONFIGS;
    }

    private static class Holder {
        private static final Configurator INSTANCE = new Configurator();
    }

    public final Configurator withApiHost(String host) {
        HUO_CONFIGS.put(ConfigKeys.API_HOST, host);
        return this;
    }

    public final void configure(){

    }

    public final Configurator withInterceptor(Interceptor interceptor) {
        INTERCEPTORS.add(interceptor);
        HUO_CONFIGS.put(ConfigKeys.INTERCEPTOR, INTERCEPTORS);
        return this;
    }

    @SuppressWarnings("unchecked")
    final <T> T getConfiguration(Object key) {
        final Object value = HUO_CONFIGS.get(key);
        if (value == null) {
            throw new NullPointerException(key.toString() + " IS NULL");
        }
        return (T) HUO_CONFIGS.get(key);
    }
}
