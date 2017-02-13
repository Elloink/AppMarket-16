package com.leiyun.appmarket.ui.fragment;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.leiyun.appmarket.http.protocol.HotProtocol;
import com.leiyun.appmarket.ui.view.FlowLayout;
import com.leiyun.appmarket.ui.view.LoadingPage;
import com.leiyun.appmarket.utils.DrawableUtils;
import com.leiyun.appmarket.utils.UIUtils;

import java.util.ArrayList;
import java.util.Random;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * 排行
 * Created by LeiYun on 2017/2/6 0006.
 */

public class HotFragment extends BaseFragment {

    private ArrayList<String> data;

    @Override
    public View onCreateSuccessView() {
        // 支持上下滑动
        ScrollView scrollView = new ScrollView(UIUtils.getContext());
        FlowLayout flow = new FlowLayout(UIUtils.getContext());
        int padding = UIUtils.dip2px(10);

        // 设置内边距
        flow.setPadding(padding, padding, padding, padding);

        flow.setHorizontalSpacing(UIUtils.dip2px(6));// 设置水平间距
        flow.setVerticalSpacing(UIUtils.dip2px(8)); // 设置垂直间距

        Random random = new Random();
        for (int i = 0; i < data.size(); i++) {
            final String keyword = data.get(i);
            TextView view = new TextView(UIUtils.getContext());
            view.setText(keyword);
            view.setTextColor(Color.WHITE);
            view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            view.setPadding(padding, padding, padding, padding);
            view.setGravity(Gravity.CENTER);

            // 随机颜色
            int r = random.nextInt(200) + 30;
            int g = random.nextInt(200) + 30;
            int b = random.nextInt(200) + 30;

            // 按下的颜色
            int pressColor = 0xffcecece; //按下后的颜色

            // 设置背景
            StateListDrawable selector = DrawableUtils
                    .getSelector(Color.rgb(r,g,b), pressColor, UIUtils.dip2px(6));

            view.setBackground(selector);
            flow.addView(view);

            // 设置点击事件状态选择器才起作用
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(UIUtils.getContext(),
                            keyword, Toast.LENGTH_SHORT).show();
                }
            });
        }

        scrollView.addView(flow);
        return scrollView;
    }

    @Override
    public LoadingPage.ResultState onLoad() {
        HotProtocol protocol = new HotProtocol();
        data = protocol.getData(0);
        return check(data);
    }
}
