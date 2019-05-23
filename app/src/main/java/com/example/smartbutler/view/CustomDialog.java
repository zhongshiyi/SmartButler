package com.example.smartbutler.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.example.smartbutler.R;

/**
 * 项目名：    SmartButler
 * 包名：      com.example.smartbutler.view
 * 文件名：    CustomDialog
 * 作者：      钟士宜
 * 创建时间    2019/5/23 21:08
 * 描述：      自定义Dialog
 */
public class CustomDialog extends Dialog {

    //定义模板
    public CustomDialog(Context context,int layout,int style){
        this(context,WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT,
                layout,style,Gravity.CENTER);

    }

    //定义属性
    public CustomDialog(Context context,int width,int height,int layout,int style,int gravity,int anim){
        super(context,style);
        //设置属性
        setContentView(layout);
        Window window = getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = width;
        layoutParams.height = height;
        layoutParams.gravity = gravity;
        window.setAttributes(layoutParams);
        window.setWindowAnimations(anim);
    }

    //实例
    public CustomDialog(Context context,int width,int height,int layout,int style,int gravity){
        this(context,width,height,layout,style,gravity,R.style.pop_anim_style);
    }
}
