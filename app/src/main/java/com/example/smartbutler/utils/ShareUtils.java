package com.example.smartbutler.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 项目名：    SmartButler
 * 包名：      com.example.smartbutler.utils
 * 文件名：    ShareUtils
 * 作者：      钟士宜
 * 创建时间    2019/5/18 17:46
 * 描述：      SharedPreferences封装
 */
public class ShareUtils {

//    private void test(Context mContext){
//        SharedPreferences sp = mContext.getSharedPreferences("config",Context.MODE_PRIVATE);
//        sp.getString("key","未获取得到");//取的方式
//
//        SharedPreferences.Editor editor = sp.edit();
//        editor.putString("key","value");//存的方式
//
//
//    }
    public static final String NAME = "config";

    /**
     *  存储(键---值)
     */
    public static void putString(Context mContext,String key,String value){
        SharedPreferences sp = mContext.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        sp.edit().putString(key,value).commit();
    }
    public static void putInt(Context mContext,String key,int value){
        SharedPreferences sp = mContext.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        sp.edit().putInt(key,value).commit();
    }
    public static void putBoolean(Context mContext,String key,boolean value){
        SharedPreferences sp = mContext.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        sp.edit().putBoolean(key,value).commit();
    }

    /**
     * 读取（键---默认值）
     */
    public static String getString(Context mContext,String key,String defValue){
        SharedPreferences sp = mContext.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        return sp.getString(key,defValue);
    }
    public static int getInt(Context mContext,String key,int defValue){
        SharedPreferences sp = mContext.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        return sp.getInt(key,defValue);
    }
    public static boolean getBoolean(Context mContext,String key,boolean defValue){
        SharedPreferences sp = mContext.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        return sp.getBoolean(key,defValue);
    }

    /**
     * 删除单个
     */
    public static void deleShare(Context mContext,String key){
        SharedPreferences sp = mContext.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        sp.edit().remove(key).commit();
    }

    /**
     * 删除全部
     * @param mContext
     */
    public static void deleAll(Context mContext){
        SharedPreferences sp = mContext.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        sp.edit().clear().commit();
    }

}
