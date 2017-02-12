package com.leiyun.appmarket.ui.holder;

import android.view.View;
import android.widget.TextView;

import com.leiyun.appmarket.R;
import com.leiyun.appmarket.domain.AppInfo;
import com.leiyun.appmarket.utils.UIUtils;

/**
 * Created by LeiYun on 2017/2/7 0007.
 */

public class HomeHolder extends BaseHolder<AppInfo> {

    private TextView tvContent;

    @Override
    public View initView() {
        // 1. 加载布局文件
        View view = UIUtils.inflate(R.layout.list_item_home);
        // 2. findViewById：找到布局中的控件（初始化控件）
        tvContent = (TextView) view.findViewById(R.id.tv_name);
        return view;
    }

    /**
     * 4. 根据数据来刷新界面
     * @param data
     */
    @Override
    public void refreshView(AppInfo data) {
        tvContent.setText(data.name);
    }
}
