package com.example.huoapp.widget.calendar.view;


import com.example.huoapp.widget.calendar.Const;

/**
 * Created by ldf on 17/6/27.
 */

public class Week {
    public int row;
    public Day[] days = new Day[Const.TOTAL_COL];

    public Week(int row) {
        this.row = row;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public Day[] getDays() {
        return days;
    }

    public void setDays(Day[] days) {
        this.days = days;
    }
}
