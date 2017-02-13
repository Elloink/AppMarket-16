package com.leiyun.appmarket.http.protocol;

import com.leiyun.appmarket.domain.AppInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by LeiYun on 2017/2/13 0013.
 */

public class GameProtocol extends BaseProtocol<ArrayList<AppInfo>> {
    @Override
    public String getKey() {
        return "game";
    }

    @Override
    public String getParams() {
        return "";
    }

    @Override
    public ArrayList<AppInfo> parseData(String result) {
        try {
            JSONArray ja = new JSONArray(result);
            ArrayList<AppInfo> appInfoList = new ArrayList<>();
            for (int i = 0; i < ja.length(); i++) {
                JSONObject jo = ja.getJSONObject(i);
                AppInfo info = new AppInfo();
                info.des = jo.getString("des");
                info.downloadUrl = jo.getString("downloadUrl");
                info.iconUrl = jo.getString("iconUrl");
                info.id = jo.getString("id");
                info.name = jo.getString("name");
                info.packageName = jo.getString("packageName");
                info.size = jo.getLong("size");
                info.stars = (float)jo.getDouble("stars");
                appInfoList.add(info);
            }

            return appInfoList;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
