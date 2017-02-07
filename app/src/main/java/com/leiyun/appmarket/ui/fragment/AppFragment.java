package com.leiyun.appmarket.ui.fragment;

import android.view.View;

import com.leiyun.appmarket.ui.view.LoadingPage;

/**
 * 应用
 * Created by LeiYun on 2017/2/6 0006.
 */

public class AppFragment extends BaseFragment {
    // 如果加载数据成功，就回调此方法,在主线程运行，加载成功才会运行
    @Override
    public View onCreateSuccessView() {
        return null;
    }

    // 这个方法是运行在子线程中的，可以直接执行耗时的操作
    @Override
    public LoadingPage.ResultState onLoad() {
        return LoadingPage.ResultState.STATE_ERROR;
    }
}
