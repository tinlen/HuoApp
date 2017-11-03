package com.example.huoapp.http;

import android.content.Context;

import com.example.huoapp.http.callback.IError;
import com.example.huoapp.http.callback.IFailure;
import com.example.huoapp.http.callback.IRequest;
import com.example.huoapp.http.callback.ISuccess;

import java.io.File;
import java.util.WeakHashMap;

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
    private String mExtension = null;
    private String mName = null;
}
