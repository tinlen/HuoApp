package com.example.huoapp.ui.home;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SizeUtils;
import com.example.huoapp.R;
import com.example.huoapp.base.baseActivity.BaseSwipeBackActivity;
import com.example.huoapp.util.HuoUtils;
import com.example.huoapp.widget.calendar.CustomDayView;
import com.example.huoapp.widget.calendar.Utils;
import com.example.huoapp.widget.calendar.component.CalendarAttr;
import com.example.huoapp.widget.calendar.component.CalendarViewAdapter;
import com.example.huoapp.widget.calendar.interf.OnSelectDateListener;
import com.example.huoapp.widget.calendar.model.CalendarDate;
import com.example.huoapp.widget.calendar.view.Calendar;
import com.example.huoapp.widget.calendar.view.MonthPager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;
import qiu.niorgai.StatusBarCompat;

/**
 * 签到
 * Created by tinle on 2017/11/6.
 */

public class SignActivity extends BaseSwipeBackActivity {
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.vp_calendar)
    MonthPager monthPager;
    @BindView(R.id.rl_top)
    RelativeLayout rlTop;

    private ArrayList<Calendar> currentCalendars = new ArrayList<>();
    private CalendarViewAdapter calendarAdapter;
    private OnSelectDateListener onSelectDateListener;
    private int mCurrentPage = MonthPager.CURRENT_DAY_INDEX;
    private CalendarDate currentDate;
    private boolean initiated = false;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


    @Override
    protected int getContentViewLayoutId() {
        StatusBarCompat.translucentStatusBar(this,true);
        return R.layout.activity_sign;
    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            int statusBarHeight = HuoUtils.getStatusBarHeight(this);
            rlTop.getLayoutParams().height = statusBarHeight + SizeUtils.dp2px(50);
            rlTop.setPadding(0,statusBarHeight,0,0);
        }

        monthPager.setViewheight(Utils.dpi2px(this,270));
        initCurrentDate();
        initCalendarView();
    }

    private void initCurrentDate() {
        currentDate = new CalendarDate();
        Date date = new Date();
        tvDate.setText(sdf.format(date));
    }

    private void initCalendarView() {
        initListener();
        CustomDayView customDayView = new CustomDayView(this, R.layout.custom_day);
        calendarAdapter = new CalendarViewAdapter(
                this,
                onSelectDateListener,
                CalendarAttr.CalendayType.MONTH,
                customDayView);
        initMonthPager();
    }

    private void initListener() {
        onSelectDateListener = new OnSelectDateListener() {
            @Override
            public void onSelectDate(CalendarDate date) {
                refreshClickDate(date);
            }

            @Override
            public void onSelectOtherMonth(int offset) {
                //偏移量 -1表示刷新成上一个月数据 ， 1表示刷新成下一个月数据
                monthPager.selectOtherMonth(offset);
            }
        };
    }

    private void initMonthPager() {
        monthPager.setAdapter(calendarAdapter);
        monthPager.setCurrentItem(MonthPager.CURRENT_DAY_INDEX);
        monthPager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                position = (float) Math.sqrt(1 - Math.abs(position));
                page.setAlpha(position);
            }
        });
        monthPager.addOnPageChangeListener(new MonthPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mCurrentPage = position;
                currentCalendars = calendarAdapter.getPagers();
                if (currentCalendars.get(position % currentCalendars.size()) instanceof Calendar) {
                    CalendarDate date = currentCalendars.get(position % currentCalendars.size()).getSeedDate();
                    currentDate = date;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    /**
     * onWindowFocusChanged回调时，将当前月的种子日期修改为今天
     *
     * @return void
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && !initiated) {
            refreshMonthPager();
            initiated = true;
        }
    }

    private void refreshMonthPager() {
        CalendarDate today = new CalendarDate();
        calendarAdapter.notifyDataChanged(today);
    }

    private void refreshClickDate(CalendarDate date) {
        currentDate = date;
    }


    @OnClick(R.id.btn_sign)
    void action(){
        monthPager.signDay(currentDate);
    }

}
