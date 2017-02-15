package com.leiyun.appmarket.ui.holder;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.leiyun.appmarket.R;
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
    private LinearLayout llContainer;
    private int mPreviousPos = 0;// 上个圆点位置

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

        // 初始化指示器
        llContainer = new LinearLayout(UIUtils.getContext());
        llContainer.setOrientation(LinearLayout.HORIZONTAL); // 水平方向
        RelativeLayout.LayoutParams llParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );

        // 设置内边距
        int padding = UIUtils.dip2px(10);
        llContainer.setPadding(padding, padding, padding, padding);

        // 添加规则进行定位
        llParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM); // 定位到底部
        llParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);  // 定位到右边，和上面一起用就是定位到右下角

        rlRoot.addView(llContainer, llParams);

        return rlRoot;
    }

    @Override
    public void refreshView(final ArrayList<String> data) {
        this.data = data;
        // 填充viewpage的数据
        mViewPager.setAdapter(new HomeHeaderAdapter());
        mViewPager.setCurrentItem(data.size() * 10000); // 初始化第一张

        // 初始化指示器
        for (int i = 0; i < data.size(); i++) {
            ImageView point = new ImageView(UIUtils.getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            if (i == 0) { // 第一个默认选择
                point.setImageResource(R.drawable.indicator_selected);
            } else {
                point.setImageResource(R.drawable.indicator_normal);
                params.leftMargin = UIUtils.dip2px(6);

            }
            llContainer.addView(point,params);
        }

        // 对ViewPage设置监听事件
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                position %= data.size();

                // 上个点变为不选中
                ImageView prePoint = (ImageView) llContainer.getChildAt(mPreviousPos);
                prePoint.setImageResource(R.drawable.indicator_normal);

                // 当前点被选中
                ImageView point = (ImageView) llContainer.getChildAt(position);
                point.setImageResource(R.drawable.indicator_selected);

                // 更新上一个点的位置信息
                mPreviousPos = position;
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        // 启动轮播条自动播放
        HomeHeaderTask task = new HomeHeaderTask();
        task.start();
    }

    class HomeHeaderTask implements Runnable {

        public void start() {
            // 移除所有消息（先移除以前的消息，避免消息重复发送）
            UIUtils.getHandler().removeCallbacksAndMessages(null);

            UIUtils.getHandler().postDelayed(this, 3000);
        }

        @Override
        public void run() {
            int currentItem = mViewPager.getCurrentItem();
            currentItem++;
            mViewPager.setCurrentItem(currentItem);

            // 继续发延时3秒消息，实现内循环
            UIUtils.getHandler().postDelayed(this, 3000);
        }
    }

    class HomeHeaderAdapter extends PagerAdapter {

        private final BitmapUtils mBitmapUtils;

        public HomeHeaderAdapter() {
            mBitmapUtils = BitmapHelper.getBitmapUtils();
        }


        @Override
        public int getCount() {
//            return data.size();
            return Integer.MAX_VALUE; //
        }


        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            position %= data.size();
            String url = data.get(position);

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
