package com.example.huoapp;

import android.app.Application;
import android.content.Context;

import com.blankj.utilcode.util.LogUtils;
import com.facebook.stetho.Stetho;
import com.lzy.okgo.OkGo;
import com.socks.library.KLog;
import com.squareup.leakcanary.LeakCanary;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by tinle on 2017/11/2.
 */

public class HuoApplication extends Application{

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();

        initApp();

        initAndroidUtil();
        initLeakCanary();
        initLog();
        initStetho();

    }

    public static Context getAppContext(){
        return context;
    }

    private void initApp() {

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(
                new HttpLoggingInterceptor.Logger() {
                    @Override
                    public void log(String message) {
                        KLog.i(message);
                    }
                }
        );
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkGo.getInstance().init(this);

        OkHttpClient.Builder builder = OkGo.getInstance().getOkHttpClient().newBuilder();
        builder.addInterceptor(loggingInterceptor);
    }

    private void initStetho() {
        Stetho.initializeWithDefaults(this);
    }

    private void initAndroidUtil() {
        com.blankj.utilcode.util.Utils.init(this);
        com.blankj.subutil.util.Utils.init(this);
    }

    private void initLeakCanary() {
        // 内存泄露检查工具
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }

    // init it in ur application
    public void initLog() {
        LogUtils.Config config = LogUtils.getConfig()
                .setLogSwitch(BuildConfig.DEBUG)// 设置log总开关，包括输出到控制台和文件，默认开
                .setConsoleSwitch(BuildConfig.DEBUG)// 设置是否输出到控制台开关，默认开
                .setGlobalTag(null)// 设置log全局标签，默认为空
                // 当全局标签不为空时，我们输出的log全部为该tag，
                // 为空时，如果传入的tag为空那就显示类名，否则显示tag
                .setLogHeadSwitch(false)// 设置log头信息开关，默认为开
                .setLog2FileSwitch(false)// 打印log时是否存到文件的开关，默认关
                .setDir("")// 当自定义路径为空时，写入应用的/cache/log/目录中
                .setFilePrefix("")// 当文件前缀为空时，默认为"util"，即写入文件为"util-MM-dd.txt"
                .setBorderSwitch(true)// 输出日志是否带边框开关，默认开
                .setConsoleFilter(LogUtils.V)// log的控制台过滤器，和logcat过滤器同理，默认Verbose
                .setFileFilter(LogUtils.V)// log文件过滤器，和logcat过滤器同理，默认Verbose
                .setStackDeep(1);// log栈深度，默认为1
        LogUtils.d(config.toString());
    }
}
