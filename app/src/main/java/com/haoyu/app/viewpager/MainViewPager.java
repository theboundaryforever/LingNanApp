package com.haoyu.app.viewpager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class MainViewPager extends ViewPager {
    private boolean noScroll = true;

    public MainViewPager(Context context) {
        super(context);
    }

    public MainViewPager(Context context, AttributeSet attr) {
        super(context, attr);
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (this.noScroll) {
            return false;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @SuppressLint({"ClickableViewAccessibility"})
    public boolean onTouchEvent(MotionEvent ev) {
        if (this.noScroll) {
            return false;
        }
        return super.onTouchEvent(ev);
    }

    public void scrollTo(int x, int y) {
        super.scrollTo(x, y);
    }

    public void setCurrentItem(int position) {
        super.setCurrentItem(position);
    }

    public void setCurrentItem(int position, boolean flag) {
        super.setCurrentItem(position, flag);
    }

    public void setNoScroll(boolean flag) {
        this.noScroll = flag;
    }
}
