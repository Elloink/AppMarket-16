package com.leiyun.appmarket.ui.holder;

import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.leiyun.appmarket.R;
import com.leiyun.appmarket.domain.AppInfo;
import com.leiyun.appmarket.domain.DownloadInfo;
import com.leiyun.appmarket.manager.DownloadManager;
import com.leiyun.appmarket.ui.view.ProgressHorizontal;
import com.leiyun.appmarket.utils.UIUtils;

/**
 * 详情页-下载模块
 * Created by LeiYun on 2017/2/25 0025.
 */

public class DetailDownloadHolder extends BaseHolder<AppInfo> implements
        DownloadManager.DownloadObserver, View.OnClickListener{

    private DownloadManager mDM;
    private FrameLayout flProgress;
    private int mCurrentState;
    private float mProgress;
    private Button btnDownload;
    private ProgressHorizontal pbProgress;

    @Override
    public View initView() {
        View view = UIUtils.inflate(R.layout.layout_detail_download);

        btnDownload = (Button) view.findViewById(R.id.btn_download);
        btnDownload.setOnClickListener(this);

        // 初始化自定义进度条
        flProgress = (FrameLayout) view.findViewById(R.id.fl_progress);
        pbProgress = new ProgressHorizontal(UIUtils.getContext());
        flProgress.setOnClickListener(this);

        pbProgress.setProgressBackgroundResource(R.drawable.progress_bg); // 进度条背景
        pbProgress.setProgressResource(R.drawable.progress_normal); // 进度条图片
        pbProgress.setProgressTextColor(Color.WHITE); // 进度文字颜色
        pbProgress.setProgressTextSize(UIUtils.dip2px(18)); // 进度文字大小

        // 高宽填充父窗体
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT);


        // 给帧布局添加自定义进度条
        flProgress.addView(pbProgress, params);

        mDM = DownloadManager.getInstance();
        mDM.registerObserver(this); // 注册观察者，监听状态和进度变化

        return view;
    }

    @Override
    public void refreshView(AppInfo data) {
        // 判断当前应用是否下载过
        DownloadInfo downloadInfo = mDM.getDownloadInfo(data);

        if (downloadInfo != null) {
            // 之前下载过
            mCurrentState = downloadInfo.currentState;
            mProgress = downloadInfo.getProgress();
        } else {
            // 没有下载过
            mCurrentState = DownloadManager.STATE_UNDO;
            mProgress = 0;
        }

        refreshUI(mCurrentState, mProgress);
    }

    /**
     * 根据当前的下载进度和状态来更新界面
     * @param currentState
     * @param progress
     */
    private void refreshUI(int currentState, float progress) {
        mCurrentState = currentState;
        mProgress = progress;

        switch (currentState) {
            case DownloadManager.STATE_UNDO: // 未下载
                flProgress.setVisibility(View.GONE);
                btnDownload.setVisibility(View.VISIBLE);
                btnDownload.setText("下载");
                break;

            case DownloadManager.STATE_WAITING: // 等待下载
                flProgress.setVisibility(View.GONE);
                btnDownload.setVisibility(View.VISIBLE);
                btnDownload.setText("等待中. . .");
                break;

            case DownloadManager.STATE_DOWNLOADING:// 正在下载
                flProgress.setVisibility(View.VISIBLE);
                btnDownload.setVisibility(View.GONE);
                pbProgress.setCenterText("");
                pbProgress.setProgress(mProgress);// 设置下载进度
                break;

            case DownloadManager.STATE_PAUSE: // 下载暂停
                flProgress.setVisibility(View.VISIBLE);
                btnDownload.setVisibility(View.GONE);
                pbProgress.setCenterText("暂停");
                pbProgress.setProgress(mProgress);
                break;

            case DownloadManager.STATE_ERROR: // 下载失败
                flProgress.setVisibility(View.GONE);
                btnDownload.setVisibility(View.VISIBLE);
                btnDownload.setText("下载失败");
                break;

            case DownloadManager.STATE_SUCCESS: // 下载成功
                flProgress.setVisibility(View.GONE);
                btnDownload.setVisibility(View.VISIBLE);
                btnDownload.setText("安装");
                break;

            default:
                break;
        }
    }

    /**
     * 主线程更新UI
     *
     */
    private void refreshUIOnMainThread(final DownloadInfo info) {
        AppInfo appInfo = getData();
        if (appInfo.id.equals(info.id)) {// 判断下载对象是否是当前应用
            UIUtils.runOnUIThread(new Runnable() {
                @Override
                public void run() {
                    refreshUI(info.currentState, info.getProgress());
                }
            });
        }
    }

    /**
     * 状态的更新
     * @param info
     */
    @Override
    public void onDownloadStateChanged(DownloadInfo info) {
        refreshUIOnMainThread(info);
    }

    /**
     * 进度的更新，子线程
     * @param info
     */
    @Override
    public void onDownloadProgressChanged(DownloadInfo info) {
        refreshUIOnMainThread(info);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_download:
            case R.id.fl_progress:
                // 根据当前状态来决定下一步操作
                if (mCurrentState == DownloadManager.STATE_UNDO
                        || mCurrentState == DownloadManager.STATE_ERROR
                        ||mCurrentState == DownloadManager.STATE_PAUSE) {
                    mDM.download(getData());// 开始下载
                } else if (mCurrentState == DownloadManager.STATE_DOWNLOADING
                        || mCurrentState == DownloadManager.STATE_WAITING) {
                    mDM.pause(getData()); // 暂停下载
                } else if (mCurrentState == DownloadManager.STATE_SUCCESS) {
                    mDM.install(getData());
                }

                break;

            default:
                break;
        }
    }
}
