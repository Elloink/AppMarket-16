package com.leiyun.appmarket.ui.holder;

import android.view.View;

import com.leiyun.appmarket.R;
import com.leiyun.appmarket.utils.UIUtils;

/**
 * Created by LeiYun on 2017/2/8 0008.
 */

public class MoreHolder extends BaseHolder<Integer> {

    @Override
    public View initView() {
        View view = UIUtils.inflate(R.layout.list_item_more);
        return view;
    }

    @Override
    public void refreshView(Integer data) {

    }
}
