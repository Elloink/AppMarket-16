package com.leiyun.appmarket.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.leiyun.appmarket.R;
import com.leiyun.appmarket.utils.UIUtils;

/**
 * 根据当前状态来显示不同页面的自定义控件
 * - 未加载
 * - 加载中
 * - 加载失败
 * - 数据为空
 * - 加载成功
 * Created by LeiYun on 2017/2/6 0006.
 */

public class LoadingPage extends FrameLayout {

    private static final int STATE_LOAD_UNDO = 1;   // 未加载
    private static final int STATE_LOAD_LOADING = 2;// 加载中
    private static final int STATE_LOAD_ERROR = 3;  // 加载失败
    private static final int STATE_LOAD_EMPTY = 4;  // 数据为空
    private static final int STATE_LOAD_SUCCESS = 5;// 加载成功

    private int mCurrentState = STATE_LOAD_UNDO; // 当前状态
    private View mLoadingPage;
    private View mErrorPage;
    private View mEmptyPage;

    public LoadingPage(Context context) {
        super(context);
        initView();
    }

    public LoadingPage(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();

    }

    public LoadingPage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        // 初始化加载中的布局
        if (mLoadingPage == null) {
            mLoadingPage = UIUtils.inflate(R.layout.page_loading);
            addView(mLoadingPage);  //将加载中的布局添加给当前的帧布局
        }

        if (mErrorPage == null) {
            mErrorPage = UIUtils.inflate(R.layout.page_error);
            addView(mErrorPage); //将页面错误的布局添加给当前的帧布局
        }

        if (mEmptyPage == null) {
            mEmptyPage = UIUtils.inflate(R.layout.page_empty);
            addView(mEmptyPage); //将页面为空的布局添加给当前的帧布局
        }
    }
}
