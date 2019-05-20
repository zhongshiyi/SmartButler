package com.example.smartbutler.application;

import android.app.Application;

import com.example.smartbutler.utils.StaticClass;
import com.tencent.bugly.crashreport.CrashReport;

/**
 * 项目名：    SmartButler
 * 包名：      com.example.smartbutler.ui
 * 文件名：    BaseApplication
 * 作者：      钟士宜
 * 创建时间    2019/5/17 22:19
 * 描述：
 */
public class BaseApplication extends Application {

    //创建
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化Bugly
        CrashReport.initCrashReport(getApplicationContext(), StaticClass.BUGLY_APP_ID, true);
    }
}
