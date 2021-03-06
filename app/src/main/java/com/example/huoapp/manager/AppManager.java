package com.example.huoapp.manager;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by tinle on 2017/11/2.
 */

public class AppManager {

    private static AppManager instance = null;
    private static List<Activity> activityStack = new LinkedList<>();

    private AppManager(){}

    public static AppManager getInstance(){
        if (null == instance){
            synchronized (AppManager.class) {
                if (null == instance) {
                    instance = new AppManager();
                }
            }
        }
        return instance;
    }

    public int   size() { return activityStack.size();}

    public synchronized Activity getForwardActivity(){
        return size() > 0 ? activityStack.get(size() - 1) : null;
    }

    public synchronized void addActivity(Activity activity){
        activityStack.add(activity);
    }

    public synchronized void removeActivity(Activity activity){
        if (activityStack.contains(activity)){
            activityStack.remove(activity);
        }
    }

    /**
     * 结束指定的Activity
     */
    public synchronized void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public synchronized void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public synchronized void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    /**
     * 退出应用程序
     */
    @SuppressWarnings("deprecation")
    public synchronized void exit() {
        try {
            finishAllActivity();
            System.gc();
            android.os.Process.killProcess(android.os.Process.myPid());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void readyGo(Context context, Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(context, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }
}
