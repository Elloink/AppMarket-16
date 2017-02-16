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
 * 详情页-应用信息
 * Created by LeiYun on 2017/2/16 0016.
 */

public class DetailAppInfoHolder extends BaseHolder<AppInfo> {

    private ImageView ivIcon;
    private TextView tvName;
    private TextView tvDownloadNum;
    private TextView tvVersion;
    private TextView tvData;
    private TextView tvSize;
    private RatingBar rbStar;
    private BitmapUtils mBitmapUtils;

    @Override
    public View initView() {
        View view = UIUtils.inflate(R.layout.layout_detail_appinfo);

        ivIcon = (ImageView) view.findViewById(R.id.iv_2_icon);
        tvName = (TextView) view.findViewById(R.id.tv_2_name);
        tvDownloadNum = (TextView) view.findViewById(R.id.tv_2_download_num);
        tvVersion = (TextView) view.findViewById(R.id.tv_2_version);
        tvData = (TextView) view.findViewById(R.id.tv_2_date);
        tvSize = (TextView) view.findViewById(R.id.tv_2_size);
        rbStar = (RatingBar) view.findViewById(R.id.rb_2_star);

        mBitmapUtils = new BitmapUtils(UIUtils.getContext());

        return view;
    }

    @Override
    public void refreshView(AppInfo data) {
        mBitmapUtils.display(ivIcon, HttpHelper.URL + "image?name=" + data.iconUrl);
        tvName.setText(data.name);
        tvDownloadNum.setText("下载量：" + data.downloadNum);
        tvVersion.setText("版本号：" + data.version);
        tvData.setText(data.date);
        tvSize.setText(Formatter.formatFileSize(UIUtils.getContext(), data.size));
        rbStar.setRating(data.stars);
    }
}
