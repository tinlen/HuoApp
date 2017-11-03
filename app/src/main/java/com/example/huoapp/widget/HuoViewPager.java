package com.example.huoapp.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by liuhongliang on 2017/8/28.
 * 可设置是否滑动的ViewPager
 * 1.viewpager 主要是通过重写了onInterceptTouchEvent和onTouchEvent来实现的效果，所以对这两个方法进行判断屏蔽滑动
 * 2.当viewpager 作为子view，父view根据子view是否可以滑动来判断是否要把事件交给子view处理，所以把横向滑动进行判断是否屏蔽滑动
 */

public class HuoViewPager extends ViewPager {
    private boolean canScroll = false;

    public HuoViewPager(Context context) {
        super(context);
    }

    public HuoViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if(this.canScroll) {
            try {
                return super.onInterceptTouchEvent(ev);
            } catch (IllegalArgumentException var3) {
                var3.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        return this.canScroll?super.onTouchEvent(event):false;
    }

    public void toggleLock() {
        this.canScroll = !this.canScroll;
    }

    public void setCanScroll(boolean canScroll) {
        this.canScroll = canScroll;
    }

    public boolean isCanScroll() {
        return this.canScroll;
    }

    /**
     * 当viewpager 作为子view，父view根据子view是否可以滑动来判断是否要把事件交给子view处理，所以把横向滑动进行判断是否屏蔽滑动
     * @param direction
     * @return
     */
    @Override
    public boolean canScrollHorizontally(int direction) {
        return this.canScroll?super.canScrollHorizontally(direction):false;
    }

}
