package com.example.smartbutler.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.smartbutler.R;

/**
 * 项目名：    SmartButler
 * 包名：      com.example.smartbutler.ui
 * 文件名：    LoginActivity
 * 作者：      钟士宜
 * 创建时间    2019/5/21 9:54
 * 描述：      登录
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    //登录,注册，忘记密码按钮
    private Button btn_login;
    private Button btn_register;
    private Button btn_forget;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
    }

    private void initView() {
        btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_register:
                startActivity(new Intent(this,RegisteredActivity.class));
                break;
        }
    }
}
