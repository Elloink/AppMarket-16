package com.leiyun.appmarket.ui.holder;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.leiyun.appmarket.R;
import com.leiyun.appmarket.domain.SubjectInfo;
import com.leiyun.appmarket.http.HttpHelper;
import com.leiyun.appmarket.utils.BitmapHelper;
import com.leiyun.appmarket.utils.UIUtils;
import com.lidroid.xutils.BitmapUtils;

/**
 * 专题holder
 * Created by LeiYun on 2017/2/7 0007.
 */

public class SubjectHolder extends BaseHolder<SubjectInfo> {

    private TextView tvTitle;
    private ImageView ivPic;
    private BitmapUtils mBitmapUtils;

    @Override
    public View initView() {
        // 1. 加载布局文件
        View view = UIUtils.inflate(R.layout.list_item_subject);
        // 2. findViewById：找到布局中的控件（初始化控件）
        ivPic = (ImageView) view.findViewById(R.id.iv_pic);
        tvTitle = (TextView) view.findViewById(R.id.tv_title);

        mBitmapUtils = BitmapHelper.getBitmapUtils();
        return view;
    }

    /**
     * 4. 根据数据来刷新界面
     * @param data
     */
    @Override
    public void refreshView(SubjectInfo data) {
        tvTitle.setText(data.des);
        mBitmapUtils.display(ivPic, HttpHelper.URL + "image?name=" + data.url);
    }
}
