package com.leiyun.appmarket.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.leiyun.appmarket.manager.ThreadManager;
import com.leiyun.appmarket.ui.holder.BaseHolder;
import com.leiyun.appmarket.ui.holder.MoreHolder;
import com.leiyun.appmarket.utils.UIUtils;

import java.util.ArrayList;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static android.os.Build.VERSION_CODES.M;

/**
 * 对adapter的封装
 * Created by LeiYun on 2017/2/7 0007.
 */

public abstract class MyBaseAdapter<T> extends BaseAdapter {

    // 注意：此处必须从0开始写，因为底层数据有可能是数组，而数组的起始位就是0
    private static final int TYPE_NORMAl = 1; // 普通类型
    private static final int TYPE_MORE = 0;   // 加载更多类型

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
                return getInnerType(position);
        }
    }

    // 子类可以重写此方法来更改返回的布局类型
    public int getInnerType(int position) {
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
                holder = new MoreHolder(hasMore());
            } else {
                holder = getHolder(i); // 子类返回具体对象
            }
        } else {
            holder = (BaseHolder) view.getTag();
        }

        // 4. 根据数据来刷新界面
        if (getItemViewType(i) != TYPE_MORE) {
            holder.refreshView(getItem(i));
        } else {
            // 加载更多布局
            MoreHolder moreHolder = (MoreHolder)holder;
            // 一旦加载更多布局展示出来，就开始执行加载更多的方法
            //只有在有更多数据的状态下才加载更多
            if (moreHolder.getData() == MoreHolder.STATE_MORE_MORE) {
                loadMore(moreHolder);
            }
        }
        return holder.getRootView();
    }

    // 子类可以重写此方法来决定是否可以加载更多
    public boolean hasMore() {
        return true; // 默认都是有更多数据的
    }

    // 返回当前页面的holder对象，必须由子类实现
    public abstract BaseHolder getHolder(int position);

    private boolean isLoadMore = false; // 标记是否加载更多

    // 加载更多数据的处理
    public void loadMore(final MoreHolder holder) {
        if (!isLoadMore) {
            isLoadMore = true;

//            new Thread() {
//                @Override
//                public void run() {
//
//                }
//            }.start();

            ThreadManager.getThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    final ArrayList<T> moreData = onLoadMore();
                    UIUtils.runOnUIThread(new Runnable() {
                        @Override
                        public void run() {
                            if (moreData != null) {

                                // 每一页有20条数据，如果返回的数据小于20条，就认为到了最后一页
                                if (moreData.size() < 20) {
                                    holder.setData(MoreHolder.STATE_MORE_NONE);
                                    Toast.makeText(UIUtils.getContext(),
                                            "没有更多数据了", Toast.LENGTH_SHORT).show();
                                } else {
                                    // 还有更多数据
                                    holder.setData(MoreHolder.STATE_MORE_MORE);

                                }

                                // 将更多数据追加到当前集合中
                                data.addAll(moreData);

                                // 刷新界面
                                MyBaseAdapter.this.notifyDataSetChanged();
                            } else {
                                // 加载更多失败
                                holder.setData(MoreHolder.STATE_MORE_ERROR);
                            }
                            isLoadMore = false;
                        }
                    });
                }
            });
        }
    }

    // 加载更多数据必须由子类实现
    public abstract ArrayList<T> onLoadMore();

    // 获取当前集合大小
    public int getListSize() {
        return data.size();
    }
}
