package com.example.huoapp.util;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

/**
 * Created by tinle on 2017/11/10.
 */

public class ToastUtil {
    private static Toast toast;

    public static void toast(Context context, String msg){
        if (toast!=null&&toast.getView().getVisibility()== View.VISIBLE){
            toast.cancel();
        }
        toast = Toast.makeText(context,msg,Toast.LENGTH_SHORT);
        ((Activity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                toast.show();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        toast.cancel();
                    }
                },1000);
            }
        });
    }
}
