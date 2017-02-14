package com.leiyun.appmarket.http.protocol;

import com.leiyun.appmarket.domain.AppInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * 首页网络数据解析
 * Created by LeiYun on 2017/2/11 0011.
 */

public class HomeProtocol extends BaseProtocol<ArrayList<AppInfo>> {

    private ArrayList<String> pictures;

    @Override
    public String getKey() {
        return "home";
    }

    @Override
    public String getParams() {
        return ""; // 如果没有参数就传空串，不要传null
    }

    @Override
    public ArrayList<AppInfo> parseData(String result) {
        // 使用JsonObject解析方式：如果遇到{}，就是JSONObject
        // 如果遇到[]，就是JsonArray
        try {
            JSONObject jo = new JSONObject(result);
            JSONArray ja = jo.getJSONArray("list");
            ArrayList<AppInfo> appInfoList = new ArrayList<>();
            for (int i = 0; i < ja.length(); i++) {
                JSONObject jo1 = ja.getJSONObject(i);
                AppInfo info = new AppInfo();
                info.des = jo1.getString("des");
                info.downloadUrl = jo1.getString("downloadUrl");
                info.iconUrl = jo1.getString("iconUrl");
                info.id = jo1.getString("id");
                info.name = jo1.getString("name");
                info.packageName = jo1.getString("packageName");
                info.size = jo1.getLong("size");
                info.stars = (float)jo1.getDouble("stars");
                appInfoList.add(info);
            }

            // 初始化首页的banner图片
            JSONArray ja1 = jo.getJSONArray("picture");
            pictures = new ArrayList<>();
            for (int i = 0; i < ja1.length(); i++) {
                String pic = ja1.getString(i);
                pictures.add(pic);
            }

            return appInfoList;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public ArrayList<String> getPictureList() {
        return pictures;
    }
}
