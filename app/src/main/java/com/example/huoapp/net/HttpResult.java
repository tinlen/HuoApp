package com.example.huoapp.net;

/**
 * Created by tinle on 2017/11/2.
 */

public class HttpResult<T> {
    private String msg;
    private String code;
    private T data;

    public String getMsg() {
        return msg;
    }

    public String getCode() {
        return code;
    }

    public T getData() {
        return data;
    }
}
