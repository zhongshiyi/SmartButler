package com.example.smartbutler.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.util.Log;
import android.widget.TextView;

/**
 * 项目名：    SmartButler
 * 包名：      com.example.smartbutler.utils
 * 文件名：    UtilTools
 * 作者：      钟士宜
 * 创建时间    2019/5/18 9:15
 * 描述：      工具统一类
 */
public class UtilTools {
    //设置字体
    public static void setFont(Context mContext, TextView textView){
        Typeface fontType = Typeface.createFromAsset(mContext.getAssets(),"fly.ttf");
        textView.setTypeface(fontType);
    }
}
