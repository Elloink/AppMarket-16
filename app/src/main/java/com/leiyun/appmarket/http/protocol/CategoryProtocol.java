package com.leiyun.appmarket.http.protocol;

import com.leiyun.appmarket.domain.CategoryInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * 分类模块请求网络
 * Created by LeiYun on 2017/2/13 0013.
 */

public class CategoryProtocol extends BaseProtocol<ArrayList<CategoryInfo>> {

    @Override
    public String getKey() {
        return "category";
    }

    @Override
    public String getParams() {
        return "";
    }

    @Override
    public ArrayList<CategoryInfo> parseData(String result) {
        try {
            JSONArray ja = new JSONArray(result);
            ArrayList<CategoryInfo> list = new ArrayList<>();
            for (int i = 0; i < ja.length(); i++) {
                JSONObject jo = ja.getJSONObject(i);

                // 初始化对象
                if (jo.has("title")) { // 判断是否有title这个字段
                    CategoryInfo titleInfo = new CategoryInfo();
                    String title = jo.getString("title");
                    titleInfo.title = title;
                    titleInfo.isTitle = true;
                    list.add(titleInfo);
                }

                // 初始化分类对象
                if (jo.has("infos")) {// 判断是否有infos这个字段
                    JSONArray ja1 = jo.getJSONArray("infos");
                    for (int j = 0; j < ja1.length(); j++) {
                        JSONObject jo1 = ja1.getJSONObject(j);
                        CategoryInfo categoryInfo = new CategoryInfo();
                        categoryInfo.name1 = jo1.getString("name1");
                        categoryInfo.name2 = jo1.getString("name2");
                        categoryInfo.name3 = jo1.getString("name3");
                        categoryInfo.url1 = jo1.getString("url1");
                        categoryInfo.url2 = jo1.getString("url2");
                        categoryInfo.url3 = jo1.getString("url3");
                        categoryInfo.isTitle = false;
                        list.add(categoryInfo);
                    }
                }
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
