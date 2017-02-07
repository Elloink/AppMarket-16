package com.leiyun.appmarket.ui.fragment;

import android.view.View;

import com.leiyun.appmarket.ui.view.LoadingPage;

/**
 * 游戏
 * Created by LeiYun on 2017/2/6 0006.
 */

public class GameFragment extends BaseFragment {
    @Override
    public View onCreateSuccessView() {
        return null;
    }

    @Override
    public LoadingPage.ResultState onLoad() {
        return LoadingPage.ResultState.STATE_EMPTY;
    }
}
