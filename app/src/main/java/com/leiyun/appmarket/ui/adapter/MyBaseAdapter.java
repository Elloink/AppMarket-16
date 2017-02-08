package com.leiyun.appmarket.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.leiyun.appmarket.ui.holder.BaseHolder;
import com.leiyun.appmarket.ui.holder.MoreHolder;

import java.util.ArrayList;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * 对adapter的封装
 * Created by LeiYun on 2017/2/7 0007.
 */

public abstract class MyBaseAdapter<T> extends BaseAdapter {

    private static final int TYPE_NORMAl = 0; // 普通类型
    private static final int TYPE_MORE = 1;   // 加载更多类型

    private ArrayList<T> data;

    public MyBaseAdapter(ArrayList<T> data) {
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size() + 1; // 增加加载更多布局数量
    }

    @Override
    public T getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    // 返回布局类型个数
    @Override
    public int getViewTypeCount() {
        return 2; // 返回两种类型，普通布局 + 加载更多布局
    }

    // 返回当前位置应该展示那种布局类型
    @Override
    public int getItemViewType(int position) {
        if (position == getCount() - 1) {// 最后一个普通布局
            return TYPE_MORE;
        } else {
            return TYPE_NORMAl;
        }
    }

    // 子类可以重写此方法来更改返回的布局类型
    public int getInnerType() {
        return TYPE_NORMAl; // 默认就是普通类型
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        BaseHolder holder;
        if (view == null) {
            // 1. 加载布局文件
            // 2. findViewById：找到布局中的控件（初始化控件）
            // 3. 打一个标记tag
            if (getItemViewType(i) == TYPE_MORE) {
                // 判断是否是加载更多的类型
                holder = new MoreHolder();
            } else {
                holder = getHolder(); // 子类返回具体对象
            }
        } else {
            holder = (BaseHolder) view.getTag();
        }

        // 4. 根据数据来刷新界面
        if (getItemViewType(i) != TYPE_MORE) {
            holder.refreshView(getItem(i));
        } else {
            // 加载更多布局
        }
        return holder.getRootView();
    }

    // 返回当前页面的holder对象，必须由子类实现
    public abstract BaseHolder getHolder();
}
