package com.example.smartbutler.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Switch;

import com.example.smartbutler.R;
import com.example.smartbutler.utils.ShareUtils;

/**
 * 项目名：    SmartButler
 * 包名：      com.example.smartbutler.ui
 * 文件名：    SettingActivity
 * 作者：      钟士宜
 * 创建时间    2019/5/18 16:26
 * 描述：      TODO
 */
public class SettingActivity extends BaseActivity  {
    //语音播报
    private Switch sw_speak;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        //initView();
    }

//    private void initView() {
//        sw_speak = findViewById(R.id.sw_speak);
//        sw_speak.setOnClickListener(this);
//    }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()){
//            case R.id.sw_speak:
//                //切换相反
//                sw_speak.setSelected(!sw_speak.isSelected());
//                //保存状态
//                ShareUtils.putBoolean(this,"isSpeak",sw_speak.isChecked());
//                break;
//        }
//    }
}
