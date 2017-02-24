package com.leiyun.appmarket.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;

import com.leiyun.appmarket.R;
import com.leiyun.appmarket.domain.AppInfo;
import com.leiyun.appmarket.http.protocol.HomeDetailProtocol;
import com.leiyun.appmarket.ui.holder.DetailAppInfoHolder;
import com.leiyun.appmarket.ui.holder.DetailDesHolder;
import com.leiyun.appmarket.ui.holder.DetailPicsHolder;
import com.leiyun.appmarket.ui.holder.DetailSafeHolder;
import com.leiyun.appmarket.ui.view.LoadingPage;
import com.leiyun.appmarket.utils.UIUtils;

import static android.R.id.toggle;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

/**
 * 首页应用详情页
 * Created by LeiYun on 2017/2/15 0015.
 */

public class HomeDetailActivity extends BaseActivity {

    private LoadingPage mLoadingPage;
    private String mPackageName;
    private AppInfo data;
    private ActionBar mActionBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLoadingPage = new LoadingPage(UIUtils.getContext()) {
            @Override
            public View onCreateSuccessView() {
                return HomeDetailActivity.this.onCreateSuccessView();

            }

            @Override
            public ResultState onLoad() {
                return HomeDetailActivity.this.onLoad();
            }
        };

        setContentView(mLoadingPage); // 直接将一个View对象设置给activity

        // 获取包名
        mPackageName = getIntent().getStringExtra("packageName");

        // 开始加载网络数据
        mLoadingPage.loadData();

        initActionBar();
    }

    /**
     * 初始化ActionBar
     */
    private void initActionBar() {
        mActionBar = getSupportActionBar();

        // 设置ActionBar显示出来
        mActionBar.setIcon(R.drawable.ic_launcher);
//        mActionBar.setDisplayUseLogoEnabled(true);
        mActionBar.setDisplayShowHomeEnabled(true);

        mActionBar.setDisplayHomeAsUpEnabled(true); // 显示左上角返回键，当和侧边栏结合是显示三个杠图片

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;

            default:
                break;
        }
        
        return super.onOptionsItemSelected(item);
    }

    public View onCreateSuccessView() {
        // 初始化成功的布局
        View view = UIUtils.inflate(R.layout.page_home_detail);

        // 初始化应用信息模块
        FrameLayout flDetailAppInfo = (FrameLayout) view.findViewById(R.id.fl_detail_appinfo);

        // 动态的给帧布局填充页面
        DetailAppInfoHolder appInfoHolder = new DetailAppInfoHolder();
        appInfoHolder.setData(data);
        flDetailAppInfo.addView(appInfoHolder.getRootView());

        // 初始化安全模块
        FrameLayout flDetailSafe = (FrameLayout) view.findViewById(R.id.fl_detail_safe);
        DetailSafeHolder detailSafeHolder = new DetailSafeHolder();
        detailSafeHolder.setData(data);
        flDetailSafe.addView(detailSafeHolder.getRootView());


        // 截图模块
        HorizontalScrollView hsv = (HorizontalScrollView) view.findViewById(R.id.hsv_detail_pics);
        DetailPicsHolder detailPicsHolder = new DetailPicsHolder();
        detailPicsHolder.setData(data);
        hsv.addView(detailPicsHolder.getRootView());

        // 初始化描述
        FrameLayout flDetailDes = (FrameLayout) view.findViewById(R.id.fl_detail_des);
        DetailDesHolder detailDesHolder = new DetailDesHolder();
        detailDesHolder.setData(data);
        flDetailDes.addView(detailDesHolder.getRootView());

        return view;
    }

    public LoadingPage.ResultState onLoad() {
        // 请求网络，加载数据
        HomeDetailProtocol protocol = new HomeDetailProtocol(mPackageName);
        data = protocol.getData(0);
        if (data != null) {
            return LoadingPage.ResultState.STATE_SUCCESS;
        } else {
            return LoadingPage.ResultState.STATE_ERROR;
        }
    }
}
