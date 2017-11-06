package com.example.huoapp.manager;

import com.example.huoapp.http.SdkConstant;
import com.example.huoapp.util.MD5;

import java.util.WeakHashMap;

/**
 * Created by tinle on 2017/11/3.
 */

public class AppApi {
    //广告图高宽比例
    public static final float AD_IMAGE_HW_RATA=300/720f;

    public static final String bannerUrl1="http://img.zcool.cn/community/018f38557b825f00000059ff0f774c.jpg";
    public static final String testUrl="http://h5i.99play.cc/app_api/index";
    public static final String bannerUrl2="http://pic.qiantucdn.com/58pic/17/56/27/12S58PIC89m_1024.jpg";
    public static final String bannerUrl3="http://img.zcool.cn/community/01c97e55447b680000019ae9e36479.jpg";
    public static final String downApkUrl="http://119.147.33.13/imtt.dd.qq.com/16891/C290995FDBAE4D581BD1DD5D977AA170.apk?mkey=5874622ebc7aa782&f=d688&c=0&fsname=com.tencent.mm_6.5.3_980.apk&csr=4d5s&p=.apk";
    public static final String downApkUrl1="http://183.2.192.174/imtt.dd.qq.com/16891/85B6221DE84C466310575D9FBCA453A8.apk?mkey=587580c002e16584&f=8a5d&c=0&fsname=com.tencent.pao_1.0.39.0_139.apk&csr=4d5s&p=.apk";
    //每次重新获取请求地址
    public static String getUrl(String apiName){
        return SdkConstant.BASE_URL+ SdkConstant.BASE_SUFFIX_URL+apiName;
    }

    public static WeakHashMap<String, String> getCommonHttpParams(String apiName){
        WeakHashMap<String, String> httpParams = new WeakHashMap<>();
        httpParams.put("app_id", SdkConstant.HS_APPID);
        httpParams.put("client_id", SdkConstant.HS_CLIENTID);
        httpParams.put("from", SdkConstant.FROM);
        long timestamp=System.currentTimeMillis()/1000;
        httpParams.put("timestamp", timestamp+"");
        //MD5(game/gametype+timestamp+client_key)
        httpParams.put("sign", MD5.md5(new StringBuffer(apiName).append(timestamp).append(SdkConstant.HS_CLIENTKEY).toString()));
        httpParams.put("agentgame",SdkConstant.HS_AGENT);

        return httpParams;
    }
}
