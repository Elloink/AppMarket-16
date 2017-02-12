package com.leiyun.appmarket.ui.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.widget.ListView;

import com.leiyun.appmarket.ui.fragment.HomeFragment;

/**
 * Created by LeiYun on 2017/2/12 0012.
 */

public class MyListView extends ListView {

    public MyListView(Context context) {
        super(context);
        initView();
    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public MyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        this.setSelector(new ColorDrawable());// 设置默认状态选择器为全透明
        this.setDivider(null); // 去掉分割线
        this.setCacheColorHint(Color.TRANSPARENT); // 有时候滑动ListView背景会变成黑色，此方法可以将它透明化
    }
}
