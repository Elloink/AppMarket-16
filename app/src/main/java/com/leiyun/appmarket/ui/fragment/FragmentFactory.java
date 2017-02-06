package com.leiyun.appmarket.ui.fragment;

import android.support.v4.app.Fragment;

import java.util.HashMap;

/**
 * 生产fragment的工厂
 * Created by LeiYun on 2017/2/6 0006.
 */

public class FragmentFactory {

    private static HashMap<Integer, BaseFragment> mFragmentMap = new HashMap<>();

    public static BaseFragment createFragment(int pos) {
        //先从集合中获取，如果没有在创建，以提高性能
        BaseFragment fragment = mFragmentMap.get(pos);

        if (fragment == null) {
            switch (pos) {
                case 0:
                    fragment = new HomeFragment();
                    break;

                case 1:
                    fragment = new AppFragment();
                    break;

                case 2:
                    fragment = new GameFragment();
                    break;

                case 3:
                    fragment = new SubjectFragment();
                    break;

                case 4:
                    fragment = new RecommendFragment();
                    break;

                case 5:
                    fragment = new CategoryFragment();
                    break;

                case 6:
                    fragment = new HotFragment();
                    break;

                default:
                    break;
            }
            mFragmentMap.put(pos, fragment);// 将fragment保存到集合中
        }

        return fragment;
    }
}
