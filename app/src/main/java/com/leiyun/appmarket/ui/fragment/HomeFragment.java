package com.leiyun.appmarket.ui.fragment;

import android.graphics.Color;
import android.os.SystemClock;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.leiyun.appmarket.R;
import com.leiyun.appmarket.ui.adapter.MyBaseAdapter;
import com.leiyun.appmarket.ui.holder.BaseHolder;
import com.leiyun.appmarket.ui.holder.HomeHolder;
import com.leiyun.appmarket.ui.view.LoadingPage;
import com.leiyun.appmarket.ui.view.LoadingPage.ResultState;
import com.leiyun.appmarket.utils.UIUtils;

import java.util.ArrayList;

/**
 * 首页
 * Created by LeiYun on 2017/2/6 0006.
 */

public class HomeFragment extends BaseFragment {

    private ArrayList<String> data;

    // 如果加载数据成功，就回调此方法,在主线程运行，加载成功才会运行
    @Override
    public View onCreateSuccessView() {
//        TextView view = new TextView(UIUtils.getContext());
//        view.setText(getClass().getSimpleName());
//        view.setTextColor(Color.BLUE);
        ListView view = new ListView(UIUtils.getContext());
        view.setAdapter(new HomeAdapter(data));
        return view;
    }

    // 这个方法是运行在子线程中的，可以直接执行耗时的操作
    @Override
    public ResultState onLoad() {

        data = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            data.add("测试数据" + i);
        }

        return ResultState.STATE_SUCCESS;
    }

    //
    class HomeAdapter extends MyBaseAdapter<String> {


        public HomeAdapter(ArrayList<String> data) {
            super(data);
        }

        @Override
        public BaseHolder getHolder() {
            return new HomeHolder();
        }

        // 此方法在子线程调用
        @Override
        public ArrayList<String> onLoadMore() {
            ArrayList<String> moreData = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                moreData.add("测试更多数据" + i);
            }

            SystemClock.sleep(2000);
            return moreData;
        }

        //-------------------------------------------------

//        @Override
//        public View getView(int i, View view, ViewGroup viewGroup) {
//            ViewHolder holder;
//            if (view == null) {
//                // 1. 加载布局文件
//                view = UIUtils.inflate(R.layout.list_item_home);
//                holder = new ViewHolder();
//                // 2. findViewById：找到布局中的控件（初始化控件）
//                holder.tvContent = (TextView) view.findViewById(R.id.tv_content);
//
//                // 3. 打一个标记tag
//                view.setTag(holder);
//            } else {
//                holder = (ViewHolder) view.getTag();
//            }
//
//            // 4. 根据数据来刷新界面
//            String content = getItem(i);
//            holder.tvContent.setText(content);
//            return view;
//        }
        //-----------------------------------------------


        @Override
        public boolean hasMore() {
            return true;
        }
    }

//    static class ViewHolder {
//        public TextView tvContent;
//
//    }


}
