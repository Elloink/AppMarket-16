package com.leiyun.appmarket.ui.fragment;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.leiyun.appmarket.R;
import com.leiyun.appmarket.domain.SubjectInfo;
import com.leiyun.appmarket.http.protocol.SubjectProtocol;
import com.leiyun.appmarket.ui.adapter.MyBaseAdapter;
import com.leiyun.appmarket.ui.holder.BaseHolder;
import com.leiyun.appmarket.ui.holder.SubjectHolder;
import com.leiyun.appmarket.ui.view.LoadingPage;
import com.leiyun.appmarket.ui.view.MyListView;
import com.leiyun.appmarket.utils.UIUtils;

import java.util.ArrayList;

import static com.leiyun.appmarket.ui.view.LoadingPage.ResultState.STATE_SUCCESS;

/**
 * 专题页面
 * Created by LeiYun on 2017/2/6 0006.
 */

public class SubjectFragment extends BaseFragment {


    private ArrayList<SubjectInfo> data;

    @Override
    public View onCreateSuccessView() {
        MyListView myListView = new MyListView(UIUtils.getContext());
        myListView.setAdapter(new SubjectAdapter(data));
        return myListView;
    }

    @Override
    public LoadingPage.ResultState onLoad() {
        SubjectProtocol protocol = new SubjectProtocol();
        data = protocol.getData(0);
        return check(data);
    }

    class SubjectAdapter extends MyBaseAdapter<SubjectInfo> {

        public SubjectAdapter(ArrayList<SubjectInfo> data) {
            super(data);
        }

        @Override
        public BaseHolder getHolder() {
            return new SubjectHolder();
        }

        @Override
        public ArrayList<SubjectInfo> onLoadMore() {
            SubjectProtocol protocol = new SubjectProtocol();
            ArrayList<SubjectInfo> moreData = protocol.getData(getListSize());
            return moreData;
        }
    }

}
