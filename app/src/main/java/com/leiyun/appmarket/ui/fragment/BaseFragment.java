package com.leiyun.appmarket.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.leiyun.appmarket.utils.UIUtils;

/**
 * Created by LeiYun on 2017/2/6 0006.
 */

public class BaseFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView view = new TextView(UIUtils.getContext());
        view.setTextColor(Color.GREEN);
        view.setText(getClass().getSimpleName());
        return view;
    }
}
