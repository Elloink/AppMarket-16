package com.leiyun.appmarket.ui.fragment;

import android.view.View;

import com.leiyun.appmarket.domain.CategoryInfo;
import com.leiyun.appmarket.http.protocol.CategoryProtocol;
import com.leiyun.appmarket.ui.adapter.MyBaseAdapter;
import com.leiyun.appmarket.ui.holder.BaseHolder;
import com.leiyun.appmarket.ui.holder.CategoryHolder;
import com.leiyun.appmarket.ui.holder.TitleHolder;
import com.leiyun.appmarket.ui.view.LoadingPage;
import com.leiyun.appmarket.ui.view.MyListView;
import com.leiyun.appmarket.utils.UIUtils;

import java.util.ArrayList;

/**
 * 分类
 * Created by LeiYun on 2017/2/6 0006.
 */

public class CategoryFragment extends BaseFragment {

    private ArrayList<CategoryInfo> data;

    @Override
    public View onCreateSuccessView() {
        MyListView listView = new MyListView(UIUtils.getContext());
        listView.setAdapter(new CategoryAdapter(data));
        return listView;
    }

    @Override
    public LoadingPage.ResultState onLoad() {
        CategoryProtocol protocol = new CategoryProtocol();
        data = protocol.getData(0);
        return check(data);
    }

    class CategoryAdapter extends MyBaseAdapter<CategoryInfo> {

        public CategoryAdapter(ArrayList<CategoryInfo> data) {
            super(data);
        }

        @Override
        public int getViewTypeCount() {
            return super.getViewTypeCount() + 1; // 在原来的基础上加1，这多出来的布局是标题的布局
        }

        @Override
        public int getInnerType(int position) {
            // 判断是标题类型还是普通分类类型
            CategoryInfo info = data.get(position);

            if (info.isTitle) {
                // 返回标题类型
                return super.getInnerType(position) + 1; // 原来类型的基础上加1，注意：将TYPE_NORMAL修改为1
            } else {
                // 返回普通类型
                return super.getInnerType(position);
            }
        }

        @Override
        public BaseHolder<CategoryInfo> getHolder(int position) {
            // 判断是标题类型还是普通分类类型, 来返回不同的holder
            CategoryInfo info = data.get(position);

            if (info.isTitle) {
                return new TitleHolder();
            } else {
                CategoryHolder categoryHolder = new CategoryHolder();
                categoryHolder.setData(info);
                return categoryHolder;
            }
        }

        @Override
        public boolean hasMore() {
            return false; // 没有更多数据，需要隐藏加载更多的布局
        }

        @Override
        public ArrayList<CategoryInfo> onLoadMore() {
            return null;
        }
    }

}
