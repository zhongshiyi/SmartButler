package com.example.smartbutler.ui;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.smartbutler.R;
import com.example.smartbutler.utils.UtilTools;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名：    SmartButler
 * 包名：      com.example.smartbutler.ui
 * 文件名：    AboutActivity
 * 作者：      钟士宜
 * 创建时间    2019/7/21 10:48
 * 描述：      关于软件
 */
public class AboutActivity extends BaseActivity {

    private ListView mListView_about;
    //数据源
    private List<String>mList = new ArrayList<>();
    //适配器
    private ArrayAdapter<String>mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        //去除阴影
        getSupportActionBar().setElevation(0);

        initView();
    }

    //初始化
    private void initView() {
        mListView_about = findViewById(R.id.mListView_about);

        mList.add("应用名："+ getString(R.string.app_name));
        mList.add("版本号：" + UtilTools.getVersion(this));
        mList.add("官网：https://github.com/zhongshiyi/SmartButler");

        mAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,mList);
        //设置适配器
        mListView_about.setAdapter(mAdapter);

    }
}
