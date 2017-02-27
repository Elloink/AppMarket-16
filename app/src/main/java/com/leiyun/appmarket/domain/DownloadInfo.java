package com.leiyun.appmarket.domain;

import android.os.Environment;

import com.leiyun.appmarket.manager.DownloadManager;

import java.io.File;

import static android.os.Environment.getExternalStorageDirectory;

/**
 * 下载对象
 *
 * 注意: 一定要有读写sdcard的权限!!!!
 *
 * <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
 * <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
 *
 * Created by LeiYun on 2017/2/25 0025.
 */

public class DownloadInfo {
    public String id;
    public String name;
    public String packageName;
    public String downloadUrl;
    public long size;

    public long currentPos; // 当前下载位置
    public int currentState; // 当前下载状态
    public String path; // 下载到本地文件的路径

    public static final String GOOGLE_MARKET = "GOOGLE_MARKET"; // sdcard根目录文件夹名称
    public static final String DOWNLOAD = "download"; // 子文件夹名称，存放下载的文件

    /**
     * 获取下载进度
     * @return （0-1)
     */
    public float getProgress() {
        if (size == 0) {
            return 0;
        }
        return currentPos/(float)size;
    }

    /**
     * 拷贝对象，从AppInfo中得出一个Download对象
     * @param info AppInfo对象
     * @return
     */
    public static DownloadInfo copy(AppInfo info) {
        DownloadInfo downloadInfo = new DownloadInfo();
        downloadInfo.id = info.id;
        downloadInfo.name = info.name;
        downloadInfo.downloadUrl = info.downloadUrl;
        downloadInfo.packageName = info.packageName;
        downloadInfo.size = info.size;

        downloadInfo.currentPos = 0;
        downloadInfo.currentState = DownloadManager.STATE_UNDO;  // 默认是未下载

        downloadInfo.path = downloadInfo.getFilePath();

        return downloadInfo;
    }

    /**
     * 获取文件下载路径
     * @return 文件下载路径
     */
    public String getFilePath() {
        StringBuffer sb = new StringBuffer();
        String sdcard = Environment.getExternalStorageDirectory()
                .getAbsolutePath();
        sb.append(sdcard);
        // sb.append("/");
        sb.append(File.separator);
        sb.append(GOOGLE_MARKET);
        sb.append(File.separator);
        sb.append(DOWNLOAD);

        if (createDir(sb.toString())) {
            // 文件夹存在或者已经创建完成
            return sb.toString() + File.separator + name + ".apk";// 返回文件路径
        }

        return null;
    }

    /**
     * 判断下载的目录是否创建好了
     * @param dir
     * @return 一个boolean值true表示已经有了，false表示文件夹没有/创建失败
     */
    private boolean createDir(String dir) {
        File dirFile = new File(dir);

        // 文件夹不存在或者不是文件夹
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            boolean success = dirFile.mkdirs();
            return success;
        }
        return true; // 文件夹存在
    }
}
