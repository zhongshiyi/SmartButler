package com.example.smartbutler.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

import com.example.smartbutler.utils.L;
import com.example.smartbutler.utils.StaticClass;


/**
 * 项目名：    SmartButler
 * 包名：      com.example.smartbutler.service
 * 文件名：    MessageService
 * 作者：      钟士宜
 * 创建时间    2019/7/17 21:45
 * 描述：      短信监听服务
 */
public class SmsService extends Service {

    private SmsReceiver smsReceiver;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        init();
    }

    //初始化
    private void init() {
        L.i("init service");

        //动态注册
        smsReceiver = new SmsReceiver();
        IntentFilter intent = new IntentFilter();
        //添加Action
        intent.addAction(StaticClass.SMS_ACTION);
        //设置权限
        intent.setPriority(Integer.MAX_VALUE);
        //注册
        registerReceiver(smsReceiver,intent);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        L.i("stop service");

        //注销
        unregisterReceiver(smsReceiver);
    }

    //短信广播
    public class SmsReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {

                L.i("短信来了！");

        }
    }
}
