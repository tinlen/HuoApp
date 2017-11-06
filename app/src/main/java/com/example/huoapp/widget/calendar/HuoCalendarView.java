package com.example.huoapp.widget.calendar;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * 日历
 * Created by tinle on 2017/11/6.
 */

public class HuoCalendarView extends FrameLayout{
    public HuoCalendarView(@NonNull Context context) {
        this(context, null);
    }

    public HuoCalendarView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public HuoCalendarView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
