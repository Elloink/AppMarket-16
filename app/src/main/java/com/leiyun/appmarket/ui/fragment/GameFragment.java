package com.leiyun.appmarket.ui.fragment;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.leiyun.appmarket.R;
import com.leiyun.appmarket.domain.AppInfo;
import com.leiyun.appmarket.http.protocol.GameProtocol;
import com.leiyun.appmarket.ui.adapter.MyBaseAdapter;
import com.leiyun.appmarket.ui.holder.BaseHolder;
import com.leiyun.appmarket.ui.holder.GameHolder;
import com.leiyun.appmarket.ui.view.LoadingPage;
import com.leiyun.appmarket.ui.view.MyListView;
import com.leiyun.appmarket.utils.UIUtils;

import java.util.ArrayList;

/**
 * 游戏
 * Created by LeiYun on 2017/2/6 0006.
 */

public class GameFragment extends BaseFragment {

    private ArrayList<AppInfo> data;

    @Override
    public View onCreateSuccessView() {
        MyListView view = new MyListView(UIUtils.getContext());
        view.setAdapter(new GameAdapter(data));
        return view;
    }

    @Override
    public LoadingPage.ResultState onLoad() {
        GameProtocol protocol = new GameProtocol();
        data = protocol.getData(0);
        return check(data);
    }

    class GameAdapter extends MyBaseAdapter<AppInfo> {
        public GameAdapter(ArrayList<AppInfo> data) {
            super(data);
        }

        @Override
        public BaseHolder getHolder(int positon) {
            return new GameHolder();
        }

        @Override
        public ArrayList<AppInfo> onLoadMore() {
            GameProtocol protocol = new GameProtocol();
            ArrayList<AppInfo> moreData = protocol.getData(getListSize());
            return moreData;
        }
    }

}
