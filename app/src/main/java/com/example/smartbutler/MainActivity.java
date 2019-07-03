package com.example.smartbutler;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


import com.example.smartbutler.fragment.ButlerFragment;
import com.example.smartbutler.fragment.GirlFragment;
import com.example.smartbutler.fragment.UserFragment;
import com.example.smartbutler.fragment.WeChatFragment;
import com.example.smartbutler.ui.SettingActivity;



import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //TabLayout
    private TabLayout mTabLayout;
    //ViewPager
    private ViewPager mViewPager;
    //Title
    private List<String> mTitle;
    //Fragment
    private List<Fragment> mFragment;
    //悬浮窗
    private FloatingActionButton fab_setting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //去掉阴影
        getSupportActionBar().setElevation(0);//设置一个0的阴影

        initData();
        initView();

        
    }




    //初始化数据
    private void initData() {
        mTitle = new ArrayList<>();
        mTitle.add(getResources().getString(R.string.butler));//服务管家
        mTitle.add(getResources().getString(R.string.weChat_selection));//微信精选
        mTitle.add(getResources().getString(R.string.beauty_community));//美女社区
        mTitle.add(getResources().getString(R.string.personal_center));//个人中心

        mFragment = new ArrayList<>();
        mFragment.add(new ButlerFragment());
        mFragment.add(new WeChatFragment());
        mFragment.add(new GirlFragment());
        mFragment.add(new UserFragment());
    }

    //初始化View
    @SuppressLint("RestrictedApi")
    private void initView() {
        fab_setting = findViewById(R.id.fab_setting);
        fab_setting.setOnClickListener(this);//点击事件

        //默认是隐藏的
        fab_setting.setVisibility(View.GONE);

        mTabLayout = findViewById(R.id.mTabLayout);
        mViewPager = findViewById(R.id.main_mViewPager);

        //预加载(提前进行加载)
        mViewPager.setOffscreenPageLimit(mFragment.size());



        //设置mViewPager滑动监听
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @SuppressLint("RestrictedApi")
            @Override
            public void onPageSelected(int position) {
                Log.i("TAG","position:" + position);
                if(position == 0){
                    fab_setting.setVisibility(View.GONE);//服务管家页面浮动窗口消失
                }else{
                    fab_setting.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //设置适配器
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            //选中的item
            @Override
            public Fragment getItem(int position) {
                return mFragment.get(position);

            }

            //返回item的个数
            @Override
            public int getCount() {
                return mFragment.size();
            }

            //设置标题
            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return mTitle.get(position);
            }
        });

        //绑定
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.fab_setting:
                startActivity(new Intent(this,SettingActivity.class));
                break;
        }
    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        if (mViewPager != null) {
//            ((ViewGroup) mViewPager.getParent()).removeView(mViewPager);
//        }
//    }
}
