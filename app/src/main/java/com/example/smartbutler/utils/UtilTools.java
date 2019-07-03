package com.example.smartbutler.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

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

    //保存图片到shareUtils
    public static void putImageToShare(Context context,ImageView imageView){
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        //第一步：将Bitmap压缩成字节数组输出流
        ByteArrayOutputStream byStream = new ByteArrayOutputStream();
        //转化为输出流
        bitmap.compress(Bitmap.CompressFormat.PNG,80,byStream);
        //第二步：利用Base64将我们的字节数组输出流转换成String
        byte [] byteArray = byStream.toByteArray();
        String imgString = new String(Base64.encodeToString(byteArray,Base64.DEFAULT));
        //第三步：将String保存在shareUtils
        ShareUtils.putString(context,"image_title",imgString);
    }

    //读取图片
    public static void getImageView(Context context,ImageView imageView){
        String imgString = ShareUtils.getString(context,"image_title","");
        if(!imgString.equals("")){
            //2.利用Base64将我们String转化
            byte [] byteArray = Base64.decode(imgString,Base64.DEFAULT);
            ByteArrayInputStream byStream = new ByteArrayInputStream(byteArray);
            //3.生成bitmap
            Bitmap bitmap = BitmapFactory.decodeStream(byStream);
            imageView.setImageBitmap(bitmap);
        }
    }
}
