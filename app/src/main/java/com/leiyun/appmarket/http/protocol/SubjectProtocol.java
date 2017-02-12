package com.leiyun.appmarket.http.protocol;

import com.leiyun.appmarket.domain.SubjectInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * 专题网络请求
 * Created by LeiYun on 2017/2/12 0012.
 */

public class SubjectProtocol extends BaseProtocol<ArrayList<SubjectInfo>> {
    @Override
    public String getKey() {
        return "subject";
    }

    @Override
    public String getParams() {
        return "";
    }

    @Override
    public ArrayList<SubjectInfo> parseData(String result) {
        try {
            JSONArray ja = new JSONArray(result);
            ArrayList<SubjectInfo> subjecs = new ArrayList<>();
            for (int i = 0; i < ja.length(); i++) {
                JSONObject jo = ja.getJSONObject(i);

                SubjectInfo info = new SubjectInfo();
                info.des = jo.getString("des");
                info.url = jo.getString("url");

                subjecs.add(info);
            }
            return subjecs;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
