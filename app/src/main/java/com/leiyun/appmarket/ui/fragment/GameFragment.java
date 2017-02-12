package com.leiyun.appmarket.ui.fragment;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.leiyun.appmarket.R;
import com.leiyun.appmarket.ui.view.LoadingPage;
import com.leiyun.appmarket.utils.UIUtils;

/**
 * 游戏
 * Created by LeiYun on 2017/2/6 0006.
 */

public class GameFragment extends BaseFragment {
    @Override
    public View onCreateSuccessView() {
        TextView view = new TextView(UIUtils.getContext());
        view.setText(getClass().getSimpleName());
        view.setTextColor(Color.BLUE);
        return view;
    }

    @Override
    public LoadingPage.ResultState onLoad() {
        return LoadingPage.ResultState.STATE_SUCCESS;
    }
}
