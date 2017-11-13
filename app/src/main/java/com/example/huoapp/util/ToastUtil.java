package com.example.huoapp.util;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huoapp.R;

/**
 * Created by tinle on 2017/11/10.
 */

public class ToastUtil {
    private static Toast toast;

    public static void toast(Context context, String msg){
        View layout =((Activity) context).getLayoutInflater().inflate(R.layout.layout_toast,null);
        TextView text = layout.findViewById(R.id.tv_toast_msg);
        text.setText(msg);
        if (toast!=null&&toast.getView().getVisibility()== View.VISIBLE){
            toast.cancel();
        }
        toast = new Toast(context);
        toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
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
