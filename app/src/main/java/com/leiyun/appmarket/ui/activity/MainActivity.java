package com.leiyun.appmarket.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.leiyun.appmarket.R;
import com.leiyun.appmarket.ui.fragment.BaseFragment;
import com.leiyun.appmarket.ui.fragment.FragmentFactory;
import com.leiyun.appmarket.ui.view.PagerTab;
import com.leiyun.appmarket.utils.UIUtils;

/**
 * 当项目和appcompat关联在一起时，就必须在清单文件中设置Theme.AppCompat的主题，否则崩溃
 * android:theme="@style/Theme.AppCompat.Light"
 */
public class MainActivity extends BaseActivity {

    private PagerTab mPagerTab;
    private ViewPager mViewPager;
    private MyAdapter mAdapter;
    private ActionBar mActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mActionBar = getSupportActionBar();
        mActionBar.setIcon(R.drawable.ic_launcher);
        mActionBar.setDisplayUseLogoEnabled(true);
        mActionBar.setDisplayShowHomeEnabled(true);

        mPagerTab = (PagerTab)findViewById(R.id.pager_tab);
        mViewPager = (ViewPager) findViewById(R.id.viewpage);

        mAdapter = new MyAdapter(getSupportFragmentManager());

        mViewPager.setAdapter(mAdapter);

        mPagerTab.setViewPager(mViewPager); //将指针和viewpager绑定在一起

        mPagerTab.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                BaseFragment fragment = FragmentFactory.createFragment(position);

                // 开始加载数据
                fragment.loadData();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    class MyAdapter extends FragmentPagerAdapter {


        private String[] mTabNames;
        private BaseFragment mFragment;

        public MyAdapter(FragmentManager fm) {
            super(fm);
            mTabNames = UIUtils.getStringArray(R.array.tab_names);// 加载页面标题数组
        }

        //返回页签标题
        @Override
        public CharSequence getPageTitle(int position) {
            return mTabNames[position];
        }

        //返回当前页面位置的fragment对象
        @Override
        public Fragment getItem(int position) {
            mFragment = FragmentFactory.createFragment(position);
            return mFragment;
        }

        // fragment数量
        @Override
        public int getCount() {
            return mTabNames.length;
        }
    }

}
