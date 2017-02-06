package com.leiyun.appmarket.global;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

/**
 * Created by LeiYun on 2017/1/4 0004.
 * 自定义application,进行全局初始化
 */

public class GooglePlayApplication extends Application {
    private static Context sContext;
    private static Handler sHandler;
    private static int sMainThreadId;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
        sHandler = new Handler();

        sMainThreadId = android.os.Process.myTid();//当前线程id是主线程id;

    }

    public static Context getContext() {
        return sContext;
    }

    public static Handler getHandler() {
        return sHandler;
    }

    public static int getMainThreadId() {
        return sMainThreadId;
    }
}
