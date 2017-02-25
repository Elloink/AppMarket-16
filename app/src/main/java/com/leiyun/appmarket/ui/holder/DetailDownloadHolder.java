package com.leiyun.appmarket.ui.holder;

import android.view.View;

import com.leiyun.appmarket.R;
import com.leiyun.appmarket.domain.AppInfo;
import com.leiyun.appmarket.utils.UIUtils;

/**
 * 详情页-下载模块
 * Created by LeiYun on 2017/2/25 0025.
 */

public class DetailDownloadHolder extends BaseHolder<AppInfo> {
    @Override
    public View initView() {
        View view = UIUtils.inflate(R.layout.layout_detail_download);

        return view;
    }

    @Override
    public void refreshView(AppInfo data) {

    }
}
