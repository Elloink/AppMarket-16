package com.leiyun.appmarket.ui.fragment;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.leiyun.appmarket.ui.view.LoadingPage;
import com.leiyun.appmarket.ui.view.LoadingPage.ResultState;
import com.leiyun.appmarket.utils.UIUtils;

/**
 * 首页
 * Created by LeiYun on 2017/2/6 0006.
 */

public class HomeFragment extends BaseFragment {
    // 如果加载数据成功，就回调此方法,在主线程运行，加载成功才会运行
    @Override
    public View onCreateSuccessView() {
        TextView view = new TextView(UIUtils.getContext());
        view.setText(getClass().getSimpleName());
        view.setTextColor(Color.BLUE);
        return view;
    }

    // 这个方法是运行在子线程中的，可以直接执行耗时的操作
    @Override
    public ResultState onLoad() {
        return ResultState.STATE_SUCCESS;
    }
}
