package com.leiyun.appmarket.ui.fragment;

import android.view.View;
import android.widget.Adapter;

import com.leiyun.appmarket.domain.AppInfo;
import com.leiyun.appmarket.http.protocol.AppProtocol;
import com.leiyun.appmarket.ui.adapter.MyBaseAdapter;
import com.leiyun.appmarket.ui.holder.AppHolder;
import com.leiyun.appmarket.ui.holder.BaseHolder;
import com.leiyun.appmarket.ui.view.LoadingPage;
import com.leiyun.appmarket.ui.view.MyListView;
import com.leiyun.appmarket.utils.UIUtils;

import java.util.ArrayList;

/**
 * 应用
 * Created by LeiYun on 2017/2/6 0006.
 */

public class AppFragment extends BaseFragment {
    private ArrayList<AppInfo> data;

    // 如果加载数据成功，就回调此方法,在主线程运行，加载成功才会运行
    @Override
    public View onCreateSuccessView() {
        MyListView view = new MyListView(UIUtils.getContext());
        view.setAdapter(new AppAdapter(data));
        return view;
    }

    // 这个方法是运行在子线程中的，可以直接执行耗时的操作
    @Override
    public LoadingPage.ResultState onLoad() {
        AppProtocol protocol = new AppProtocol();
        data = protocol.getData(0);
        return check(data);
    }

    class AppAdapter extends MyBaseAdapter<AppInfo>{

        public AppAdapter(ArrayList<AppInfo> data) {
            super(data);
        }

        @Override
        public BaseHolder<AppInfo> getHolder() {

            return new AppHolder();
        }

        @Override
        public ArrayList<AppInfo> onLoadMore() {
            AppProtocol protocol = new AppProtocol();
            ArrayList<AppInfo> moreData = protocol.getData(getListSize());
            return moreData;
        }
    }
}
