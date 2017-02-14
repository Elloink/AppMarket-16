package com.leiyun.appmarket.ui.holder;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.leiyun.appmarket.R;
import com.leiyun.appmarket.domain.CategoryInfo;
import com.leiyun.appmarket.utils.UIUtils;

/**
 * 分类模块标题Holder
 * Created by LeiYun on 2017/2/14 0014.
 */

public class TitleHolder extends BaseHolder<CategoryInfo> {

    private TextView tvTitle;

    @Override
    public View initView() {
        View view = UIUtils.inflate(R.layout.list_item_title);
        tvTitle = (TextView) view.findViewById(R.id.tv_title);
        return view;
    }

    @Override
    public void refreshView(CategoryInfo data) {
        tvTitle.setText(data.title);
    }
}
