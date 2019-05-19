package com.example.smartbutler.ui;

/**
 * 项目名：    SmartButler
 * 包名：      com.example.smartbutler.ui
 * 文件名：    BaseActivity
 * 作者：      钟士宜
 * 创建时间    2019/5/17 22:19
 * 描述：      Activity基类
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

/**
 * 主要做的事情：
 * 1、同意的属性
 * 2、统一的接口
 * 3、统一的方法
 */
public class BaseActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //返回返回键
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    //菜单栏操作
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
