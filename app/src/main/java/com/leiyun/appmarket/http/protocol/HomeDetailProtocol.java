package com.leiyun.appmarket.http.protocol;

import com.leiyun.appmarket.domain.AppInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * 首页详情页网络访问
 * Created by LeiYun on 2017/2/15 0015.
 */

public class HomeDetailProtocol extends BaseProtocol <AppInfo>{
    public String packageName;

    public HomeDetailProtocol(String packageName) {
        this.packageName = packageName;
    }

    @Override
    public String getKey() {
        return "detail";
    }

    @Override
    public String getParams() {
        return "&packageName=" + packageName;
    }

    @Override
    public AppInfo parseData(String result) {

        try {
            JSONObject jo = new JSONObject(result);

            AppInfo info = new AppInfo();

            info.des = jo.getString("des");
            info.downloadUrl = jo.getString("downloadUrl");
            info.iconUrl = jo.getString("iconUrl");
            info.id = jo.getString("id");
            info.name = jo.getString("name");
            info.packageName = jo.getString("packageName");
            info.size = jo.getLong("size");
            info.stars = (float)jo.getDouble("stars");

            info.author = jo.getString("author");
            info.date = jo.getString("date");
            info.downloadNum = jo.getString("downloadNum");
            info.version = jo.getString("version");

            JSONArray ja = jo.getJSONArray("safe");
            ArrayList<AppInfo.SafeInfo> safes = new ArrayList<>();
            for (int i = 0; i < ja.length(); i++) {
                JSONObject jo1 = ja.getJSONObject(i);
                AppInfo.SafeInfo safeInfo = new AppInfo.SafeInfo();
                safeInfo.safeDes = jo1.getString("safeDes");
                safeInfo.safeDesUrl = jo1.getString("safeDesUrl");
                safeInfo.safeUrl = jo1.getString("safeUrl");
                safes.add(safeInfo);
            }
            info.safe = safes;

            JSONArray ja1 = jo.getJSONArray("screen");
            ArrayList<String> screens = new ArrayList<>();
            for (int i = 0; i < ja1.length(); i++) {
                String pic = ja1.getString(i);
                screens.add(pic);
            }
            info.screen = screens;

            return info;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
