package com.leiyun.appmarket.ui.holder;

import android.view.View;
import android.widget.ImageView;

import com.leiyun.appmarket.R;
import com.leiyun.appmarket.domain.AppInfo;
import com.leiyun.appmarket.http.HttpHelper;
import com.leiyun.appmarket.utils.BitmapHelper;
import com.leiyun.appmarket.utils.UIUtils;
import com.lidroid.xutils.BitmapUtils;

import java.util.ArrayList;

/**
 * Created by LeiYun on 2017/2/17 0017.
 */

public class DetailPicsHolder extends BaseHolder<AppInfo> {
    private ImageView[] ivPics;
    private BitmapUtils mBitmapUtils;

    @Override
    public View initView() {
        View view = UIUtils.inflate(R.layout.layout_detail_picinfo);

        ivPics = new ImageView[5];
        ivPics[0] = (ImageView) view.findViewById(R.id.iv_pic1);
        ivPics[1] = (ImageView) view.findViewById(R.id.iv_pic2);
        ivPics[2] = (ImageView) view.findViewById(R.id.iv_pic3);
        ivPics[3] = (ImageView) view.findViewById(R.id.iv_pic4);
        ivPics[4] = (ImageView) view.findViewById(R.id.iv_pic5);

        mBitmapUtils = BitmapHelper.getBitmapUtils();

        return view;
    }

    @Override
    public void refreshView(AppInfo data) {
        ArrayList<String> screen = data.screen;
        for (int i = 0; i < 5; i++) {
            if (i < screen.size()) {
                mBitmapUtils.display(ivPics[i],
                        HttpHelper.URL + "image?name=" + screen.get(i));
            } else {
                ivPics[i].setVisibility(View.GONE);
            }
        }
    }
}
