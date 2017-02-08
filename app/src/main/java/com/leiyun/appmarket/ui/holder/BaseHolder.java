package com.leiyun.appmarket.ui.holder;

import android.view.View;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * 对adapter中的getView方法里的操作进行封装
 * Created by LeiYun on 2017/2/7 0007.
 */

public abstract class BaseHolder<T> {

    private final View mRootView; // 一个item的根布局
    private T data;

    // 当new这个对象时，就会加载布局，初始化控件，设置tag
    public BaseHolder() {
        mRootView = initView();
        // 3. 打一个标记tag
        mRootView.setTag(this);
    }

    /**
     * 1. 加载布局文件
     * 2. 初始化控件
     * @return 返回各自定义界面
     */
    public abstract View initView();

    // 返回item的布局
    public View getRootView() {
        return mRootView;
    }

    // 设置当前item的数据
    public void setData(T data) {
        this.data = data;
        refreshView(data);
    }

    // 获取当前item的数据
    public T getData() {
        return data;
    }

    /**
     * 4. 根据数据来刷新界面
     * @param data
     */
    public abstract void refreshView(T data);
}
