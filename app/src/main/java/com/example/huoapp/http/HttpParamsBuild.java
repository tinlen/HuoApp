package com.example.huoapp.http;

import com.blankj.utilcode.util.AuthCodeUtil;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.RSAUtils;
import com.example.huoapp.SdkConstant;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by liu hong liang on 2016/11/9.
 * 火速加密请求参数构造器
 *
 */
public class HttpParamsBuild {
    private static final String TAG = HttpParamsBuild.class.getSimpleName();
    private static String randChDict="qwertyuiopasdfghjklzxcvbnm123456789QWERTYUIOPASDFGHJKLZXCVBNM";
    private String jsonParam="";
    private String authkey;
    private Map<String,Object> httpParams;
    public HttpParamsBuild(String jsonParam) {
        this.jsonParam = jsonParam;
        encodeData();
    }
    public HttpParamsBuild(Map<String,Object> paramMap){
        jsonParam=new Gson().toJson(paramMap);
        encodeData();
    }
    private void encodeData() {
        httpParams=new HashMap<>();
        String randCh = getRandCh(16);
        //生成key
//        client_id 与 时间戳 与 rand16 使用下划线(_)连接，得到 rsakey
        long time= System.currentTimeMillis()+ SdkConstant.SERVER_TIME_INTERVAL;
        StringBuffer keyBuffer=new StringBuffer(SdkConstant.HS_CLIENTID).append("_")
                .append(time).append("_").append(randCh);
        LogUtils.e(TAG,"key加密前："+keyBuffer);
        String key=null;
        try {
            key= new String(RSAUtils.encryptByPublicKey(keyBuffer.toString().getBytes(), SdkConstant.RSA_PUBLIC_KEY), "utf-8");
            LogUtils.e(TAG,"key公钥加密后："+key);
            //生成key
            httpParams.put("key",key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //生成加密数据
//        6、2中的rand16与client_key组成对称加密参数 authkey ([client_key]_rand16)
//        7、将requestdata与 authkey 对称加密并 URLencoding 得到请求参数  `data`
        StringBuffer dataKeyBuffer=new StringBuffer(SdkConstant.HS_CLIENTKEY).append(randCh);
        this.authkey=dataKeyBuffer.toString();
        LogUtils.e(TAG,"data加密前1："+jsonParam);
        String data = AuthCodeUtil.authcodeEncode(jsonParam, authkey);
        LogUtils.e(TAG,"key:"+key+"\ndata:"+data);
        httpParams.put("data",data);

    }

    public Map<String, Object> getHttpParams(){
        return httpParams;
    }


    public String getAuthkey() {
        return authkey;
    }
    /**
     * 随机从randChDict字典里获取length长度的字符串
     * @param length
     * @return
     */
    public static String getRandCh(int length){
        int dictLength = randChDict.length();
        Random random=new Random();
        StringBuffer buffer=new StringBuffer();
        for(int i=0;i<length;i++){
            buffer.append(randChDict.charAt(random.nextInt(dictLength)));
        }
        return buffer.toString();
    }
}
