package com.leiyun.appmarket.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.leiyun.appmarket.global.GooglePlayApplication;

/**
 * Created by LeiYun on 2017/1/4 0004.
 */

public class UIUtils {
    public static Context getContext() {
        return GooglePlayApplication.getContext();
    }

    public static Handler getHandler() {
        return GooglePlayApplication.getHandler();
    }

    public static int getMainThreadId() {
        return GooglePlayApplication.getMainThreadId();
    }

    // 获取字符串
    public static String getString(int id) {
        return getContext().getResources().getString(id);
    }

    // 获取字符串数组
    public static String[] getStringArray(int id) {
        return getContext().getResources().getStringArray(id);
    }

    // 获取图片
    public static Drawable getDrawable(int id) {
        return ContextCompat.getDrawable(getContext(), id);
    }

    // 获取颜色
    public static int getColor(int id) {
        return ContextCompat.getColor(getContext(), id);
    }

    // 根据id获取颜色的状态选择器
    public static ColorStateList getColorStateList(int tabTextColorResId) {
        return getContext().getResources().getColorStateList(tabTextColorResId);
    }

    // 获取尺寸
    public static int getDimen(int id) {
        return getContext().getResources().getDimensionPixelSize(id);
    }

    // dip 和 px 之间的转换
    public static int dip2px(float dip) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (dip * density + 0.5f);
    }

    public static float px2dip(int px) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return px / density;
    }

    // 加载布局文件
    public static View inflate(int id) {
        return View.inflate(getContext(), id, null);
    }

    // 判断是否运行在主线程
    public static boolean isRunOnUIThread() {
        // 获取当前线程id，如果当前线程id等于主线程，那么就是主线程。
        int myId = android.os.Process.myTid();
        return myId == getMainThreadId();
    }

    // 运行在主线程
    public static void runOnUIThread(Runnable r) {
        if (isRunOnUIThread()) {
            // 已经是主线程，直接运行
            r.run();
        } else {
            // 如果是子线程，借助handler让其运行在主线程
            getHandler().post(r);
        }
    }


}
