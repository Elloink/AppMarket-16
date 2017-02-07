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

public abstract class LoadingPage extends FrameLayout {

    private static final int STATE_LOAD_UNDO = 1;   // 未加载
    private static final int STATE_LOAD_LOADING = 2;// 加载中
    private static final int STATE_LOAD_ERROR = 3;  // 加载失败
    private static final int STATE_LOAD_EMPTY = 4;  // 数据为空
    private static final int STATE_LOAD_SUCCESS = 5;// 加载成功

    private int mCurrentState = STATE_LOAD_UNDO; // 当前状态

    private View mLoadingPage; // 未加载的布局
    private View mErrorPage; // 加载成功的布局
    private View mEmptyPage; // 数据为空的布局
    private View mSuccessPage; // 加载成功的布局

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

        // 初始化加载失败的布局
        if (mErrorPage == null) {
            mErrorPage = UIUtils.inflate(R.layout.page_error);
            addView(mErrorPage); //将页面错误的布局添加给当前的帧布局
        }

        // 初始化加载为空的布局
        if (mEmptyPage == null) {
            mEmptyPage = UIUtils.inflate(R.layout.page_empty);
            addView(mEmptyPage); //将页面为空的布局添加给当前的帧布局
        }

        showRightPage();
    }

    // 根据当前状态，决定显示那个布局
    private void showRightPage() {
        mLoadingPage.setVisibility(
                mCurrentState == STATE_LOAD_UNDO || mCurrentState == STATE_LOAD_LOADING ?
                        View.VISIBLE : View.GONE
        );

        mErrorPage.setVisibility(
                mCurrentState == STATE_LOAD_ERROR ?
                        View.VISIBLE : View.GONE
        );

        mEmptyPage.setVisibility(
                mCurrentState == STATE_LOAD_EMPTY ?
                        View.VISIBLE : View.GONE
        );

        // 当成功布局为空，并且当前状态为成功，才初始化成功的布局
        if (mSuccessPage == null && mCurrentState == STATE_LOAD_SUCCESS) {
            mSuccessPage = onCreateSuccessView();

            if (mSuccessPage != null) {
                addView(mSuccessPage);
            }
        }

        if (mSuccessPage != null) {
            mSuccessPage.setVisibility(
                    mCurrentState == STATE_LOAD_SUCCESS ?
                            View.VISIBLE : View.GONE
            );
        }

    }

    // 开始加载数据
    public void loadData() {
        if (mCurrentState != STATE_LOAD_LOADING) { // 如果当前没有加载，就开始加载数据

            mCurrentState = STATE_LOAD_LOADING;

            new Thread() {
                @Override
                public void run() {
                    final ResultState resultState = onLoad();

                    // 使用主线程更新界面
                    UIUtils.runOnUIThread(new Runnable() {
                        @Override
                        public void run() {
                            if (resultState != null) {
                                mCurrentState = resultState.getState(); // 网络加载结束后，更新网路状态

                                // 根据最新的状态来刷新界面
                                showRightPage();
                            }
                        }
                    });

                }
            }.start();
        }
    }

    // 加载成功后显示的布局,有调用者来实现
    public abstract View onCreateSuccessView();

    // 加载网路数据,返回值表示请求结束后的状态
    public abstract ResultState onLoad();

    // 对加载的状态的枚举
    public enum ResultState {
        STATE_SUCCESS(STATE_LOAD_SUCCESS),
        STATE_EMPTY(STATE_LOAD_EMPTY),
        STATE_ERROR(STATE_LOAD_ERROR);

        int state;

        private ResultState(int state) {
            this.state = state;
        }

        public int getState() {
            return state;
        }
    }
}
