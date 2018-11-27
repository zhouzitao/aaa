package com.bwei.zhouzitao.zhouzitao1123;


import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.bwei.zhouzitao.zhouzitao1123.adapter.FragPagerAdapter;
import com.bwei.zhouzitao.zhouzitao1123.adapter.MlAdapter;
import com.bwei.zhouzitao.zhouzitao1123.fragment.ShouFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TabLayout mTablayout;
    private ViewPager mViewpager;
    private ListView mLv;
    private String[] mTitles = {"首页","我的","未登录"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化控件
        mTablayout = findViewById(R.id.tablayout);
        mViewpager = findViewById(R.id.viewpager);
        mLv = findViewById(R.id.lv);
        //设置集合
        ArrayList<String> list = new ArrayList<>();
        //添加侧拉条目
        list.add("日志");
        list.add("帮助");
        list.add("无障碍服务");
        list.add("悬浮窗");
        list.add("连接电脑");
        MlAdapter mlAdapter = new MlAdapter(MainActivity.this,list);
        mLv.setAdapter(mlAdapter);
        //创建fragment集合
        ArrayList<Fragment> fragments = new ArrayList<>();
        //添加fragment
        for (int i=0; i<mTitles.length;i++){
            ShouFragment shouFragment = new ShouFragment();
            //添加区别
            Bundle bundle = new Bundle();
            bundle.putInt("id",i);
            shouFragment.setArguments(bundle);
            //添加集合
            fragments.add(shouFragment);
            //设置适配器
            FragPagerAdapter fragPagerAdapter = new FragPagerAdapter(getSupportFragmentManager(),mTitles);
            mViewpager.setAdapter(fragPagerAdapter);
            fragPagerAdapter.setList(fragments);
            //设置Tablayout
            mTablayout.setupWithViewPager(mViewpager);
            mTablayout.setTabMode(TabLayout.MODE_FIXED);
            mTablayout.setTabGravity(TabLayout.GRAVITY_FILL);

        }

    }
}
