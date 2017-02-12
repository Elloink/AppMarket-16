package com.leiyun.appmarket.ui.holder;

import android.text.format.Formatter;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.leiyun.appmarket.R;
import com.leiyun.appmarket.domain.AppInfo;
import com.leiyun.appmarket.http.HttpHelper;
import com.leiyun.appmarket.utils.BitmapHelper;
import com.leiyun.appmarket.utils.UIUtils;
import com.lidroid.xutils.BitmapUtils;

/**
 * 应用holder
 * Created by LeiYun on 2017/2/7 0007.
 */

public class AppHolder extends BaseHolder<AppInfo> {

    private TextView tvName;
    private TextView tvSize;
    private TextView tvDes;
    private ImageView ivIcon;
    private RatingBar rbStart;
    private BitmapUtils mBitmapUtils;

    @Override
    public View initView() {
        // 1. 加载布局文件
        View view = UIUtils.inflate(R.layout.list_item_home);
        // 2. findViewById：找到布局中的控件（初始化控件）
        tvName = (TextView) view.findViewById(R.id.tv_name);
        tvSize = (TextView) view.findViewById(R.id.tv_size);
        tvDes = (TextView) view.findViewById(R.id.tv_des);
        ivIcon = (ImageView) view.findViewById(R.id.iv_icon);
        rbStart = (RatingBar) view.findViewById(R.id.rb_start);

        mBitmapUtils = BitmapHelper.getBitmapUtils();
        return view;
    }

    /**
     * 4. 根据数据来刷新界面
     * @param data
     */
    @Override
    public void refreshView(AppInfo data) {
        tvName.setText(data.name);
        tvSize.setText(Formatter.formatFileSize(UIUtils.getContext(), data.size));
        tvDes.setText(data.des);
        rbStart.setRating(data.stars);

        mBitmapUtils.display(ivIcon, HttpHelper.URL + "image?name=" + data.iconUrl);
    }
}
