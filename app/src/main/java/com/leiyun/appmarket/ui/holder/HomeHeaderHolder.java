package com.leiyun.appmarket.ui.holder;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.leiyun.appmarket.http.HttpHelper;
import com.leiyun.appmarket.utils.BitmapHelper;
import com.leiyun.appmarket.utils.LogUtils;
import com.leiyun.appmarket.utils.UIUtils;
import com.lidroid.xutils.BitmapUtils;

import java.util.ArrayList;

/**
 * 首页轮播条holder
 * Created by LeiYun on 2017/2/14 0014.
 */

public class HomeHeaderHolder extends BaseHolder<ArrayList<String>> {

    private ViewPager mViewPager;
    private ArrayList<String> data;

    @Override
    public View initView() {
        // 创建根布局，相对布局
        RelativeLayout rlRoot = new RelativeLayout(UIUtils.getContext());

        // 初始化布局参数，根布局上层控件是listview，所以要使用listview定义的LayoutParams
        // Tip:在这里ListView他不能使用LayoutParams的不过他的父类AbsListView可以
        AbsListView.LayoutParams params = new AbsListView.LayoutParams(
                AbsListView.LayoutParams.MATCH_PARENT,
                UIUtils.dip2px(150)
        );
        rlRoot.setLayoutParams(params);

        // ViewPage
        mViewPager = new ViewPager(UIUtils.getContext());
        RelativeLayout.LayoutParams vpParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
        );
        // addView方法的第二个参数是LayoutParams的话，子View就不用单独的去设置LayoutParams了
        rlRoot.addView(mViewPager, vpParams); // 把ViewPage添加个相对布局

        return rlRoot;
    }

    @Override
    public void refreshView(ArrayList<String> data) {
        this.data = data;
        // 填充viewpage的数据
        mViewPager.setAdapter(new HomeHeaderAdapter());

    }

    class HomeHeaderAdapter extends PagerAdapter {

        private final BitmapUtils mBitmapUtils;

        public HomeHeaderAdapter() {
            mBitmapUtils = BitmapHelper.getBitmapUtils();
        }


        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            String url = data.get(position);

            LogUtils.e(url);

            ImageView view = new ImageView(UIUtils.getContext());
            view.setScaleType(ImageView.ScaleType.FIT_XY);
            mBitmapUtils.display(view, HttpHelper.URL + "image?name=" + url);

            container.addView(view);

            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View)object);
        }
    }

}
