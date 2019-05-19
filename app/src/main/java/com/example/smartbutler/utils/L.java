package com.example.smartbutler.utils;

import android.util.Log;

/**
 * 项目名：    SmartButler
 * 包名：      com.example.smartbutler.utils
 * 文件名：    L
 * 作者：      钟士宜
 * 创建时间    2019/5/18 17:19
 * 描述：      Log封装类
 */
public class L {

    //开关
    public static final boolean DEBUG = true;
    //TAG
    public static final String TAG = "SmartButler";

    //五个等级  D I W E F,但是这里只封装前四个

    public static void d(String text){
        if(DEBUG){
            Log.d(TAG,text);
        }
    }

    public static void i(String text){
        if(DEBUG){
            Log.i(TAG,text);
        }
    }

    public static void w(String text){
        if(DEBUG){
            Log.w(TAG,text);
        }
    }

    public static void e(String text){
        if(DEBUG){
            Log.e(TAG,text);
        }
    }
}
