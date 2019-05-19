package com.example.smartbutler.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.smartbutler.MainActivity;
import com.example.smartbutler.R;
import com.example.smartbutler.utils.ShareUtils;
import com.example.smartbutler.utils.StaticClass;
import com.example.smartbutler.utils.UtilTools;

import static com.example.smartbutler.utils.StaticClass.HANDLER_SPLASH;

/**
 * 项目名：    SmartButler
 * 包名：      com.example.smartbutler.ui
 * 文件名：    SplashActivity
 * 作者：      钟士宜
 * 创建时间    2019/5/18 20:38
 * 描述：      闪屏类
 */
public class SplashActivity extends AppCompatActivity {

    /**
     * 1、延时2000ms
     * 2、判断程序是不是第一次运行
     * 3、自定义字体
     * 4、Activity全屏主题
     */
    private TextView tv_splash;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case StaticClass.HANDLER_SPLASH:
                    //判断程序是不是第一次运行
                    if(isFirst()){
                        startActivity(new Intent(SplashActivity.this,GuideActivity.class));
                    }else{
                        startActivity(new Intent(SplashActivity.this,GuideActivity.class));
                    }
                    break;
            }
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initView();
    }
    //初始化View
    private void initView() {
        //延时2000ms
        handler.sendEmptyMessageDelayed(HANDLER_SPLASH,2000);

        tv_splash = findViewById(R.id.tv_splash);
        //设置字体
        UtilTools.setFont(this,tv_splash);
    }

    //判断程序是不是第一运行
    private boolean isFirst(){
        boolean isFirst = ShareUtils.getBoolean(this,StaticClass.SHARE_IS_FIRST,true);
        if(isFirst){
            ShareUtils.putBoolean(this,StaticClass.SHARE_IS_FIRST,false);//变为false
            //是第一次运行
            return true;
        }else{
            return false;
        }
    }
}
