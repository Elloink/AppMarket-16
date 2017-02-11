package com.leiyun.appmarket.http.protocol;

import com.leiyun.appmarket.http.HttpHelper;
import com.leiyun.appmarket.utils.IOUtils;
import com.leiyun.appmarket.utils.LogUtils;
import com.leiyun.appmarket.utils.StringUtils;
import com.leiyun.appmarket.utils.UIUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 访问网络的基类
 * Created by LeiYun on 2017/2/11 0011.
 */

public abstract class BaseProtocol<T> {

    /**
     * 数据的获取
     * @param index 表示的是从哪个位置开始返回20条数据，用于分页
     */
    public T getData(int index) {
        // 先判断是否有缓冲，有的话就加载缓存
        String result = getCache(index);

        if (StringUtils.isEmpty(result)) { // 如果没有缓存，或缓存失效
            // 重新请求服务器
            result = getDataFromServer(index);
        }

        // 开始解析
        if (result != null) {
            T data = parseData(result);
            return data;
        }

        return null;
    }

    /**
     * 从网络获取数据
     * @param index 表示的是从哪个位置开始返回20条数据，用于分页
     */
    private String getDataFromServer(int index) {
        HttpHelper.HttpResult httpResult = HttpHelper.get(HttpHelper.URL + getKey() +
                "?index=" + index + getParams());

        if (httpResult != null) {
            String result = httpResult.getString();
            LogUtils.d("访问结果：" + result);

            // 写缓存
            if (!StringUtils.isEmpty(result)) {
                setCache(index, result);
            }

            return result;
        }

        return null;
    }

    /**
     * 获取网络链接的关键字，由子类实现
     * @return 网络链接的关键字
     */
    public abstract String getKey();

    /**
     * 获取网络链接参数，由子类实现
     * @return 网络链接的参数
     */
    public abstract String getParams();

    /**
     * 解析数据方法，由子类实现
     */
    public abstract T parseData(String result);

    /**
     * 写缓存，以url为key，以json为value
     */
    public void setCache(int index, String json) {
        // 以url为文件名，以json为文件内容，保存在本地
        File cacheDir = UIUtils.getContext().getCacheDir(); // 目录是在本应用的缓存文件夹里，并不是在sd卡中

        // 生成缓存文件
        File cacheFile = new File(cacheDir, getKey() +
                "?index=" + index + getParams());
        FileWriter writer = null;
        try {
            writer = new FileWriter(cacheFile);

            long deadline = System.currentTimeMillis() + 30 * 60 * 1000; // 半个小时有效期
            writer.write(deadline + "\n"); // 在第一行写入缓存时间
            writer.write(json); // 写入json
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            IOUtils.close(writer);
        }
    }

    /**
     * 获取缓存里的数据
     * @param index 表示的是从哪个位置开始返回20条数据，用于分页
     * @return 缓存文件里的数据
     */
    public String getCache(int index) {
        // 以url为文件名，以json为文件内容，保存在本地
        File cacheDir = UIUtils.getContext().getCacheDir(); // 目录是在本应用的缓存文件夹里，并不是在sd卡中

        // 生成缓存文件
        File cacheFile = new File(cacheDir, getKey() +
                "?index=" + index + getParams());

        // 判断是否有缓存文件
        if (cacheFile != null) {

            BufferedReader reader = null;
            try {
                //获取有效期
                reader = new BufferedReader(new FileReader(cacheFile));
                StringBuffer sb = new StringBuffer();
                String line;

                String deadline = reader.readLine();
                long deadtime = Long.parseLong(deadline);


                // 判断是否有效
                if (System.currentTimeMillis() < deadtime) {// 当前时间小于截止时间，说明缓存有效
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                    }
                    return sb.toString();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                IOUtils.close(reader);
            }


        }
        return null;
    }

}
