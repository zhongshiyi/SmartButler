package com.example.smartbutler.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Switch;

import com.example.smartbutler.R;
import com.example.smartbutler.service.SmsService;
import com.example.smartbutler.utils.ShareUtils;

/**
 * 项目名：    SmartButler
 * 包名：      com.example.smartbutler.ui
 * 文件名：    SettingActivity
 * 作者：      钟士宜
 * 创建时间    2019/5/18 16:26
 * 描述：      TODO
 */
public class SettingActivity extends BaseActivity implements View.OnClickListener {
    //语音播报
    private Switch sw_speak;
    //智能短信
    private Switch sw_sms;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initView();
    }

    private void initView() {
        sw_speak = findViewById(R.id.sw_speak);
        sw_speak.setOnClickListener(this);
        //默认语音播报不选中
        boolean isSpeak = ShareUtils.getBoolean(this,"isSpeak",false);
        sw_speak.setChecked(isSpeak);

        sw_sms = findViewById(R.id.sw_sms);
        sw_sms.setOnClickListener(this);
        //默认智能短信提醒不选中
        boolean isSms = ShareUtils.getBoolean(this,"isSms",false);
        sw_sms.setChecked(isSms);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sw_speak:
                //切换相反
                sw_speak.setSelected(!sw_speak.isSelected());
                //保存状态
                ShareUtils.putBoolean(this,"isSpeak",sw_speak.isChecked());
                break;
            case R.id.sw_sms:
                //切换相反
                sw_sms.setSelected(!sw_sms.isSelected());
                //保存状态
                ShareUtils.putBoolean(this,"isSms",sw_sms.isChecked());
                if(sw_sms.isChecked()){
                    startService(new Intent(this,SmsService.class));
                }else{
                    stopService(new Intent(this,SmsService.class));
                }
                break;
        }
    }
}
